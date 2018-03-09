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
import com.stvjuliengmail.smartmeds.model.MyInteraction;

import java.util.List;

/**
 * Created by Steven on 2/23/2018.
 */

public class MyInteractionsAdapter extends RecyclerView.Adapter<MyInteractionsAdapter.MyInteractionsViewHolder>  {

    private List<MyInteraction> myInteractions;
    private int rowLayout;
    private Context context;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public MyInteractionsAdapter(List<MyInteraction> myInteractions, int rowLayout, Context context) {
        this.myInteractions = myInteractions;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    public class MyInteractionsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout myInteractionLayout;
        TextView tvFirstDrug;
        TextView tvSecondDrug;
        TextView tvDescription;
        int position = 0;

        public MyInteractionsViewHolder(View v) {
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
            myInteractionLayout = (LinearLayout) v.findViewById(R.id.my_interaction_item_layout);
            tvFirstDrug = (TextView) v.findViewById(R.id.tvFirstDrug);
            tvSecondDrug = (TextView) v.findViewById(R.id.tvSecondDrug);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
        }
    }

    public void setOnItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    public List<MyInteraction> getMyInteractions() {
        return myInteractions;
    }

    public void setMyInteractions(List<MyInteraction> myInteractions) {
        this.myInteractions = myInteractions;
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
    public MyInteractionsAdapter.MyInteractionsViewHolder onCreateViewHolder(ViewGroup parent,
                                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyInteractionsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyInteractionsViewHolder holder, final int position) {
        holder.tvFirstDrug.setText(myInteractions.get(position).getFirstDrug());
        holder.tvSecondDrug.setText(myInteractions.get(position).getSecondDrug());
        holder.tvDescription.setText(myInteractions.get(position).getDescription());
    }

    @Override
    public int getItemCount() { return myInteractions.size();}
}

