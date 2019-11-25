package edu.ktu.labaratorywork1;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.View;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

import java.util.List;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity  implements TextWatcher{
    private ListView mylist;
    //private ListAdapter adapter;
    private MyAdapter myAdapter;

    private Spinner sortDropDown ;
    private String[] sortBy = new String[]{"none", "A-Z", "Z-A"};
    List<String> sortStrList = new ArrayList<String>(Arrays.asList(sortBy));
    private Button sortDancesButton;

    private Button removeSomeDancesButton;

    private EditText filterText;

    private ArrayList<ListItem> items = new ArrayList<>();
    private ArrayList<ListItem> dancesForThirdActivity = new ArrayList<>();

    private Context context = this;
    private Intent savedIntent;

    @Override
    protected void onCreate (Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.secondactivitydesign);
        mylist = (ListView) findViewById(R.id.listView);

        final Intent intentGiven = getIntent();
        savedIntent=intentGiven;
        Bundle bundleItems = intentGiven.getExtras();
        final ArrayList<ListItem> allDances = (ArrayList<ListItem>) bundleItems.getSerializable("dances");
        ArrayList<ListItem> danceTypes = (ArrayList<ListItem>) bundleItems.getSerializable("danceTypes");
        ArrayList<ListItem> items = new ArrayList<>();

        if (intentGiven.getBooleanExtra("flag",true)){

            items.add(new ListItem("Ballroom", R.drawable.danceballroom,"Slow Waltz, Tango, Slow Foxtrot, Viennese Waltz, QuickStep"));
            items.add(new ListItem("Latin", R.drawable.dancelatin,"Cha cha cha, Rumba, Samba, Pasodoble, Jive"));
            items.add(new ListItem("Social dance", R.drawable.dancelatin,"Salsa, Bachata, Argentine tango"));
            items.add(new ListItem("Solo dance", R.drawable.dancesolo,"Salsa, Bachata, Argentine tango"));

        } else {
            LinearLayout optionalBar = findViewById(R.id.aditionalBarForSort);
            optionalBar.setVisibility(optionalBar.VISIBLE);

            String sortingMethod = bundleItems.getString("sort");

            ArrayAdapter<String> arraySortAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortBy);
            arraySortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sortDropDown = findViewById(R.id.sortDropDown);
            sortDropDown.setAdapter(arraySortAdapter);
            sortDropDown.setSelection(sortStrList.indexOf(sortingMethod));

            sortDancesButton = (Button) findViewById(R.id.sortDancesButton);
            sortDancesButton.setOnClickListener(sortDancesBySelectedValue);

            items = allDances;
            removeSomeDancesButton = findViewById(R.id.removeSomeDances);
            removeSomeDancesButton.setOnClickListener(removeSomeDances);

            switch (sortingMethod){
                case "A-Z":
                    Collections.sort(items);
                    break;
                case "Z-A":
                    Collections.sort(items, Collections.reverseOrder());
                    break;
            }
        }

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               ListItem item = myAdapter.getItem(position);
               //Intent intentItem = new Intent(context, DanceDetailsActivity.class);
               //runDanceDetails(intentItem, item);
               String[] dances = item.getListOfDances();
               dancesForThirdActivity = new ArrayList<>();
               for (String dance:dances){
                   for (ListItem danceFromAll:allDances){
                       if(dance.equals(danceFromAll.getTitle())){
                           dancesForThirdActivity.add(danceFromAll);
                           break;
                      }
                  }
               }
               Intent intentItem = new Intent(context, ThirdActivity.class);
               runThirdActivity(intentItem);
            }
        });


        mylist.setTextFilterEnabled(true);
        filterText = (EditText) findViewById(R.id.filterDanceText);
        filterText.addTextChangedListener(this);
        this.items = items;
        //adapter = new ListAdapter(this, items);
        myAdapter = new MyAdapter(this,items);

        mylist.setAdapter(myAdapter);
    }

    View.OnClickListener removeSomeDances = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int x = 2;
            ArrayList<ListItem> tempItems = new ArrayList<>(items);
            for (ListItem item:tempItems){
                x=3-x;
                if (x == 2) {
                    items.remove(item);
                }
            }
            myAdapter.notifyDataSetChanged();
        }
    };
    public void runThirdActivity(Intent intentThirdActivity){
        //Intent intentThirdActivity = new Intent(context, ThirdActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("items", dancesForThirdActivity);
        intentThirdActivity.putExtras(bundle);
        context.startActivity(intentThirdActivity);
    }


    View.OnClickListener sortDancesBySelectedValue = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (sortDropDown.getSelectedItem().toString()){
                case "A-Z":
                    Collections.sort(items);
                    break;
                case "Z-A":
                    Collections.sort(items, Collections.reverseOrder());
                    break;
            }
            myAdapter.setOriginalArray(items);
            myAdapter.notifyDataSetChanged();
        }
    };

    public void runSecondActivitySorted(Intent intentSecondActivity, String sort, boolean flag){
        intentSecondActivity.putExtras(savedIntent.getExtras());
        intentSecondActivity.putExtra("flag", flag);
        intentSecondActivity.putExtra("sort", sort);
        context.startActivity(intentSecondActivity);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.myAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
