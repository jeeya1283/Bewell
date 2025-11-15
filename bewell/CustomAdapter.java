package com.example.bewell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter{

    private final Context context;
    private final String[] names;
    private final String[] times;
    private final int[] images;


    public CustomAdapter(Context context, String[] names, String[] times, int[] images) {
        this.context = context;
        this.names = names;
        this.times = times;
        this.images = images;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_customtext, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView timeTextView = convertView.findViewById(R.id.timeTextView);

        imageView.setImageResource(images[position]);
        nameTextView.setText(names[position]);
        timeTextView.setText(times[position]);

        return convertView;
    }

    public void updateNames(List<String> filteredData) {

    }
}
