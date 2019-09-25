package com.example.themodist1;

/**
 * Created by
 * Dilipkumar R. Kaklotar
 * [ Senior Android Developer ]
 * Mobile: +91 8000722607
 * Skype: dilipkaklotar
 * Created On 2019-09-25.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.MessageViewHolder> {

    private ArrayList<ImageModel> imageModelArrayList = new ArrayList<>();
    private Context context;

    public HorizontalRecyclerViewAdapter(ArrayList<ImageModel> horizontalList, Context context) {
        this.imageModelArrayList = horizontalList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, final int position) {
        final ImageModel model = imageModelArrayList.get(position);
        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;

        Glide.with(context).load(model.getImagePath()).into(messageViewHolder.imageView);

        messageViewHolder.tvName.setText(model.getImageName());

        messageViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, model.getImageName()+" - "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName;

        private MessageViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            tvName = (TextView)view.findViewById(R.id.tvName);
        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MessageViewHolder(itemView);
    }
}
