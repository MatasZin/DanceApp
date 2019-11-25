package edu.ktu.labaratorywork1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter implements Filterable {

    Context c;
    ArrayList<ListItem> originalArray, tempArray;
    CustomFilter cs;

    public MyAdapter(Context c, ArrayList<ListItem> originalArray){
        this.c = c;
        this.originalArray = originalArray;
        this.tempArray = originalArray;
    }

    public void setOriginalArray(ArrayList<ListItem> originalArray) {
        this.originalArray = originalArray;
    }

    @Override
    public int getCount() {
        return originalArray.size();
    }

    @Override
    public ListItem getItem(int position) {
        return originalArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.object, null);

        TextView title = (TextView) row.findViewById(R.id.title);
        TextView description = (TextView) row.findViewById(R.id.description);
        ImageView image = (ImageView) row.findViewById(R.id.image);

        title.setText(originalArray.get(position).getTitle());
        description.setText(originalArray.get(position).getDescription());
        image.setImageResource(originalArray.get(position).getImagedId());
        return row;
    }

    @Override
    public Filter getFilter() {
        if(cs == null){
            cs = new CustomFilter();
        }
        return cs;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint!=null && constraint.length()>0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<ListItem> filters = new ArrayList<>();
                for (int i = 0; i < tempArray.size()-1; i++) {
                    if (tempArray.get(i).getTitle().toUpperCase().contains(constraint)) {
                        ListItem item = new ListItem(tempArray.get(i).getTitle(), tempArray.get(i).getImagedId(), tempArray.get(i).getDescription());
                        filters.add(item);
                    }
                }

                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = tempArray.size();
                results.values = tempArray;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalArray = (ArrayList<ListItem>)results.values;
            notifyDataSetChanged();
        }
    }
}
