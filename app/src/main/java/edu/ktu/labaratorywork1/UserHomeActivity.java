package edu.ktu.labaratorywork1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class UserHomeActivity extends AppCompatActivity {

    private Button publicDancesButton;
    private Button privateDancesButton;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminhome);

        publicDancesButton = (Button) findViewById(R.id.userPublicLessonsButton);
        privateDancesButton = (Button) findViewById(R.id.userPrivateLessonsButton);

        publicDancesButton.setOnClickListener(startUserPublicLessons);
        privateDancesButton.setOnClickListener(startUserPrivteLessons);
    }

    public void runUserPublicDanceLessons(Intent intent) {context.startActivity(intent);}
    View.OnClickListener startUserPublicLessons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, UserPublicLessonsActivity.class);
            runUserPublicDanceLessons(intent);
        }
    };

    public void runUserPrivateDanceLessons(Intent intent) {context.startActivity(intent);}
    View.OnClickListener startUserPrivteLessons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, UserPrivateLessonsActivity.class);
            runUserPrivateDanceLessons(intent);
        }
    };
}
