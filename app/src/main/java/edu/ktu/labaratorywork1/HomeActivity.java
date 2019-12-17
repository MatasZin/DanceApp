package edu.ktu.labaratorywork1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Button dancesExploreButton;
    private Button adminHomeButton;
    private Button userHomeButton;
    private Button requestActivityButton;
    private Button lab3AcitivtyButton;
    private ArrayList<DanceLesson> danceLessonList;

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
        lab3AcitivtyButton = (Button) findViewById(R.id.Lab3Activity);

        dancesExploreButton.setOnClickListener(startDanceExploration);
        adminHomeButton.setOnClickListener(startAdminHome);
        userHomeButton.setOnClickListener(startUserHome);
        requestActivityButton.setOnClickListener(startRequestActivity);
        lab3AcitivtyButton.setOnClickListener(startLab3Activity);

        danceLessonList = new ArrayList<>();
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

    public void runLab3Activity(Intent intent) {context.startActivity(intent);}
    View.OnClickListener startLab3Activity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Lab3main.class);
            runLab3Activity(intent);
        }
    };

    public void runAdminHomeActivity(Intent intent) {
        //this.db.addBaseLessons();
        //Bundle database = new Bundle();
        //database.putSerializable("db", db);
        //intent.putExtras(database);
        Bundle lessons = new Bundle();
        addBaseLessons();
        lessons.putSerializable("lessons", danceLessonList);
        intent.putExtras(lessons);
        context.startActivity(intent);
    }
    View.OnClickListener startAdminHome = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, AdminHomeActivity.class);
            runAdminHomeActivity(intent);
        }
    };

    public void runUserHomeActivity(Intent intent) {
        Bundle lessons = new Bundle();
        lessons.putSerializable("lessons", addBaseLessons());
        intent.putExtras(lessons);
        context.startActivity(intent);
    }
    View.OnClickListener startUserHome = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, UserHomeActivity.class);
            runUserHomeActivity(intent);
        }
    };

    private ArrayList<DanceLesson> addBaseLessons(){
        ArrayList<DanceLesson> danceLessonList = new ArrayList<>();
        DanceLesson publicDanceLessonMonday1 = new DanceLesson(
                "Medalist bronze", "17:00", "18:30",
                "Monday", "", "Pair's", "Latin"
        );
        DanceLesson publicDanceLessonMonday2 = new DanceLesson(
                "Medalist silver", "18:30", "20:00",
                "Monday", "", "Pair's", "Latin"
        );
        DanceLesson publicDanceLessonWednesday1 = new DanceLesson(
                "Medalist bronze", "17:00", "18:30",
                "Wednesday", "", "Pair's", "Ballroom"
        );
        DanceLesson publicDanceLessonWednesday2 = new DanceLesson(
                "Medalist silver", "18:30", "20:00",
                "Wednesday", "", "Pair's", "Ballroom"
        );
        DanceLesson publicDanceLessonTuesday1 = new DanceLesson(
                "Medalist bronze", "17:00", "18:30",
                "Tuesday", "", "Solo", "Latin"
        );
        DanceLesson publicDanceLessonTuesday2 = new DanceLesson(
                "Medalist silver", "18:30", "20:00",
                "Tuesday", "", "Solo", "Latin"
        );
        DanceLesson publicDanceLessonThursday1 = new DanceLesson(
                "Argentine", "17:00", "18:30",
                "Thursday", "", "Pair's", "Social dance"
        );
        DanceLesson publicDanceLessonThursday2 = new DanceLesson(
                "Caribean", "18:30", "20:00",
                "Thursday", "", "Pair's", "Social dance"
        );
        DanceLesson private1 = new DanceLesson(
                "Private", "17:00", "18:30",
                "Friday", "2019-12-20", "Pair's", "Private"
        );
        DanceLesson private2 = new DanceLesson(
                "Private", "18:30", "20:00",
                "Friday", "2019-12-20", "Pair's", "Private"
        );

        danceLessonList.add(publicDanceLessonMonday1);
        danceLessonList.add(publicDanceLessonMonday2);
        danceLessonList.add(publicDanceLessonTuesday1);
        danceLessonList.add(publicDanceLessonTuesday2);
        danceLessonList.add(publicDanceLessonWednesday1);
        danceLessonList.add(publicDanceLessonWednesday2);
        danceLessonList.add(publicDanceLessonThursday1);
        danceLessonList.add(publicDanceLessonThursday2);
        danceLessonList.add(private1);
        danceLessonList.add(private2);

        this.danceLessonList = danceLessonList;

        return danceLessonList;
    }
}
