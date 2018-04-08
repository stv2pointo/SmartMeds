package com.stvjuliengmail.smartmeds.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.InteractionsAdapter;
import com.stvjuliengmail.smartmeds.api.InteractionsListTask;
import com.stvjuliengmail.smartmeds.model.Interaction;

import java.util.ArrayList;
import java.util.List;

public class InteractionsActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private String name;
    private int rxcui;
    private TextView tvName, tvDisclaimer;
    private RecyclerView interactions_recycler_view;
    private List<Interaction> interactions = new ArrayList<>();
    private RecyclerView.Adapter interactionsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactions);
        unpackIntentExtras();
        initializeUiElements();
        displayPillName();
        new InteractionsListTask(this, Integer.toString(rxcui)).execute("");
    }

    private void initializeUiElements() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvDisclaimer = (TextView) findViewById(R.id.tvDisclaimer);
        interactions_recycler_view = (RecyclerView) findViewById(R.id.interactions_recycler_view);
        interactions_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        interactionsAdapter = new InteractionsAdapter(interactions, R.layout.rv_row_interaction, getApplicationContext());
        interactions_recycler_view.setAdapter(interactionsAdapter);
    }

    private void displayPillName() {
        tvName.setText(name);
    }

    private void unpackIntentExtras() {
        Bundle extras = getIntent().getExtras();
        rxcui = extras.getInt("rxcui");
        name = extras.getString("name");
    }

    public void populateRecyclerView(ArrayList<Interaction> interactionsFromApi) {
        interactions.clear();
        interactions.addAll(interactionsFromApi);
        interactionsAdapter.notifyDataSetChanged();
    }

    public void populateDisclaimer(String disclaimer) {
        tvDisclaimer.setText(disclaimer);
    }
}
