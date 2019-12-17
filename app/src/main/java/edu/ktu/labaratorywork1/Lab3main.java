package edu.ktu.labaratorywork1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab3main extends AppCompatActivity implements SensorEventListener, LocationListener {
    private SensorManager sensorManager;
    private Sensor senAccelerometer;
    private LocationManager locationManager;

    private Button startAndStop;

    private TextView xValue;
    private TextView yValue;
    private TextView zValue;
    private TextView coordinatesGPS;
    private TextView position;
    private TextView degrees;
    private TextView coordinatesNETWORK;

    private boolean InformationObtained;

    private static final String TAG = "AndroidCameraApi";
    private Button takePictureButton;
    private TextureView textureView;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private String cameraID;
    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;
    private File file;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private Handler mBackgroudHandler;
    private HandlerThread mBackgroundThread;

    private ArrayList<Float> rollingAverage[];
    private static final int MAX_SAMPLE_SIZE = 5;
    private static final double UPDATE_LIMIT = 0.1f;


    private ImageView compass_img;
    private Sensor mMagnetometer;
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float[] Rot = new float[9];
    private float[] I = new float[9];
    private float azimuth;

    private boolean wasNorth = true;
    private static int picNumber = 0;
    private boolean isPictureBeingTaken = false;
    private boolean sosStatus = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab3main);

        InformationObtained = false;

        startAndStop = (Button) findViewById(R.id.start_and_stop);
        startAndStop.setOnClickListener(StartAndStopButtonListener);

        xValue = (TextView) findViewById(R.id.x_value);
        yValue = (TextView) findViewById(R.id.y_value);
        zValue = (TextView) findViewById(R.id.z_value);
        coordinatesGPS = (TextView) findViewById(R.id.coordinatesGPS);
        position = findViewById(R.id.position);
        degrees = findViewById(R.id.degrees);
        coordinatesNETWORK = findViewById(R.id.coordinatesNETWORK);

        rollingAverage = new ArrayList[3];
        rollingAverage[0] = new ArrayList<>(MAX_SAMPLE_SIZE);
        rollingAverage[1] = new ArrayList<>(MAX_SAMPLE_SIZE);
        rollingAverage[2] = new ArrayList<>(MAX_SAMPLE_SIZE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        textureView = (TextureView) findViewById(R.id.textureView);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);

        takePictureButton = (Button) findViewById(R.id.take_photo);
        assert takePictureButton != null;
        takePictureButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                takePicture();
            }
        });

        compass_img = findViewById(R.id.imageViewCompass);
        mMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    View.OnClickListener StartAndStopButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            if(senAccelerometer == null){
                Toast.makeText(Lab3main.this, getString(R.string.no_sensor), Toast.LENGTH_SHORT).show();
                return;
            }
            if(mMagnetometer == null){
                Toast.makeText(Lab3main.this, "Your device has no magnetometer.", Toast.LENGTH_LONG).show();
                return;
            }

            if(InformationObtained){
                startAndStop.setText(getString(R.string.start));
                sensorManager.unregisterListener(Lab3main.this, senAccelerometer);
                InformationObtained = false;
            } else {
                sensorManager.registerListener(Lab3main.this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(Lab3main.this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
                startAndStop.setText(getString(R.string.stop));
                InformationObtained = true;
                compass_img.setImageResource(R.mipmap.compass);
                InformationObtained = true;
            }
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.3f;

        Sensor mySensor = event.sensor;

        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){

            if(event.values[2] > 5 && event.values[0] < 5 && event.values[0] > -5 && event.values[1] < 5 && event.values[1] > -5){
                position.setText("Screen up");
            } else if (event.values[2] < -5 && event.values[0] < 5 && event.values[0] > -5 && event.values[1] < 5 && event.values[1] > -5){
                //this.finish();
            } else if(event.values[0] > 5 && event.values[1] < 5 && event.values[1] > -5){
                position.setText("Right side up/left side down");
            } else if (event.values[0] < -5 && event.values[1] < 5 && event.values[1] > -5){
                position.setText("Left side up/right side down");
            } else if(event.values[1] > 5){
                position.setText("Top side up/bottom side down");
            } else if(event.values[1] < -5){
                position.setText("Bottom side up/top side down");
            }

            if(rollingAverage[0].size() == 0 || Math.abs(Math.abs(averageList(rollingAverage[0])) -
                    Math.abs(event.values[0])) > UPDATE_LIMIT){
                rollingAverage[0] = roll(rollingAverage[0], event.values[0]);
                xValue.setText(String.valueOf(averageList(rollingAverage[0])));
            }

            if(rollingAverage[1].size() == 0 || Math.abs(Math.abs(averageList(rollingAverage[1])) -
                    Math.abs(event.values[1])) > UPDATE_LIMIT){
                rollingAverage[1] = roll(rollingAverage[1], event.values[1]);
                yValue.setText(String.valueOf(averageList(rollingAverage[1])));
            }

            if(rollingAverage[2].size() == 0 || Math.abs(Math.abs(averageList(rollingAverage[2])) -
                    Math.abs(event.values[2])) > UPDATE_LIMIT){
                rollingAverage[2] = roll(rollingAverage[2], event.values[2]);
                zValue.setText(String.valueOf(averageList(rollingAverage[2])));
            }

            mGravity[0] = alpha * mGravity[0] + (1 - alpha)
                    * event.values[0];
            mGravity[1] = alpha * mGravity[1] + (1 - alpha)
                    * event.values[1];
            mGravity[2] = alpha * mGravity[2] + (1 - alpha)
                    * event.values[2];

            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = Math.abs(event.values[1] / 10);
            getWindow().setAttributes(lp);
        }

        /**Compass**/
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // mGeomagnetic = event.values;

            mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha)
                    * event.values[0];
            mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha)
                    * event.values[1];
            mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha)
                    * event.values[2];
            // Log.e(TAG, Float.toString(event.values[0]));
        }

        boolean success = SensorManager.getRotationMatrix(Rot, I, mGravity,
                mGeomagnetic);
        if (success) {
            float orientation[] = new float[3];
            SensorManager.getOrientation(Rot, orientation);
            // Log.d(TAG, "azimuth (rad): " + azimuth);
            azimuth = (float) Math.toDegrees(orientation[0]); // orientation
            azimuth = (azimuth + 360) % 360;
            // Log.d(TAG, "azimuth (deg): " + azimuth);
            compass_img.setRotation(-azimuth);
            degrees.setText(String.valueOf(Math.round(azimuth)));

            if (azimuth > 340 || azimuth < 20) {
                if (!wasNorth) {
                    wasNorth = true;
                    takePicture();
                }
            } else if (azimuth > 90 && azimuth < 270) {
                wasNorth = false;
            }

            //Flash on south
            if (azimuth > 160 && azimuth < 200 && mGravity[1] > 8) {
                if (!sosStatus) {
                    sosStatus = true;
                    CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        closeCamera();
                        //Short flash
                        manager.setTorchMode(cameraID, true);
                        Thread.sleep(100);
                        manager.setTorchMode(cameraID, false);
                        Thread.sleep(300);
                        //Short flash
                        manager.setTorchMode(cameraID, true);
                        Thread.sleep(100);
                        manager.setTorchMode(cameraID, false);
                        Thread.sleep(300);
                        //Short flash
                        manager.setTorchMode(cameraID, true);
                        Thread.sleep(100);
                        manager.setTorchMode(cameraID, false);
                        Thread.sleep(900);
                        //Long flash
                        manager.setTorchMode(cameraID, true);
                        Thread.sleep(500);
                        manager.setTorchMode(cameraID, false);
                        Thread.sleep(500);
                        //Long flash
                        manager.setTorchMode(cameraID, true);
                        Thread.sleep(500);
                        manager.setTorchMode(cameraID, false);
                        Thread.sleep(500);
                        //Long flash
                        manager.setTorchMode(cameraID, true);
                        Thread.sleep(500);
                        manager.setTorchMode(cameraID, false);
                        Thread.sleep(500);
                        //Short flash
                        manager.setTorchMode(cameraID, true);
                        Thread.sleep(100);
                        manager.setTorchMode(cameraID, false);
                        Thread.sleep(300);
                        //Short flash
                        manager.setTorchMode(cameraID, true);
                        Thread.sleep(100);
                        manager.setTorchMode(cameraID, false);
                        Thread.sleep(300);
                        //Short flash
                        manager.setTorchMode(cameraID, true);
                        Thread.sleep(100);
                        manager.setTorchMode(cameraID, false);
                        Thread.sleep(300);

                        openCamera();
                    } catch (CameraAccessException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if (azimuth < 90 || azimuth > 270 || mGravity[1] < 5) {
                sosStatus = false;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(senAccelerometer != null){
            sensorManager.unregisterListener(Lab3main.this, senAccelerometer);
        }
        stopBackgroundThread();

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        this.locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(senAccelerometer != null && InformationObtained) {
            sensorManager.registerListener(Lab3main.this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
        this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, this);

        startBackgroundThread();
        if(textureView.isAvailable()){
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }

        sensorManager.registerListener(Lab3main.this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
                coordinatesGPS.setText(getString(R.string.Latitude_text) + " "
                        + location.getLatitude() + " " + getString(R.string.Longitude_text)
                        + " " + location.getLongitude());
            } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
                coordinatesNETWORK.setText(getString(R.string.Latitude_text) + " "
                        + location.getLatitude() + " " + getString(R.string.Longitude_text)
                        + " " + location.getLongitude());
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener(){

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            Log.e(TAG, "onOpened");
            cameraDevice = camera;
            createCameraPreview();
            isPictureBeingTaken = false;
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    protected void startBackgroundThread(){
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroudHandler = new Handler(mBackgroundThread.getLooper());
    }

    protected void stopBackgroundThread(){
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroudHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void takePicture(){
        if(null == cameraDevice){
            Log.e(TAG, "Camera device is null");
            return;
        }

        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        if(!isPictureBeingTaken) {
            isPictureBeingTaken = true;
            try {
                CameraCharacteristics cameraCharacteristics = manager.getCameraCharacteristics(cameraDevice.getId());
                Size[] jpegSizes = null;
                if (cameraCharacteristics != null) {
                    jpegSizes = cameraCharacteristics.get(cameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
                }
                int width = 640;
                int height = 480;
                if (jpegSizes != null && 0 < jpegSizes.length) {
                    width = jpegSizes[0].getWidth();
                    height = jpegSizes[0].getHeight();
                }
                ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
                List<Surface> outputSurface = new ArrayList<Surface>(2);
                outputSurface.add(reader.getSurface());
                outputSurface.add(new Surface(textureView.getSurfaceTexture()));
                final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                captureBuilder.addTarget(reader.getSurface());

                captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

                int rotation = getWindowManager().getDefaultDisplay().getRotation();
                captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
                final File file = new File(Environment.getExternalStorageDirectory() + "/pic" + (picNumber++) + ".jpg");
                ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
                    @Override
                    public void onImageAvailable(ImageReader reader) {
                        Image image = null;
                        try {
                            image = reader.acquireLatestImage();
                            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                            byte[] bytes = new byte[buffer.capacity()];
                            buffer.get(bytes);
                            save(bytes);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (image != null) {
                                image.close();
                            }
                        }
                    }

                    private void save(byte[] bytes) throws IOException {
                        OutputStream output = null;
                        try {
                            output = new FileOutputStream(file);
                            output.write(bytes);
                        } finally {
                            if (null != output) {
                                output.close();
                            }
                        }
                    }
                };
                reader.setOnImageAvailableListener(readerListener, mBackgroudHandler);

                final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                    @Override
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                        super.onCaptureCompleted(session, request, result);
                        Toast.makeText(Lab3main.this, "Saved:" + file,
                                Toast.LENGTH_SHORT).show();
                        createCameraPreview();
                        isPictureBeingTaken = false;
                    }
                };

                cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession session) {
                        try {
                            session.capture(captureBuilder.build(), captureListener, mBackgroudHandler);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                    }
                }, mBackgroudHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    protected void createCameraPreview(){
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert  texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if(null == cameraDevice){return;}
                    cameraCaptureSessions = session;
                    updatePreview();
                }


                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Toast.makeText(Lab3main.this, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        Log.e(TAG, " is camera open");

        try {
            cameraID = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraID);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert  map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Lab3main.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraID, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "openCamera X");
    }

    protected void updatePreview(){
        if(null == cameraDevice){
            Log.e(TAG, "updatePreview error, return");
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null , mBackgroudHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera(){
        if(null != cameraDevice){
            cameraDevice.close();
            cameraDevice = null;
        }
        if(null != imageReader){
            imageReader.close();
            imageReader = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CAMERA_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(Lab3main.this, "Sorry!!!, you can't use this app without granting permission",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public ArrayList<Float> roll(ArrayList<Float> list, float newMember){
        if(list.size() == MAX_SAMPLE_SIZE){
            list.remove(0);
        }
        list.add(newMember);
        return list;
    }

    public float averageList(ArrayList<Float> tallyUp){
        float total=0;
        for(float item : tallyUp ){
            total+=item;
        }
        total = total/tallyUp.size();

        return total;
    }
}


