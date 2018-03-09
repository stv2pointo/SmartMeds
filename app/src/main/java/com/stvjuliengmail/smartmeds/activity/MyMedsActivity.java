package com.stvjuliengmail.smartmeds.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.MyMedsAdapter;
import com.stvjuliengmail.smartmeds.adapter.RecyclerViewItemClickListener;
import com.stvjuliengmail.smartmeds.api.MyInteractionsTask;
import com.stvjuliengmail.smartmeds.database.GetDBMedsTask;
import com.stvjuliengmail.smartmeds.database.SmartMedsDbOpenHelper;
import com.stvjuliengmail.smartmeds.model.MyInteraction;
import com.stvjuliengmail.smartmeds.model.MyMed;

import java.util.ArrayList;

public class MyMedsActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private MyMedsAdapter adapter;
    private ArrayList<MyMed> myMedsList = new ArrayList<>();
    private ArrayList<MyInteraction> myInteractions = new ArrayList<>();
    private String interactionDisclaimer;
    private Context context;
    private String[] rxcuis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meds);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.rvMyMeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wireAdapterToRecyclerView();

        new GetDBMedsTask(this).execute("");
//SmartMedsDbOpenHelper dbOpenHelper = SmartMedsDbOpenHelper.getInstance(this);
//populateRecyclerView(dbOpenHelper);

//setMyMeds(dbOpenHelper.getAllMyMeds());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void wireAdapterToRecyclerView() {
        adapter = new MyMedsAdapter(myMedsList, R.layout.rv_row_my_meds,
                getApplicationContext());
        //Create custom interface object and send it to adapter for clickable list items
        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context, myMedsList.get(position).getName() + " selected", Toast.LENGTH_SHORT).show();
                MyMed myMed = myMedsList.get(position);
                Intent intent = new Intent(context, MyMedActivity.class);
                intent.putExtra("myMed", myMed);
                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // TODO: Is there a use case for this?
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void populateRecyclerView(SmartMedsDbOpenHelper dbOpenHelper) {
        myMedsList.clear();
        myMedsList = dbOpenHelper.getAllMyMeds();
//        if(myMedsList == null || myMedsList.size() < 1){
//            loadDummyData(dbOpenHelper);
//            myMedsList = dbOpenHelper.getAllMyMeds();
//        }
        wireAdapterToRecyclerView();
        adapter.notifyDataSetChanged();
    }
    public void setMyMeds(ArrayList<MyMed> myMedsFromDb) {
        myMedsList.clear();
        myMedsList.addAll(myMedsFromDb);
        adapter.notifyDataSetChanged();
    }

    public void checkForInteractions() {
        setRxcuis();
        lookForMyInteractionsTask();
    }

    private void setRxcuis() {
        rxcuis = new String[myMedsList.size()];
        int index = 0;
        for (MyMed myMed : myMedsList) {
            rxcuis[index] = myMed.getRxcui();
            index++;
        }
    }

    private void lookForMyInteractionsTask() {
        new MyInteractionsTask(this, rxcuis).execute("");
    }

    public void setDisclaimer(String disclaimer) {
        interactionDisclaimer = disclaimer;
    }

    public void setMyInteractions(ArrayList<MyInteraction> myInteractionsFromApi){
        if(myInteractionsFromApi != null && myInteractionsFromApi.size() > 0){
            myInteractions = myInteractionsFromApi;
            warnInteractions();
        }
    }

    private void warnInteractions() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Drug Interactions!");
        alertDialog.setMessage("Your medicines have interactions that may be hazardous.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "VIEW INTERACTIONS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInteractions();
                    }
                });
        alertDialog.show();
    }

    private void startInteractions(){
        Intent intent = new Intent(this, MyInteractionsActivity.class);
        intent.putExtra("my_interactions", myInteractions);
        intent.putExtra("disclaimer", interactionDisclaimer);
        startActivity(intent);
    }

//    private void loadDummyData(SmartMedsDbOpenHelper dbOpenHelper) {
//        String[] rxcuis = new String[]{"966200","197313"};
//        String[] names = new String[]{"Levothyroxine Sodium 0.15 MG Oral Tablet [Levoxyl]", "Acyclovir 800 MG Oral Tablet"};
//        String[] urls = new String[] {"https://rximage.nlm.nih.gov/image/images/gallery/600/60793-0858-01_RXNAVIMAGE10_02088174.jpg",
//                "https://rximage.nlm.nih.gov/image/images/gallery/600/60505-5307-01_RXNAVIMAGE10_4D26A6D5.jpg"};
//        for(int i=0;i<2;i++){
//            long rowId = dbOpenHelper.addOrUpdateMyMed(new MyMed(
//                    names[i],
//                    rxcuis[i],
//                    "150 mg",
//                    "Dr. Feelgood",
//                    "Take some daily",
//                    "Walgreens",
//                    urls[i]));
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}



