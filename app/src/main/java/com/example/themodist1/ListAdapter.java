package com.example.themodist1;

/**
 * Created by
 * Dilipkumar R. Kaklotar
 * [ Senior Android Developer ]
 * Mobile: +91 8000722607
 * Skype: dilipkaklotar
 * Created On 2019-09-25.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final ArrayList<String> web;

    public ListAdapter(Activity context,
                       ArrayList<String> web) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        Glide.with(context).load(web.get(position)).into(imageView);
        return rowView;
    }
}
