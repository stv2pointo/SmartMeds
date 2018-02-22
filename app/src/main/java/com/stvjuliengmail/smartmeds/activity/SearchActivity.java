package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.RecyclerViewItemClickListener;
import com.stvjuliengmail.smartmeds.adapter.ResultsAdapter;
import com.stvjuliengmail.smartmeds.api.ImageListTask;
import com.stvjuliengmail.smartmeds.model.NlmRxImage;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.String.*;

public class SearchActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    Button btnLoadList;
    Spinner colorSpinner, shapeSpinner;
    EditText etName, etImprint;
    RecyclerView recyclerView;
    ResultsAdapter adapter;
    ArrayList<NlmRxImage> imageList = new ArrayList<>();
    boolean isInitialDisplayColor;
    boolean isInitialDisplayShape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeUiComponents();
    }

    private void initializeUiComponents() {
        recyclerView = (RecyclerView) findViewById(R.id.recVwResultList);
        btnLoadList = (Button) findViewById(R.id.btnLoadList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etName = (EditText) findViewById(R.id.etName);
        etImprint = (EditText) findViewById(R.id.etImprint);

        wireUpColorSpinner();

        wireUpShapeSpinner();

        wireAdapterToRecyclerView();

        wireUpSearchButton();
    }


    private void wireUpColorSpinner() {
        colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
        colorSpinner.setSelection(0);
        isInitialDisplayColor = true;
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isInitialDisplayColor) {
                    isInitialDisplayColor = false;
                } else {
                    search();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // obligatory override
            }
        });
    }

    private void wireUpShapeSpinner() {
        shapeSpinner = (Spinner) findViewById(R.id.shapeSpinner);
        shapeSpinner.setSelection(0);
        isInitialDisplayShape = true;
        shapeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isInitialDisplayShape) {
                    isInitialDisplayShape = false;
                } else {
                    search();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // obligatory override
            }
        });
    }

    private void wireAdapterToRecyclerView() {
        adapter = new ResultsAdapter(imageList, R.layout.list_search_result,
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
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(this.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public ImageListTask.ImageFilter getFilter() {
        ImageListTask.ImageFilter filter = new ImageListTask.ImageFilter();

        filter.imprint = etImprint.getText().toString();
        String nameInput = etName.getText().toString();
        if (nameInput != null && nameInput.length() > 0 && nameInput.length() < 3) {
            Toast.makeText(this, "Names must be more than 2 letters", Toast.LENGTH_SHORT).show();
            nameInput = "";
            etName.setText("");
        }
        filter.name = nameInput;
        /** TODO: Figure out how to get rid of hardcoded values to avoid problems in query
         where color = "Choose color" etc **/
        String selectedColor = colorSpinner.getSelectedItem().toString();
        filter.color = (selectedColor.equals("Pill Color")) ? "" : selectedColor;
        String selectedShape = shapeSpinner.getSelectedItem().toString();
        filter.shape = (selectedShape.equals("Pill Shape")) ? "" : selectedShape;
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

