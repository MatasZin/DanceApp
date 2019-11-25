package edu.ktu.labaratorywork1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button dancesExploreButton;
    private Button adminHomeButton;
    private Button userHomeButton;
    private Button requestActivityButton;

    private Context context = this;

    private final DataBaseHelper db = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        dancesExploreButton = (Button) findViewById(R.id.danceExplorationButton);
        adminHomeButton = (Button) findViewById(R.id.adminHomeButton);
        userHomeButton = (Button) findViewById(R.id.userHomeButton);
        requestActivityButton = (Button) findViewById(R.id.requestActivityButton);

        dancesExploreButton.setOnClickListener(startDanceExploration);
        adminHomeButton.setOnClickListener(startAdminHome);
        userHomeButton.setOnClickListener(startUserHome);
        requestActivityButton.setOnClickListener(startRequestActivity);
    }


    public void runDanceExploration(Intent firstActivity){
        context.startActivity(firstActivity);
    }

    View.OnClickListener startDanceExploration = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentItems = new Intent(context, FirstActivity.class);
            runDanceExploration(intentItems);
        }
    };

    public void runRequestActivity(Intent intent) {context.startActivity(intent);}
    View.OnClickListener startRequestActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Lab2Activity.class);
            runRequestActivity(intent);
        }
    };

    public void runAdminHomeActivity(Intent intent) {
        this.db.addBaseLessons();
        Bundle database = new Bundle();
        database.putSerializable("db", db);
        intent.putExtras(database);
        context.startActivity(intent);
    }
    View.OnClickListener startAdminHome = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, AdminHomeActivity.class);
            runAdminHomeActivity(intent);
        }
    };

    public void runUserHomeActivity(Intent intent) {context.startActivity(intent);}
    View.OnClickListener startUserHome = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, UserHomeActivity.class);
            runUserHomeActivity(intent);
        }
    };
}
