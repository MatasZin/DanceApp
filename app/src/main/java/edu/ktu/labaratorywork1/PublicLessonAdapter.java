package edu.ktu.labaratorywork1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.ktu.labaratorywork1.DanceLesson;
import edu.ktu.labaratorywork1.ListItem;
import edu.ktu.labaratorywork1.R;

public class PublicLessonAdapter extends BaseAdapter {
    Context c;
    ArrayList<DanceLesson> originalArray;

    public PublicLessonAdapter(Context c, ArrayList<DanceLesson> originalArray){
        this.c = c;
        this.originalArray = originalArray;
    }

    public void setOriginalArray(ArrayList<DanceLesson> originalArray) {
        this.originalArray = originalArray;
    }
    @Override
    public int getCount()  {
        return originalArray.size();
    }

    @Override
    public Object getItem(int position)  {
        return originalArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.public_lesson, null);

        TextView title = (TextView) row.findViewById(R.id.publicTitle);
        TextView description = (TextView) row.findViewById(R.id.publicDescription);
        TextView type = (TextView) row.findViewById(R.id.publicType);
        TextView weekDay = (TextView) row.findViewById(R.id.publicWeekDay);
        TextView startTime = (TextView) row.findViewById(R.id.publicStartTime);
        TextView endTime = (TextView) row.findViewById(R.id.publicEndTime);
        ImageView image = (ImageView) row.findViewById(R.id.image);

        title.setText(originalArray.get(position).getTitle());
        description.setText(originalArray.get(position).getDescription());
        type.setText("Type: "+originalArray.get(position).getType());
        weekDay.setText(originalArray.get(position).getWeekDay());
        startTime.setText(originalArray.get(position).getStartTime());
        endTime.setText(originalArray.get(position).getEndTime());
        image.setImageResource(R.drawable.dancelatin);
        return row;

    }
}
