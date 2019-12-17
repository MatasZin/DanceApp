package edu.ktu.labaratorywork1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import edu.ktu.labaratorywork1.Utils.PrivateLessonAdapter;

public class AdminPrivateLessonsActivity extends AppCompatActivity {
    private LinearLayout toolbar;
    private ListView listView;
    private ArrayList<DanceLesson> privatelessons;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_private_lesson);

        listView = (ListView) findViewById(R.id.privateLessonsListView);

        Bundle bundleItems = getIntent().getExtras();
        final ArrayList<DanceLesson> lessons = (ArrayList<DanceLesson>) bundleItems.getSerializable("privateLessons");
        privatelessons = lessons;
        PrivateLessonAdapter privateLessonAdapter = new PrivateLessonAdapter(context, privatelessons);
        listView.setAdapter(privateLessonAdapter);


    }
}
