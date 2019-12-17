package edu.ktu.labaratorywork1.Utils;

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

public class PrivateLessonAdapter extends BaseAdapter {
    Context c;
    ArrayList<DanceLesson> originalArray;

    public PrivateLessonAdapter(Context c, ArrayList<DanceLesson> originalArray){
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
    public DanceLesson getItem(int position)  {
        return originalArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.private_lesson, null);

        TextView title = (TextView) row.findViewById(R.id.privateTitle);
        TextView description = (TextView) row.findViewById(R.id.privateDescription);
        TextView date = (TextView) row.findViewById(R.id.privateDate);
        TextView startTime = (TextView) row.findViewById(R.id.privateStartTime);
        TextView endTime = (TextView) row.findViewById(R.id.privateEndTime);
        ImageView image = (ImageView) row.findViewById(R.id.image);

        title.setText(originalArray.get(position).getTitle());
        description.setText(originalArray.get(position).getDescription());
        date.setText(originalArray.get(position).getDate());
        startTime.setText(originalArray.get(position).getStartTime());
        endTime.setText(originalArray.get(position).getEndTime());
        image.setImageResource(R.drawable.basicdance);
        return row;

    }
}
