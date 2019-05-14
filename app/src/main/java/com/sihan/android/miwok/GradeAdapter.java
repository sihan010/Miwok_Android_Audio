package com.sihan.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GradeAdapter extends ArrayAdapter<Grade> {
    private Context mContext;
    private int mResource;

    public GradeAdapter(Context context, int resource, ArrayList<Grade> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String gradeData = getItem(position).getResultString();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView textView_grade = convertView.findViewById(R.id.textView_grade);
        textView_grade.setText(gradeData);

        return convertView;
    }
}
