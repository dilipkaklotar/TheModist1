package com.example.themodist1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * Dilipkumar R. Kaklotar
 * [ Senior Android Developer ]
 * Mobile: +91 8000722607
 * Skype: dilipkaklotar
 * Created On 2019-09-25.
 */
public class CarousalPagerAdapter  extends PagerAdapter {

    Context context;

    LayoutInflater inflater;

    ArrayList<String> sampleTitles = new ArrayList<String>();
    ArrayList<String> sampleNetworkImageURLs = new ArrayList<String>();
    ArrayList<String> sampleDescription = new ArrayList<String>();

    public CarousalPagerAdapter(Context context, ArrayList<String> sampleTitles, ArrayList<String> sampleNetworkImageURLs,
                                ArrayList<String> sampleDescription) {
        this.context= context;

       // this.sampleTitles.clear();
        //this.sampleNetworkImageURLs.clear();

        this.sampleTitles.addAll(sampleTitles);
        this.sampleNetworkImageURLs.addAll(sampleNetworkImageURLs);
        this.sampleDescription.addAll(sampleDescription);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public int getCount() {
        return sampleNetworkImageURLs.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView;
        itemView = inflater.inflate(R.layout.carousal_adapter, container, false);

        ImageView imageBanner = (ImageView) itemView.findViewById(R.id.ImageView);
        Log.e("gg","banner :::: " + sampleNetworkImageURLs.get(position));
        Glide.with(context).load(sampleNetworkImageURLs.get(position)).into(imageBanner);

        TextView textView = (TextView)itemView.findViewById(R.id.txtLabel);
        textView.setText(sampleTitles.get(position));

        TextView textViewDesc = (TextView)itemView.findViewById(R.id.txtDescription);
        textViewDesc.setText(sampleDescription.get(position));

        ((ViewPager) container).addView(itemView);

        return itemView;
    }
}
