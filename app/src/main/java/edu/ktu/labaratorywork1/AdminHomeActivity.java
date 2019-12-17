package edu.ktu.labaratorywork1;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {

    private Button publicDancesButton;
    private Button privateDancesButton;
    private Button openCreatePublicLessonButton;
    private Button openCreatePrivateLessonButton;
    private Button createLessonButton;
    private Button dismissCreateButton;
    private RelativeLayout lessonsButtonsLayout;
    private RelativeLayout titleLayout;
    private RelativeLayout descriptionLayout;
    private RelativeLayout weekDayLayout;
    private RelativeLayout typeLayout;
    private RelativeLayout dateLayout;
    private RelativeLayout startTimeLayout;
    private RelativeLayout endTimeLayout;
    private RelativeLayout createLessonButtonsLayout;
    private RelativeLayout lessonsCreateButtonsLayout;
    private TextView adminPanel;
    private EditText lessonTitle;
    private EditText lessonDescription;
    private Spinner lessonWeekDay;
    private Spinner lessonType;
    private EditText lessonDate;
    private EditText lessonStartTime;
    private EditText lessonEndTime;

    private Intent savedIntent;
    private boolean ifOpen = false;

    private String[] types = new String[]{"Private", "Latin", "Ballroom", "Solo dance", "Social Dance"};
    private String[] weekDays = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", ""};

    private ArrayList<DanceLesson> items = new ArrayList<>();
    private ArrayList<DanceLesson> privateLessons;
    private ArrayList<DanceLesson> publicLessons;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminhome);

        initializeUIComponents();
        setOnClicks();
        final Intent intentGiven = getIntent();
        savedIntent=intentGiven;
        Bundle bundleItems = intentGiven.getExtras();
        final ArrayList<DanceLesson> lessons = (ArrayList<DanceLesson>) bundleItems.getSerializable("lessons");

        items = lessons;

    }

    public void initializeUIComponents(){
        publicDancesButton = (Button) findViewById(R.id.adminPublicLessonsButton);
        privateDancesButton = (Button) findViewById(R.id.adminPrivateLessonsButton);
        openCreatePublicLessonButton = (Button) findViewById(R.id.openCreatePublicLessonButton);
        openCreatePrivateLessonButton = (Button) findViewById(R.id.openCreatePrivateLessonButton);
        createLessonButton = (Button) findViewById(R.id.createLessonButton);
        dismissCreateButton = (Button) findViewById(R.id.dismissCreateButton);

        lessonsButtonsLayout = (RelativeLayout) findViewById(R.id.lessonsButtonsLayout);
        titleLayout = (RelativeLayout) findViewById(R.id.titleLayout);
        descriptionLayout = (RelativeLayout) findViewById(R.id.descriptionLayout);
        weekDayLayout = (RelativeLayout) findViewById(R.id.weekDayLayout);
        typeLayout = (RelativeLayout) findViewById(R.id.typeLayout);
        dateLayout = (RelativeLayout) findViewById(R.id.dateLayout);
        startTimeLayout = (RelativeLayout) findViewById(R.id.startTimeLayout);
        endTimeLayout = (RelativeLayout) findViewById(R.id.endTimeLayout);
        createLessonButtonsLayout = (RelativeLayout) findViewById(R.id.createLessonButtonsLayout);

        adminPanel = (TextView) findViewById(R.id.adminPanel);
        lessonTitle = (EditText) findViewById(R.id.lessonTitle);
        lessonDescription = (EditText) findViewById(R.id.lessonDescription);
        lessonDate = (EditText) findViewById(R.id.lessonDate);
        lessonStartTime = (EditText) findViewById(R.id.lessonStartTime);
        lessonEndTime = (EditText) findViewById(R.id.lessonEndTime);

        lessonType = (Spinner) findViewById(R.id.lessonType);
        ArrayAdapter<String> arrayTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        arrayTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lessonType.setAdapter(arrayTypeAdapter);
        lessonType.setSelection(0);

        lessonWeekDay = (Spinner) findViewById(R.id.lessonWeekDay);
        ArrayAdapter<String> arrayWeekDaysAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weekDays);
        arrayWeekDaysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lessonWeekDay.setAdapter(arrayWeekDaysAdapter);
        lessonWeekDay.setSelection(0);


        //createLessonButtonsLayout.setVisibility(View.GONE);
        titleLayout.setVisibility(View.GONE);
        descriptionLayout.setVisibility(View.GONE);
        startTimeLayout.setVisibility(View.GONE);
        endTimeLayout.setVisibility(View.GONE);
        dateLayout.setVisibility(View.GONE);
        weekDayLayout.setVisibility(View.GONE);
        typeLayout.setVisibility(View.GONE);

    }

    public void setOnClicks(){
        publicDancesButton.setOnClickListener(startAdminPublicLessons);
        privateDancesButton.setOnClickListener(startAdminPrivteLessons);
        openCreatePublicLessonButton.setOnClickListener(openPublicLessonCreate); //pakeist
        openCreatePrivateLessonButton.setOnClickListener(openPrivateLessonCreate); //pakeist
        createLessonButton.setOnClickListener(createLesson);
        dismissCreateButton.setOnClickListener(dismissCreateLesson);
    }
    public void runAdminPublicDanceLessons(Intent intent) {
        publicLessons = new ArrayList<>();
        for (DanceLesson item:items){
            if(!item.getType().equals("Private")){
                publicLessons.add(item);
            }
        }
        Bundle lessons = new Bundle();
        lessons.putSerializable("publicLessons", publicLessons);
        intent.putExtras(lessons);
        context.startActivity(intent);
    }
    View.OnClickListener startAdminPublicLessons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, AdminPublicLessonsActivity.class);
            runAdminPublicDanceLessons(intent);
        }
    };

    public void runAdminPrivateDanceLessons(Intent intent) {
        privateLessons = new ArrayList<>();
        for (DanceLesson item:items){
            if(item.getType().equals("Private")){
                privateLessons.add(item);
            }
        }
        Bundle lessons = new Bundle();
        lessons.putSerializable("privateLessons", privateLessons);
        intent.putExtras(lessons);
        context.startActivity(intent);
    }
    View.OnClickListener startAdminPrivteLessons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, AdminPrivateLessonsActivity.class);
            runAdminPrivateDanceLessons(intent);
        }
    };

    View.OnClickListener openPublicLessonCreate = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(!ifOpen) {
                adminPanel.setVisibility(View.GONE);
                lessonsButtonsLayout.setVisibility(View.GONE);
                //lessonsCreateButtonsLayout.setVisibility(View.INVISIBLE);
                //createLessonButtonsLayout.setVisibility(View.VISIBLE);
                weekDayLayout.setVisibility(View.VISIBLE);
                titleLayout.setVisibility(View.VISIBLE);
                startTimeLayout.setVisibility(View.VISIBLE);
                endTimeLayout.setVisibility(View.VISIBLE);
                descriptionLayout.setVisibility(View.VISIBLE);
                typeLayout.setVisibility(View.VISIBLE);
                lessonDate.setText("");
                ifOpen = true;
            }
        }
    };

    View.OnClickListener openPrivateLessonCreate = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(!ifOpen) {
                adminPanel.setVisibility(View.GONE);
                lessonsButtonsLayout.setVisibility(View.GONE);
                //lessonsCreateButtonsLayout.setVisibility(View.INVISIBLE);
                //createLessonButtonsLayout.setVisibility(View.VISIBLE);
                titleLayout.setVisibility(View.VISIBLE);
                descriptionLayout.setVisibility(View.VISIBLE);
                startTimeLayout.setVisibility(View.VISIBLE);
                endTimeLayout.setVisibility(View.VISIBLE);
                dateLayout.setVisibility(View.VISIBLE);
                lessonWeekDay.setSelection(5);
                lessonType.setSelection(0);
                ifOpen = true;
            }
        }
    };

    View.OnClickListener createLesson = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(ifOpen) {
                adminPanel.setVisibility(View.VISIBLE);
                lessonsButtonsLayout.setVisibility(View.VISIBLE);
                //lessonsCreateButtonsLayout.setVisibility(View.VISIBLE);
                //createLessonButtonsLayout.setVisibility(View.GONE);
                titleLayout.setVisibility(View.GONE);
                descriptionLayout.setVisibility(View.GONE);
                startTimeLayout.setVisibility(View.GONE);
                endTimeLayout.setVisibility(View.GONE);
                dateLayout.setVisibility(View.GONE);
                weekDayLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.GONE);

                items.add(new DanceLesson(lessonTitle.getText().toString(), lessonStartTime.getText().toString(),
                        lessonEndTime.getText().toString(), lessonWeekDay.getSelectedItem().toString(),
                        lessonDate.getText().toString(), lessonDescription.getText().toString(),
                        lessonType.getSelectedItem().toString()));

                lessonDescription.setText("");
                lessonStartTime.setText("");
                lessonEndTime.setText("");
                lessonTitle.setText("");
                lessonDate.setText("");
                lessonWeekDay.setSelection(5);
                lessonType.setSelection(0);
                ifOpen = false;
            }
        }
    };

    View.OnClickListener dismissCreateLesson = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(ifOpen) {
                adminPanel.setVisibility(View.VISIBLE);
                lessonsButtonsLayout.setVisibility(View.VISIBLE);
                //lessonsCreateButtonsLayout.setVisibility(View.VISIBLE);
                //createLessonButtonsLayout.setVisibility(View.GONE);
                titleLayout.setVisibility(View.GONE);
                descriptionLayout.setVisibility(View.GONE);
                startTimeLayout.setVisibility(View.GONE);
                endTimeLayout.setVisibility(View.GONE);
                dateLayout.setVisibility(View.GONE);
                weekDayLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.GONE);
                lessonDescription.setText("");
                lessonStartTime.setText("");
                lessonEndTime.setText("");
                lessonTitle.setText("");
                lessonDate.setText("");
                lessonWeekDay.setSelection(5);
                lessonType.setSelection(0);
                ifOpen = false;
            }
        }
    };

}
