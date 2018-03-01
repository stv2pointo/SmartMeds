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
    private SmartMedsDbOpenHelper dbOpenHelper;
    private RecyclerView recyclerView;
    private MyMedsAdapter adapter;
    private ArrayList<MyMed> myMedsList = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meds);
        context = this;

        DataManager.loadFromDatabase(dbOpenHelper);
        recyclerView = (RecyclerView) findViewById(R.id.rvMyMeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        wireAdapterToRecyclerView();
        //myMedsList = DataManager.getInstance().getMyMeds();

        dbOpenHelper = new SmartMedsDbOpenHelper(this);



        populateRecyclerView();


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

    public void generateDummyPills(){
        for(int i=0;i<3;i++){
            String index = Integer.toString(i);
            myMedsList.add(new MyMed(
                    "Name " + index,
                    index,"150 mg",
                    "Dr. Feelgood",
                    "Take " + index + " daily",
                    "Walgreens",
                    "https://rximage.nlm.nih.gov/image/images/gallery/600/00172-5728-60_RXNAVIMAGE10_5821AC5D.jpg"));
        }
    }

    public void populateRecyclerView() { //accepts arraylist of myMeds objects later
        myMedsList.clear();

        generateDummyPills();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        dbOpenHelper.close();
        super.onDestroy();
    }

}



