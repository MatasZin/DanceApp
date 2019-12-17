package edu.ktu.labaratorywork1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class IndicatingView extends View{
    public static final int NOTEXECUTED = 0;
    public static final int SUCCESS = 1;
    public static final int FAILED = 2;
    public static final int EXECUTING = 3;

    int state = NOTEXECUTED;

    public IndicatingView (Context context) {super(context);}

    public IndicatingView(Context context, AttributeSet attrs) { super(context, attrs);}

    public IndicatingView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    public int getState(){ return state;}

    public void setState(int state){this.state = state;}

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Paint paint;
        switch (state){
            case SUCCESS:
                paint = new Paint();
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(20f);

                canvas.drawLine(0, 0, width/2, height, paint);
                canvas.drawLine(width/2, height,width, height/2, paint);
                break;

            case FAILED:
                paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(20f);

                canvas.drawLine(0, 0, width, height, paint);
                canvas.drawLine(0, height,width, 0, paint);
                break;

            case EXECUTING:
                paint = new Paint();
                paint.setColor(Color.BLUE);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setAntiAlias(true);
                //paint.setStrokeWidth(20f);
                //canvas.drawLine(0, height, width/2, 0, paint);
                //canvas.drawLine(width/2, 0, width, height, paint);
                //canvas.drawLine(width, height, 0, height, paint);
                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.moveTo(height,width/2);
                path.lineTo(0,width);
                path.lineTo(0,0);
                path.lineTo(height,width/2);
                canvas.drawPath(path,paint);


            break;
            default:
                break;
        }
    }

    public void startAnimation() {
        clearAnimation();
        RotateAnimation rotate = new RotateAnimation(
                0,360,Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatCount(Animation.INFINITE);
        startAnimation(rotate);
    }

    public void stopAnimation(){
        clearAnimation();
    }
}
