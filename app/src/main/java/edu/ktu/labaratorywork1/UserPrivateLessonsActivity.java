package edu.ktu.labaratorywork1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import edu.ktu.labaratorywork1.Utils.PrivateLessonAdapter;

public class UserPrivateLessonsActivity extends AppCompatActivity {
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
        final PrivateLessonAdapter privateLessonAdapter = new PrivateLessonAdapter(context, privatelessons);
        listView.setAdapter(privateLessonAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DanceLesson item = privateLessonAdapter.getItem(position);
                Intent intentItem = new Intent(context, AchievmentActivity.class);
                runAchievment(intentItem, item);
            }
        });
    }

    private void runAchievment(Intent intentItem, DanceLesson item) {
        String text = "User has registered to a private lesson \"" + item.getTitle() +
                "\" on: " + item.getDate() + " it starts at - " + item.getStartTime() +
                " and ends at - "
                +item.getEndTime();
        intentItem.putExtra("text", text);
        context.startActivity(intentItem);
    }
}
