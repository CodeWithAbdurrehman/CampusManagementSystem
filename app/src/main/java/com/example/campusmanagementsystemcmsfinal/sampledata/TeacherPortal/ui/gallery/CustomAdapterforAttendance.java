package com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.campusmanagementsystemcmsfinal.R;


public class CustomAdapterforAttendance extends BaseAdapter {

    String[] nameList,rolllist;
    Context context;

    LayoutInflater inflater;

    public CustomAdapterforAttendance(String[] nameList, String[] rolllist , Context context) {
        this.nameList = nameList;
        this.context = context;
        this.rolllist = rolllist;
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

        convertView =inflater.inflate(R.layout.custom_teacher_attendance,null);
        TextView roll = convertView.findViewById(R.id.textViewRollNumber);
        TextView name = convertView.findViewById(R.id.textViewName);
        roll.setText(rolllist[position]);
        name.setText(nameList[position]);
        return convertView;
    }
    public String getName(int position) {
        return nameList[position];
    }
}
