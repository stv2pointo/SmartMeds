package com.stvjuliengmail.smartmeds.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stvjuliengmail.smartmeds.api.NameSearchResult;
import com.stvjuliengmail.smartmeds.api.REQUEST_BASE;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Steven on 4/7/2018.
 */

public class AutoCompletePillNameAdapter extends ArrayAdapter<String> implements Filterable {
    private ArrayList<String> data;
    private final String request = REQUEST_BASE.NAME_SUGGESTION;
    private String rawJson;
    private HttpURLConnection connection;

    public AutoCompletePillNameAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.data = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence userInput) {
                FilterResults results = new FilterResults();
                if (userInput != null) {
                    HttpURLConnection connection = null;
                    InputStream input = null;
                    try {
                        rawJson = getJsonFromApi(new URL(request + userInput.toString()));
                        ArrayList<String> list = getListFromRawJson();
                        results.values = list;
                        results.count = list.size();
                        data = list;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return results;
            }

            private String getJsonFromApi(URL url){
                String apiResult = null;
                try{
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int status = connection.getResponseCode();
                    switch (status) {
                        case 200:
                        case 201:
                            BufferedReader br =
                                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            apiResult = br.readLine();
                            break;
                        default:
                            Log.d("Server Error", "Bad status result");
                    }
                }
                catch (Exception e){

                }
               return apiResult;
            }

            private ArrayList<String> getListFromRawJson(){
                GsonBuilder gsonB = new GsonBuilder();
                Gson gson = gsonB.create();
                NameSearchResult resultObject;
                resultObject = gson.fromJson(rawJson, NameSearchResult.class);
                return new ArrayList<String>(Arrays.asList(resultObject.getSuggestionGroup().getSuggestionList().getSuggestion()));
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else notifyDataSetInvalidated();
            }
        };
    }
}