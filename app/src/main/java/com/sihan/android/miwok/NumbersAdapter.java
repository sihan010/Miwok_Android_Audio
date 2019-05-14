package com.sihan.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


class NumbersAdapter extends ArrayAdapter<Word> {
    private Context mContext;
    private int mResource;
    private int mColor;
    private ArrayList<Word> mData;

    public NumbersAdapter(Context context, int resource, ArrayList<Word> objects, int color) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mColor = color;
        mData=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String engText = getItem(position).getEnglish();
        String mewokText = getItem(position).getMewok();
        int src = getItem(position).getSrc();


        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView text_view_english = convertView.findViewById(R.id.text_view_english);
        TextView text_view_mewok = convertView.findViewById(R.id.text_view_mewok);
        ImageView imageView_logo = convertView.findViewById(R.id.imageView_logo);

        text_view_english.setText(engText);
        text_view_mewok.setText(mewokText);

        if(src==0){
            imageView_logo.setVisibility(View.GONE);
        }
        else{
            imageView_logo.setImageResource(src);
            imageView_logo.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.image_back));
        }

        convertView.setBackgroundColor(ContextCompat.getColor(getContext(),mColor));
        return convertView;
    }
}
