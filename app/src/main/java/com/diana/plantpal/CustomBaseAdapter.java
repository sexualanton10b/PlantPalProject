package com.diana.plantpal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    String listPlant[]; int listImage[]; LayoutInflater inflater;
    public CustomBaseAdapter(Context ctx, String [] plantList, int []images){
        this.context=ctx;
        this.listPlant=plantList;
        this.listImage=images;
        inflater=LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return listPlant.length;
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
        convertView=inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView txtView=(TextView) convertView.findViewById(R.id.plantName);
        ImageView plantImg=(ImageView) convertView.findViewById(R.id.plantIcon);
        txtView.setText(listPlant[position]);
        plantImg.setImageResource(listImage[position]);
        return convertView;
    }
}
