package com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.campusmanagementsystemcmsfinal.R;

public class CustomAdapter extends BaseAdapter {

    String[] nameList,namedesc;
    int[] imageList;
    Context context;

    LayoutInflater inflater;

    public CustomAdapter(String[] nameList, String[] namedesc , int[] imageList, Context context) {
        this.nameList = nameList;
        this.imageList = imageList;
        this.context = context;
        this.namedesc = namedesc;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return nameList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView =inflater.inflate(R.layout.tenrolled_courses,null);

        ImageView imageview = convertView.findViewById(R.id.course_image);
        TextView tvCname = convertView.findViewById(R.id.course_name);
        TextView tvCDesc = convertView.findViewById(R.id.course_detail);
        imageview.setImageResource(imageList[position]);
        tvCname.setText(nameList[position]);
        tvCDesc.setText(namedesc[position]);

        return convertView;
    }
    public String getName(int position) {
        return nameList[position];
    }
}
