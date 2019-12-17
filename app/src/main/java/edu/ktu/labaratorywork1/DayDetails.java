package edu.ktu.labaratorywork1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class DayDetails extends AppCompatActivity {

    private ListView listView;
    private LinearLayout toolbar;
    private Context context = this;
    //private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_details);

        setUpUIViews();

        String day = UserPublicLessonsActivity.sharedPreferences.getString(UserPublicLessonsActivity.SEL_DAY, null);
        Bundle bundleItems = getIntent().getExtras();
        final ArrayList<DanceLesson> lessons = (ArrayList<DanceLesson>) bundleItems.getSerializable("publicLessons");
        ArrayList<DanceLesson> dayLessons ;
        dayLessons = getDayLessons(day, lessons);
        PublicLessonAdapter publicLessonAdapter = new PublicLessonAdapter(context, dayLessons);
        listView.setAdapter(publicLessonAdapter);

        initToolbar();
    }

    private void setUpUIViews(){
        toolbar = (LinearLayout) findViewById(R.id.DayDetailsToolbar);
        listView = (ListView) findViewById(R.id.lvDayDetails);
    }

    private void initToolbar() {
        toolbar.setVisibility(toolbar.VISIBLE);
    }


    private ArrayList<DanceLesson> getDayLessons(String day, ArrayList<DanceLesson> lessons){
        ArrayList<DanceLesson> dayLessons = new ArrayList<>();
        for (DanceLesson lesson:lessons){
            if(lesson.getWeekDay().equals(day)){
                dayLessons.add(lesson);
            }
        }
        return dayLessons;
    }
}
