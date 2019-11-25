package edu.ktu.labaratorywork1;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity{
    private ListView mylist;
    //private ListAdapter adapter;
    ArrayList<ListItem> items;
    private MyAdapter myAdapter;
    private Context context = this;

    @Override
    protected void onCreate (Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.secondactivitydesign);
        mylist = (ListView) findViewById(R.id.listView);

        Bundle bundleItems = getIntent().getExtras();
        items = (ArrayList<ListItem>) bundleItems.getSerializable("items");
        myAdapter = new MyAdapter(this, items);
        mylist.setAdapter(myAdapter);

        //mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            //public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            //    ListItem item = myAdapter.getItem(position);
            //    items.remove(item);
            //    myAdapter.notifyDataSetChanged();
            //}
       // });
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ListItem item = myAdapter.getItem(position);
                Intent intentItem = new Intent(context, DanceDetailsActivity.class);
                runDanceDetails(intentItem, item);
                //String[] dances = item.getListOfDances();
                //dancesForThirdActivity = new ArrayList<>();
                //for (String dance:dances){
                //    for (ListItem danceFromAll:allDances){
                //        if(dance.equals(danceFromAll.getTitle())){
                //            dancesForThirdActivity.add(danceFromAll);
                //            break;
                //        }
                //    }
                //}
                //Intent intentItem = new Intent(context, ThirdActivity.class);
                //runThirdActivity(intentItem);
            }
        });
    }


    public void runDanceDetails(Intent intentDanceDetails, ListItem item){
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        intentDanceDetails.putExtras(bundle);
        context.startActivity(intentDanceDetails);
    }
}
