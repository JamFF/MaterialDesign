package com.ff.material.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.ff.material.R;
import com.ff.material.core.bean.Movie;

import java.util.ArrayList;

public class NormalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private ArrayList<Movie.SubjectsBean> mData;

    public NormalAdapter(ArrayList<Movie.SubjectsBean> SubjectsBean, Context context) {
        this.mData = SubjectsBean;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new RecyclerViewItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerViewItemHolder recyclerViewHolder = (RecyclerViewItemHolder) holder;

        recyclerViewHolder.tv_name.setText(mData.get(position).getTitle());
        recyclerViewHolder.tv_star.setText(mData.get(position).getYear());
        recyclerViewHolder.tv_year.setText(mData.get(position).getRating().getAverage() + "");
        recyclerViewHolder.tv_avatars.setText(mData.get(position).getCasts().get(0).getName());
        Glide.with(mContext).load(mData.get(position).getImages().getMedium())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(2000))// 淡入淡出动画
                .into(recyclerViewHolder.iv_icon);
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * 正常条目的item的ViewHolder
     */
    private class RecyclerViewItemHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;
        public ImageView iv_icon;
        public TextView tv_year;
        public TextView tv_avatars;
        public TextView tv_star;


        public RecyclerViewItemHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_avatars = itemView.findViewById(R.id.tv_avatars);
            tv_year = itemView.findViewById(R.id.tv_year);
            tv_star = itemView.findViewById(R.id.tv_star);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
