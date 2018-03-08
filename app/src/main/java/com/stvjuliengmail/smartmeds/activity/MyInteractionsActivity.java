package com.stvjuliengmail.smartmeds.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.MyInteractionsAdapter;
import com.stvjuliengmail.smartmeds.adapter.MyMedsAdapter;
import com.stvjuliengmail.smartmeds.adapter.RecyclerViewItemClickListener;
import com.stvjuliengmail.smartmeds.api.MyInteractionsTask;
import com.stvjuliengmail.smartmeds.model.MyInteraction;
import com.stvjuliengmail.smartmeds.model.MyMed;

import java.util.ArrayList;
import java.util.List;

public class MyInteractionsActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private TextView tvDisclaimer;
    private RecyclerView my_interactions_recycler_view;
    private List<MyInteraction> myInteractions = new ArrayList<>();
    private MyInteractionsAdapter myInteractionsAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_interactions);

        context = this;

        initializeUiElements();

        String[] temps = new String[]{"207106","152923","656659"};

        new MyInteractionsTask(this, temps).execute("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeUiElements() {
        tvDisclaimer = (TextView) findViewById(R.id.tvDisclaimer);
        my_interactions_recycler_view = (RecyclerView) findViewById(R.id.my_interactions_recycler_view);
        wireAdapterToRecyclerView();
    }

    private void wireAdapterToRecyclerView() {
        my_interactions_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        myInteractionsAdapter = new MyInteractionsAdapter(myInteractions, R.layout.my_interaction_row, getApplicationContext());
        my_interactions_recycler_view.setAdapter(myInteractionsAdapter);
        //Create custom interface object and send it to adapter for clickable list items
        myInteractionsAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {
                startBrowser(myInteractions.get(position).getUrl());
            }
        });

        my_interactions_recycler_view.setAdapter(myInteractionsAdapter);
    }
    public void populateDisclaimer(String disclaimer) {
        tvDisclaimer.setText(disclaimer);
    }

    public void populateRecyclerView(ArrayList<MyInteraction> interactionsFromTask) {
        myInteractions.clear();
        myInteractions.addAll(interactionsFromTask);
        myInteractionsAdapter.notifyDataSetChanged();
    }

    private void startBrowser(String url){
        try{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
        catch (Exception e){
            Toast.makeText(context, "Site unavailable", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Couldn't parse " + url);
        }
    }
}
