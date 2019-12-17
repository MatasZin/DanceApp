package edu.ktu.labaratorywork1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
//import android.widget.Toolbar;

public class UserPublicLessonsActivity extends AppCompatActivity {

    private LinearLayout toolbar;
    private ListView listView;
    public static SharedPreferences sharedPreferences;
    public static String SEL_DAY;
    private ArrayList<DanceLesson> publiclessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
        final Intent intentGiven = getIntent();
        //savedIntent=intentGiven;
        Bundle bundleItems = intentGiven.getExtras();
        final ArrayList<DanceLesson> lessons = (ArrayList<DanceLesson>) bundleItems.getSerializable("publicLessons");
        publiclessons = lessons;
        setupUIViews();
        //initToolbar();

        setupListView();


    }

    private void setupUIViews() {
        //toolbar = (LinearLayout) findViewById(R.id.ToolbarWeek);
        listView = (ListView) findViewById(R.id.lvWeek);
        sharedPreferences = getSharedPreferences("MY_DAY", MODE_PRIVATE);
    }

    private void initToolbar() {
        toolbar.setVisibility(toolbar.VISIBLE);
    }

    private void setupListView() {
        String[] week = getResources().getStringArray(R.array.Week);

        WeekAdapter adapter = new WeekAdapter(this, R.layout.activity_week_single_item, week);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UserPublicLessonsActivity.this, DayDetails.class);
                ArrayList<DanceLesson> publicLessons= new ArrayList<>();
                Bundle lessons = new Bundle();
                lessons.putSerializable("publicLessons", publiclessons);
                intent.putExtras(lessons);
                switch (position) {
                    case 0: {
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Monday").apply();

                        break;
                    }
                    case 1: {
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Tuesday").apply();
                        break;
                    }
                    case 2: {
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Wednesday").apply();
                        break;
                    }
                    case 3: {
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Thursday").apply();
                        break;
                    }
                    case 4: {
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Friday").apply();
                        break;
                    }
                    default:
                        break;
                }
            }
        });

    }
}
