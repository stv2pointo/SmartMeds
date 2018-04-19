package com.stvjuliengmail.smartmeds.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.api.ImageDownloadTask;
import com.stvjuliengmail.smartmeds.model.MyMed;

import java.util.List;

import static com.stvjuliengmail.smartmeds.R.id.my_meds_item_layout;

/**
 * Created by Steven on 3/1/2018.
 */

public class MyMedsAdapter extends RecyclerView.Adapter<MyMedsAdapter.MyMedsViewHolder>{
    private List<MyMed> results;
    private int rowLayout;
    private Context context;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public MyMedsAdapter(List<MyMed> results, int rowLayout, Context context) {
        this.results = results;
        this.rowLayout = rowLayout;
        this.context = context;

    }

    public List<MyMed> getResults() {
        return results;
    }

    public class MyMedsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout resultsLayout;
        ImageView ivPillImage;
        TextView tvPillName, tvDirections, tvDose;
        String rxcui;
        int position = 0;

        public MyMedsViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    recyclerViewItemClickListener.onItemClick(v,position);
                }
            });
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerViewItemClickListener.onItemLongClick(v,position);
                    return true;
                }
            });
            resultsLayout = v.findViewById(my_meds_item_layout);
            ivPillImage = v.findViewById(R.id.ivPillImage);
            tvPillName = v.findViewById(R.id.tvPillName);
            tvDirections = v.findViewById(R.id.tvDirections);
            tvDose = v.findViewById(R.id.tvDose);
        }
    }
    public void setOnItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    @Override
    public MyMedsAdapter.MyMedsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyMedsAdapter.MyMedsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyMedsAdapter.MyMedsViewHolder holder, final int position) {
        ImageDownloadTask task = new ImageDownloadTask();
        try {
            Bitmap myBitmap = task.execute(results.get(position).getImageUrl()).get();
            if(myBitmap == null){
                holder.ivPillImage.setImageResource(R.drawable.no_img_avail);
            }
            else {
                holder.ivPillImage.setImageBitmap(myBitmap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        MyMed myMed = results.get(position);
        holder.tvPillName.setText(myMed.getName());
        holder.tvDirections.setText(myMed.getDirections());
        holder.tvDose.setText(myMed.getDosage());
        holder.rxcui = myMed.getRxcui();
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

}
