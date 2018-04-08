package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.AutoCompletePillNameAdapter;
import com.stvjuliengmail.smartmeds.adapter.RecyclerViewItemClickListener;
import com.stvjuliengmail.smartmeds.adapter.ResultsAdapter;
import com.stvjuliengmail.smartmeds.api.ImageListTask;
import com.stvjuliengmail.smartmeds.model.NlmRxImage;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import java.util.ArrayList;
import java.util.Arrays;


public class SearchActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ImageButton btnLoadList;
    private Spinner colorSpinner, shapeSpinner;
    private AutoCompleteTextView autoName;
    private EditText etImprint;
    private Button btnShowFilters;
    private RecyclerView recyclerView;
    private LinearLayout filtersView;
    private LinearLayout filtersWidget;
    private ResultsAdapter adapter;
    private ArrayList<NlmRxImage> imageList = new ArrayList<>();
//    private boolean isInitialDisplayColor;
//    private boolean isInitialDisplayShape;
    private String defaultColorValue;
    private String defaultShapeValue;
    private AutoCompletePillNameAdapter autoCompletePillNameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        try{
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        catch (Exception e){
//            Log.d(TAG, e.getMessage());
//        }

        initializeUiComponents();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id==android.R.id.home) {
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void initializeUiComponents() {
        recyclerView = (RecyclerView) findViewById(R.id.recVwResultList);
        btnLoadList = (ImageButton) findViewById(R.id.btnLoadList);
        etImprint = (EditText) findViewById(R.id.etImprint);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        filtersView = (LinearLayout)findViewById(R.id.filters_view);
        btnShowFilters = (Button) findViewById(R.id.btnShowFilters);
        filtersWidget = (LinearLayout) findViewById(R.id.filters_widget);
        autoName = (AutoCompleteTextView) findViewById(R.id.autoName);
        autoCompletePillNameAdapter = new AutoCompletePillNameAdapter(this, android.R.layout.simple_list_item_1);
        autoName.setAdapter(autoCompletePillNameAdapter);

        wireUpColorSpinner();
        wireUpShapeSpinner();
        wireAdapterToRecyclerView();
        wireUpSearchButton();
        wireUpShowFiltersButton();
    }

    private void wireUpColorSpinner() {
        colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
        colorSpinner.setSelection(0);
        defaultColorValue = (String) colorSpinner.getItemAtPosition(0);
//        isInitialDisplayColor = true;
        //            //removed to allow for multiple options selected before searching
//        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (isInitialDisplayColor) {
//                    isInitialDisplayColor = false;
//                } else {
//                    search();
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // obligatory override
//            }
//        });
    }

    private void wireUpShapeSpinner() {
        shapeSpinner = (Spinner) findViewById(R.id.shapeSpinner);
        shapeSpinner.setSelection(0);
        defaultShapeValue = (String) shapeSpinner.getItemAtPosition(0);
//        isInitialDisplayShape = true;
        //            //removed to allow for multiple options selected before searching
//        shapeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (isInitialDisplayShape) {
//                    isInitialDisplayShape = false;
//                } else {
//                    search();
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // obligatory override
//            }
//        });
    }

    private void wireAdapterToRecyclerView() {
        adapter = new ResultsAdapter(imageList, R.layout.rv_row_search_result,
                getApplicationContext());
        //Create custom interface object and send it to adapter for clickable list items
        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startRxInfoActivity(imageList.get(position));
            }
            @Override
            public void onItemLongClick(View view, int position) {
                // TODO: Use long click to open option to save to myMeds
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void wireUpSearchButton() {
        btnLoadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }

    public void search() {
        hideKeyboard();
        new ImageListTask(this, getFilter()).execute("");
        hideFilters();
    }

    private void wireUpShowFiltersButton() {
        btnShowFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilters();
            }
        });
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(this.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void hideFilters(){
        filtersView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,0));
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,90));
//        filtersWidget.setVisibility(View.VISIBLE);
        filtersWidget.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,10));
    }

    private void showFilters(){
//        filtersWidget.setVisibility(View.INVISIBLE);
        filtersWidget.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,0));
        filtersView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,30));
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,70));
    }

    public ImageListTask.ImageFilter getFilter() {
        ImageListTask.ImageFilter filter = new ImageListTask.ImageFilter();

        filter.imprint = etImprint.getText().toString();
        String nameInput = autoName.getText().toString();
        if (nameInput != null && nameInput.length() > 0 && nameInput.length() < 3)
        {
            Toast.makeText(this, "Names must be more than 2 letters", Toast.LENGTH_SHORT).show();
            nameInput = "";
            autoName.setText("");
        }
        filter.name = nameInput;

        String selectedColor = colorSpinner.getSelectedItem().toString();
        filter.color = (selectedColor.equals(defaultColorValue)) ? "" : selectedColor;
        String selectedShape = shapeSpinner.getSelectedItem().toString();
        filter.shape = (selectedShape.equals(defaultShapeValue)) ? "" : selectedShape;
        return filter;
    }

    public void populateRecyclerView(RxImagesResult rxImagesResult) {
        imageList.clear();
        if (rxImagesResult != null && rxImagesResult.getNlmRxImages() != null && rxImagesResult.getNlmRxImages().length > 0) {
            imageList.addAll(Arrays.asList(rxImagesResult.getNlmRxImages()));
            Toast.makeText(this, Integer.toString(rxImagesResult.getNlmRxImages().length) + " results were found.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No results, try different input.", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    public void startRxInfoActivity(NlmRxImage nlmRxImage) {
        int _rxcui = nlmRxImage.getRxcui();
        String _name = nlmRxImage.getName();
        String _imageUrl = nlmRxImage.getImageUrl();
        Log.d(TAG, "startRxInfo, rxcui is " + Integer.toString(_rxcui));
        Intent intent = new Intent(this, RxInfoActivity.class);
        intent.putExtra("rxcui", _rxcui);
        intent.putExtra("name", _name);
        intent.putExtra("imageUrl", _imageUrl);
        startActivity(intent);
    }
}

