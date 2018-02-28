package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.database.DBHelper;

import java.util.ArrayList;
import android.widget.ListView;

import static com.stvjuliengmail.smartmeds.R.id.editRXid1;


public class MyMedsActivity extends AppCompatActivity {

    private ListView listViewMyMeds;
    DBHelper db;
    ArrayList myMedsList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meds);

        db = new DBHelper(this);
        myMedsList = db.getAllMeds();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, myMedsList);
        listViewMyMeds = (ListView)findViewById(R.id.listView1);
        listViewMyMeds.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        listViewMyMeds.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Intent intent = new Intent(MyMedsActivity.this, EditMed.class);
                intent.putExtra("RXid", arg2 +1);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case editRXid1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DisplayMeds.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}



