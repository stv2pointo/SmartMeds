package com.stvjuliengmail.smartmeds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import java.util.List;


/**
 * Created by Steven on 2/7/2018.
 */

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> {

    private List<RxImagesResult.NlmRxImage> results;
    private int rowLayout;
    private Context context;

    public ResultsAdapter(List<RxImagesResult.NlmRxImage> results, int rowLayout, Context context){
        this.results = results;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    public List<RxImagesResult.NlmRxImage> getResults() { return results;}

    public void setResults(List<RxImagesResult.NlmRxImage> results) {
        this.results = results;
    }

    public int getRowLayout() {
        return rowLayout;
    }

    public void setRowLayout(int rowLayout) {
        this.rowLayout = rowLayout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static class ResultsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout resultsLayout;
        ImageView ivPillImage;
        TextView tvPillName;

        public ResultsViewHolder(View v) {
            super(v);
            resultsLayout = (LinearLayout) v.findViewById(R.id.search_result_item_layout);
            ivPillImage = (ImageView) v.findViewById(R.id.ivPillImage);
            tvPillName = (TextView) v.findViewById(R.id.tvPillName);
        }
    }

    @Override
    public ResultsAdapter.ResultsViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ResultsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ResultsViewHolder holder, final int position) {
//        holder.ivPillImage.setImageBitmap(???).setText(repos.get(position).getName());
        holder.tvPillName.setText(results.get(position).getName());
    }

    @Override
    public int getItemCount() { return results.size();}

}
