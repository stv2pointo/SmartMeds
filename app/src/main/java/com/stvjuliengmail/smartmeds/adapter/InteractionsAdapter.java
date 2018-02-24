package com.stvjuliengmail.smartmeds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.model.Interaction;

import java.util.List;

/**
 * Created by Steven on 2/23/2018.
 */

public class InteractionsAdapter extends RecyclerView.Adapter<InteractionsAdapter.InteractionsViewHolder>  {

    private List<Interaction> interactions;
    private int rowLayout;
    private Context context;

    public InteractionsAdapter(List<Interaction> interactions, int rowLayout, Context context) {
        this.interactions = interactions;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    public static class InteractionsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout interactionLayout;
        TextView tvInteractionName;
        TextView tvDescription;
        TextView tvSeverity;


        public InteractionsViewHolder(View v) {
            super(v);
            interactionLayout = (LinearLayout) v.findViewById(R.id.interaction_item_layout);
            tvInteractionName = (TextView) v.findViewById(R.id.tvInteractionName);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            tvSeverity = (TextView) v.findViewById(R.id.tvSeverity);
        }
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
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

    @Override
    public InteractionsAdapter.InteractionsViewHolder onCreateViewHolder(ViewGroup parent,
                                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new InteractionsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(InteractionsViewHolder holder, final int position) {
        holder.tvInteractionName.setText(interactions.get(position).getName());
        holder.tvDescription.setText(interactions.get(position).getDescription());
        holder.tvSeverity.setText(interactions.get(position).getSeverity());
    }

    @Override
    public int getItemCount() { return interactions.size();}
}
