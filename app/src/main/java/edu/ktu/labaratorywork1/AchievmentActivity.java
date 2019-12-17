package edu.ktu.labaratorywork1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AchievmentActivity extends AppCompatActivity {

    private TextView achievmentText;
    private Button achievmentButton;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievment);

        achievmentText = (TextView) findViewById(R.id.achievment);
        achievmentButton = (Button) findViewById(R.id.shareAchievment);

        String text = getIntent().getStringExtra("text");
        message = text;

        achievmentText.setText(text);
        achievmentButton.setOnClickListener(shareAchievment);
    }

    View.OnClickListener shareAchievment = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(intent.createChooser(intent, "Share"));
        }
    };
}
