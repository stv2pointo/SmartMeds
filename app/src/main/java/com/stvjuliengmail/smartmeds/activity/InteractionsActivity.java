package com.stvjuliengmail.smartmeds.activity;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.InteractionsAdapter;
import com.stvjuliengmail.smartmeds.model.Interaction;

import java.util.ArrayList;
import java.util.List;

public class InteractionsActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private String name;
    private int rxcui;
    private TextView tvName;
    private RecyclerView interactions_recycler_view;
    private List<Interaction> interactions = new ArrayList<>();
    private RecyclerView.Adapter interactionsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactions);

        interactions_recycler_view = (RecyclerView) findViewById(R.id.interactions_recycler_view);
        interactions_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        interactionsAdapter = new InteractionsAdapter(interactions, R.layout.listview_row_interaction, getApplicationContext());
        interactions_recycler_view.setAdapter(interactionsAdapter);



        loadInteractions();


        unpackIntentExtras();

        initializeUiElements();

        displayUiElements();
    }

    private void initializeUiElements() {
        tvName = (TextView) findViewById(R.id.tvName);
    }

    private void displayUiElements() {
        tvName.setText(name);
    }

    private void unpackIntentExtras() {
        Bundle extras = getIntent().getExtras();
        rxcui = extras.getInt("rxcui");
        name = extras.getString("name");
    }

    private void loadInteractions() {
        interactions.clear();
        getInteractions();
        interactionsAdapter.notifyDataSetChanged();
    }

    private void getInteractions() {
        for (int i = 0; i < 3; i++) {
            interactions.add(new Interaction("pill " + i, "description " + i, "severity " + i));
        }
    }

}
