package edu.ktu.labaratorywork1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity{

    private Button addNewDanceTypeButton;
    private EditText danceTypeTitle;
    private EditText danceTypeDescription;
    private Button secondActivityButton;
    private Context context = this;

    DancesData dataAdder = new DancesData();
    final ArrayList<ListItem> allDances = dataAdder.createBasicDances();
    final ArrayList<ListItem> danceTypes = dataAdder.createDanceTypes();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstactivitydesign);

        addNewDanceTypeButton = (Button) findViewById(R.id.addNewDanceType);
        secondActivityButton = (Button) findViewById(R.id.secondActivityButton);
        danceTypeTitle = (EditText) findViewById(R.id.danceTypeTitle);
        danceTypeDescription = (EditText) findViewById(R.id.danceTypeDescription);

        addNewDanceTypeButton.setOnClickListener(addNewDanceType);
        secondActivityButton.setOnClickListener(startSecondActivity);
        secondActivityButton.setOnLongClickListener(startSecondActivityLong);

    }

    View.OnClickListener addNewDanceType = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            allDances.add(new ListItem(danceTypeTitle.getText().toString(),
                    R.drawable.basicdance, danceTypeDescription.getText().toString()));
        }
    };


    public void runSecondActivity(boolean b, Intent intentSecondActivity){
        Bundle dancesData = new Bundle();
        dancesData.putSerializable("dances", allDances);

        dancesData.putSerializable("danceTypes", danceTypes);
        dancesData.putSerializable("sort", "none");
        intentSecondActivity.putExtras(dancesData);

        intentSecondActivity.putExtra("flag", b);
        context.startActivity(intentSecondActivity);
    }

    View.OnLongClickListener startSecondActivityLong = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View v){
            Intent intentItems = new Intent(context, SecondActivity.class);
            runSecondActivity(false, intentItems);
            return true;
        }
    };

    View.OnClickListener startSecondActivity = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent intentItems = new Intent(context, SecondActivity.class);
            runSecondActivity(true, intentItems);
        }
    };


}
