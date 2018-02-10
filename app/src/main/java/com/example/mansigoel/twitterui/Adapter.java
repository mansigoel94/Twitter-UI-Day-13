package com.example.mansigoel.twitterui;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<String> absolutePaths;
    private Context context;
    private static final String TAG = "Adapter";

    public Adapter(ArrayList<String> absolutePaths, Context context) {
        this.absolutePaths = absolutePaths;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_camera));
                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_video));
               holder.image.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                       context.startActivity(intent);
                   }
               });

                break;
            default:
                File file = new File(absolutePaths.get(position));
                Picasso.with(context)
                        .load(file)
                        .placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(holder.image);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return absolutePaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
