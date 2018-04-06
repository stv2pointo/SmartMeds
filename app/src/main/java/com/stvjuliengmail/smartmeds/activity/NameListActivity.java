package com.stvjuliengmail.smartmeds.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.api.AutoFillTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NameListActivity extends AppCompatActivity
{

    private ArrayList<String> possibleNameMatches;
    private StableArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_list);

        final ListView listview = (ListView) findViewById(R.id.listviewPillNames);


        possibleNameMatches = new ArrayList<String>();

        adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, possibleNameMatches);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                possibleNameMatches.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });
        new AutoFillTask(this, "as;dlkfj").execute("");
    }

    private class StableArrayAdapter extends ArrayAdapter<String>
    {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects)
        {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i)
            {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position)
        {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }


    }
    public void populateListView(ArrayList<String> nameListsFromAPI)
    {
        possibleNameMatches.clear();
        possibleNameMatches.addAll(nameListsFromAPI);
        adapter.notifyDataSetChanged();
    }

}
