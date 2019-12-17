package edu.ktu.labaratorywork1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class UserHomeActivity extends AppCompatActivity {

    private Button publicDancesButton;
    private Button privateDancesButton;
    ArrayList<DanceLesson> danceLessons = new ArrayList<>();

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhome);

        final Intent intentGiven = getIntent();
        Bundle bundleItems = intentGiven.getExtras();
        final ArrayList<DanceLesson> lessons = (ArrayList<DanceLesson>) bundleItems.getSerializable("lessons");
        danceLessons = lessons;

        publicDancesButton = (Button) findViewById(R.id.userPublicLessonsButton);
        privateDancesButton = (Button) findViewById(R.id.userPrivateLessonsButton);

        publicDancesButton.setOnClickListener(startUserPublicLessons);
        privateDancesButton.setOnClickListener(startUserPrivteLessons);
    }

    public void runUserPublicDanceLessons(Intent intent) {
        ArrayList<DanceLesson> publicLessons = new ArrayList<>();
        for (DanceLesson lesson:danceLessons){
            if(!lesson.getType().equals("Private")){
                publicLessons.add(lesson);
            }
        }
        Bundle lessons = new Bundle();
        lessons.putSerializable("publicLessons", publicLessons);
        intent.putExtras(lessons);
        context.startActivity(intent);
    }
    View.OnClickListener startUserPublicLessons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, UserPublicLessonsActivity.class);
            runUserPublicDanceLessons(intent);
        }
    };

    public void runUserPrivateDanceLessons(Intent intent) {
        ArrayList<DanceLesson> privateLessons = new ArrayList<>();
        for (DanceLesson lesson:danceLessons){
            if(lesson.getType().equals("Private")){
                privateLessons.add(lesson);
            }
        }
        Bundle lessons = new Bundle();
        lessons.putSerializable("privateLessons", privateLessons);
        intent.putExtras(lessons);
        context.startActivity(intent);
    }
    View.OnClickListener startUserPrivteLessons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, UserPrivateLessonsActivity.class);
            runUserPrivateDanceLessons(intent);
        }
    };
}
