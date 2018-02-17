package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.RecyclerViewItemClickListener;
import com.stvjuliengmail.smartmeds.adapter.ResultsAdapter;
import com.stvjuliengmail.smartmeds.api.ImageListTask;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SearchActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    // TODO: replace hard coded imprint, name, color, shape and limit with UI components
    String imprint;
    String name;
    String color;
    String shape;
    int limit;

    Button btnLoadList;
    Spinner colorSpinner, shapeSpinner;
    EditText etName, etImprint;
    RecyclerView recyclerView;
    ResultsAdapter adapter;
    List<RxImagesResult.NlmRxImage> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        imprint = "dp";

        recyclerView = (RecyclerView) findViewById(R.id.recVwResultList);
        btnLoadList = (Button) findViewById(R.id.btnLoadList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etName = (EditText) findViewById(R.id.etName);
        etImprint = (EditText) findViewById(R.id.etImprint);

        colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
//        ArrayAdapter<String> adapter_PillColor = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.array_PillColors));
//        adapter_PillColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_PillColor.setAdapter(adapter_PillColor);
//        set default position for hint
        colorSpinner.setSelection(0);

        shapeSpinner = (Spinner) findViewById(R.id.shapeSpinner);

//        ArrayAdapter<String> adapter_PillShape = new ArrayAdapter<String>(getActivity(),
//              android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.array_PillShapes));
//        adapter_PillShape.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_PillShape.setAdapter(adapter_PillShape);
//        set default position for hint
        shapeSpinner.setSelection(0);

        adapter = new ResultsAdapter(imageList, R.layout.list_search_result,
                getApplicationContext());

        //Create custom interface object and send it to adapter
        //Adapter triggers it when any item view is clicked
        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(context, "item clicked " + Integer.toString(imageList.get(position).getRxcui()), Toast.LENGTH_SHORT).show();
                startRxInfoActivity(imageList.get(position).getRxcui());
            }

            // TODO: Use long click to open option to save to myMeds
            @Override
            public void onItemLongClick(View view, int position) {
                //Toast.makeText(MainActivity.this, getResources().getString(R.string.long_clicked_item, albumList.get(position).getAlbumName()), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        btnLoadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        Log.d(TAG, "ONCREATE()");
    }

    public void search(){
        new ImageListTask(this, getFilter()).execute("");
    }

    public ImageListTask.ImageFilter getFilter(){
        ImageListTask.ImageFilter filter = new ImageListTask.ImageFilter();

        filter.imp = etImprint.getText().toString();
        filter.nam = etName.getText().toString();
        // TODO: Figure out how to get rid of hardcoded values to avoid problems in query
            // where color = "Choose color" etc
        String selectedColor = colorSpinner.getSelectedItem().toString();
        filter.col = (selectedColor.equals("Color")) ? "" : selectedColor;
        String selectedShape = shapeSpinner.getSelectedItem().toString();
        filter.shap = (selectedShape.equals("Shape")) ? "" : selectedShape;
        return filter;
    }

    public void populateRecyclerView(RxImagesResult rxImagesResult) {
        imageList.clear();
        if (rxImagesResult != null && rxImagesResult.getNlmRxImages() != null && rxImagesResult.getNlmRxImages().length > 0) {
            imageList.addAll(Arrays.asList(rxImagesResult.getNlmRxImages()));
        }
        else {
            Toast.makeText(this, "No results, try different input.", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    public void startRxInfoActivity(int rxcui) {
        Intent intent = new Intent(this, RxInfoActivity.class);
        intent.putExtra("rxcui", rxcui);
        startActivity(intent);
    }
}

