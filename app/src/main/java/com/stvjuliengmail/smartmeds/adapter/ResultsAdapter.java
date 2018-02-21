package com.stvjuliengmail.smartmeds.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.model.NlmRxImage;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Created by Steven on 2/7/2018.
 */

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> {

    private List<NlmRxImage> results;
    private int rowLayout;
    private Context context;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public ResultsAdapter(List<NlmRxImage> results, int rowLayout, Context context) {
        this.results = results;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    public List<NlmRxImage> getResults() {
        return results;
    }

    public void setResults(List<NlmRxImage> results) {
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

    public class ResultsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout resultsLayout;
        ImageView ivPillImage;
        TextView tvPillName;
        int rxcui = 0;
        int position = 0;

        public ResultsViewHolder(View v) {
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
                    //When item view is clicked long, trigger the itemclicklistener
                    //Because that itemclicklistener is indicated in MainActivity
                    recyclerViewItemClickListener.onItemLongClick(v,position);
                    return true;
                }
            });
            resultsLayout = (LinearLayout) v.findViewById(R.id.search_result_item_layout);
            ivPillImage = (ImageView) v.findViewById(R.id.ivPillImage);
            tvPillName = (TextView) v.findViewById(R.id.tvPillName);
        }
    }
    public void setOnItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    @Override
    public ResultsAdapter.ResultsViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ResultsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ResultsViewHolder holder, final int position) {
        ImageDownloader task = new ImageDownloader();
        try {
            Bitmap myBitmap = task.execute(results.get(position).getImageUrl()).get();
            holder.ivPillImage.setImageBitmap(myBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tvPillName.setText(results.get(position).getName());
        holder.rxcui = results.get(position).getRxcui();
        holder.position = position;
    }



    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
