package edu.ktu.labaratorywork1;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private Button publicDancesButton;
    private Button privateDancesButton;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminhome);

        publicDancesButton = (Button) findViewById(R.id.adminPublicLessonsButton);
        privateDancesButton = (Button) findViewById(R.id.adminPrivateLessonsButton);

        publicDancesButton.setOnClickListener(startAdminPublicLessons);
        privateDancesButton.setOnClickListener(startAdminPrivteLessons);
    }

    public void runAdminPublicDanceLessons(Intent intent) {context.startActivity(intent);}
    View.OnClickListener startAdminPublicLessons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, AdminPublicLessonsActivity.class);
            runAdminPublicDanceLessons(intent);
        }
    };

    public void runAdminPrivateDanceLessons(Intent intent) {context.startActivity(intent);}
    View.OnClickListener startAdminPrivteLessons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, AdminPrivateLessonsActivity.class);
            runAdminPrivateDanceLessons(intent);
        }
    };
}
