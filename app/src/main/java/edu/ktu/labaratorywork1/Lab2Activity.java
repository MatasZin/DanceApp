package edu.ktu.labaratorywork1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Lab2Activity extends AppCompatActivity implements RequestOperator.RequestOperatorListener {

    Button sendRequestButton;
    TextView title;
    TextView bodyText;
    private ModelPost publication;
    private IndicatingView indicator;
    private IndicateArrayCountView arrayIndicator;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.lab2_activity);

        sendRequestButton = (Button) findViewById(R.id.sendRequestButton);
        sendRequestButton.setOnClickListener(requestButtonClicked);
        indicator = (IndicatingView) findViewById(R.id.generatedGraphic);
        arrayIndicator = (IndicateArrayCountView)  findViewById(R.id.generatedArrayNumberCount);
        title = (TextView) findViewById(R.id.lab2Title);
        bodyText = (TextView) findViewById(R.id.lab2bodyText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    View.OnClickListener requestButtonClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            setIndicatorStatus(IndicatingView.EXECUTING);

            indicator.startAnimation();
            progressBar.setVisibility(View.VISIBLE);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    sendRequest();
                }},2222);
            //sendRequest();
        }
    };

    public void setIndicatorStatus(final int status){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                indicator.setState(status);
                indicator.invalidate();
            }
        });
    }

    private void sendRequest() {
        RequestOperator ro = new RequestOperator();
        ro.setListener(this);
        ro.start();
    }

    public void updatePublication(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(publication != null){
                    title.setText(publication.getTitle());
                    bodyText.setText(publication.getBodyText());
                } else {
                    title.setText("Conection failed");
                    bodyText.setText("sorry");
                }
            }
        });
    }

    @Override
    public void success(ArrayList<ModelPost> publication){
        setIndicatorStatus(IndicatingView.SUCCESS);
        setNumberIndicatorCount(publication.size());
        this.publication = publication.get(0);
        updatePublication();
        indicator.stopAnimation();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void failed(int responseCode){
        this.publication = null;
        setIndicatorStatus(IndicatingView.FAILED);
        updatePublication();
        indicator.stopAnimation();
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void setNumberIndicatorCount(final int number){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arrayIndicator.setNumber(number);
                arrayIndicator.invalidate();
            }
        });
    }
}

