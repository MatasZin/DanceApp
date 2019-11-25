package edu.ktu.labaratorywork1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

public class DayDetails extends AppCompatActivity {

    private ListView listView;
    private LinearLayout toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_details);

        setUpUIViews();
        initToolbar();
    }

    private void setUpUIViews(){
        toolbar = (LinearLayout) findViewById(R.id.DayDetailsToolbar);
        listView = (ListView) findViewById(R.id.lvDayDetails);
    }

    private void initToolbar() {
        toolbar.setVisibility(toolbar.VISIBLE);
    }

    private void setUpListView(){

    }
}
