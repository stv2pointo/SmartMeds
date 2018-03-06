package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.stvjuliengmail.smartmeds.R;

import java.util.ArrayList;

import static com.stvjuliengmail.smartmeds.R.id.editRXid1;


public class MyMedsActivity extends AppCompatActivity {

    private ListView obj;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meds);

        db = new DBHelper(this);
        ArrayList array_list = db.getAllMeds();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Intent intent = new Intent(MyMedsActivity.this, EditMed.class);
                intent.putExtra("RXid", arg2 +1);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        int id = item.getItemId();
//        if(id==R.id.action_mainmenu)
//        {
//            Intent mainMenuIntent = new Intent(SearchActivity.this, MenuActivity.class);
//            startActivity(mainMenuIntent);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        int id1 = item.getItemId();
        if(id1==R.id.action_mainmenu)
        {
            Intent mainMenuIntent = new Intent(MyMedsActivity.this, MenuActivity.class);
            startActivity(mainMenuIntent);
        }

        switch(item.getItemId())
        {
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

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}



