package edu.ktu.labaratorywork1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import edu.ktu.labaratorywork1.Utils.LetterImageView;

public class WeekAdapter extends ArrayAdapter {

    private int resource;
    private LayoutInflater layoutInflater;
    private String[] week = new String[]{};

    public WeekAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.week = objects;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(resource, null);
            holder.ivLogo = (LetterImageView)convertView.findViewById(R.id.ivLetter);
            holder.tvWeek = (TextView)convertView.findViewById(R.id.tvMain);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.ivLogo.setOval(true);
        holder.ivLogo.setLetter(week[position].charAt(0));
        holder.tvWeek.setText(week[position]);

        return convertView;
    }

    class ViewHolder{
        private LetterImageView ivLogo;
        private TextView tvWeek;
    }
}

