package com.stvjuliengmail.smartmeds.database;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.stvjuliengmail.smartmeds.activity.MyMedsActivity;
import com.stvjuliengmail.smartmeds.api.MyInteractionsTask;
import com.stvjuliengmail.smartmeds.model.MyMed;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 3/8/2018.
 */

public class GetDBMedsTask extends AsyncTask <String, Integer, ArrayList<MyMed>>{
    private final WeakReference<MyMedsActivity> weakActivity;


    public GetDBMedsTask(MyMedsActivity myMedsActivity) {
        this.weakActivity = new WeakReference<>(myMedsActivity);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<MyMed> doInBackground(String... params) {
        // Get singleton instance of database
        SmartMedsDbOpenHelper dbOpenHelper = SmartMedsDbOpenHelper.getInstance(weakActivity.get());
        return dbOpenHelper.getAllMyMeds();
    }

    @Override
    protected void onPostExecute(ArrayList<MyMed> myMeds) {
        super.onPostExecute(myMeds);

        MyMedsActivity activity = weakActivity.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.setMyMeds(myMeds);
            ArrayList<String> rxcuiList = new ArrayList<String>();
            for (MyMed myMed : myMeds) {
                rxcuiList.add(myMed.getRxcui());
            }
            new MyInteractionsTask(activity, rxcuiList.toArray(new String[rxcuiList.size()])).execute("");
        }

    }
}
