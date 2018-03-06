package com.stvjuliengmail.smartmeds.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.MyMedsAdapter;
import com.stvjuliengmail.smartmeds.adapter.RecyclerViewItemClickListener;
import com.stvjuliengmail.smartmeds.database.DBHelper;
import com.stvjuliengmail.smartmeds.database.SmartMedsDbOpenHelper;
import com.stvjuliengmail.smartmeds.model.DataManager;
import com.stvjuliengmail.smartmeds.model.MyMed;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.ListView;
import android.widget.Toast;

import static com.stvjuliengmail.smartmeds.R.id.editRXid1;


public class MyMedsActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView;
    private MyMedsAdapter adapter;
    private ArrayList<MyMed> myMedsList = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meds);
        context = this;

        recyclerView = (RecyclerView) findViewById(R.id.rvMyMeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get singleton instance of database
        SmartMedsDbOpenHelper dbOpenHelper = SmartMedsDbOpenHelper.getInstance(this);




        populateRecyclerView(dbOpenHelper);


    }

    private void populateRecyclerView(SmartMedsDbOpenHelper dbOpenHelper) {
        myMedsList.clear();
        myMedsList = dbOpenHelper.getAllMyMeds();
        if(myMedsList == null || myMedsList.size() < 1){
            loadDummyData(dbOpenHelper);
            myMedsList = dbOpenHelper.getAllMyMeds();
        }
        wireAdapterToRecyclerView();
        adapter.notifyDataSetChanged();
    }

    private void loadDummyData(SmartMedsDbOpenHelper dbOpenHelper) {
        String[] rxcuis = new String[]{"966200","197313"};
        String[] names = new String[]{"Levothyroxine Sodium 0.15 MG Oral Tablet [Levoxyl]", "Acyclovir 800 MG Oral Tablet"};
        String[] urls = new String[] {"https://rximage.nlm.nih.gov/image/images/gallery/600/60793-0858-01_RXNAVIMAGE10_02088174.jpg",
            "https://rximage.nlm.nih.gov/image/images/gallery/600/60505-5307-01_RXNAVIMAGE10_4D26A6D5.jpg"};
        for(int i=0;i<2;i++){
            long rowId = dbOpenHelper.addOrUpdateMyMed(new MyMed(
                    names[i],
                    rxcuis[i],
                    "150 mg",
                    "Dr. Feelgood",
                    "Take some daily",
                    "Walgreens",
                    urls[i]));
        }

    }

    private void wireAdapterToRecyclerView() {
        adapter = new MyMedsAdapter(myMedsList, R.layout.my_meds_item,
                getApplicationContext());
        //Create custom interface object and send it to adapter for clickable list items
        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context, myMedsList.get(position).getName() + " selected",Toast.LENGTH_SHORT).show();
//                startRxInfoActivity(imageList.get(position));
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // TODO: Use long click to open option to save to myMeds
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}



