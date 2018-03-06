package com.stvjuliengmail.smartmeds.activity;

import android.app.Activity;

import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;


public class MyMedActivity extends Activity {
    int from_Where_I_Am_Coming = 0;

    TextView RXid;
    TextView dosage;
    TextView doc;

    int id_To_Update = 0;

    protected void onCreate(Integer id) {
        setContentView(R.layout.activity_my_med);
        RXid = (TextView) findViewById(R.id.editRXid1);
        dosage = (TextView) findViewById(R.id.editDosage1);
        doc = (TextView) findViewById(R.id.editDoc1);

//        DBHelper mydb = new DBHelper(this);

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            int Value = extras.getInt("id");
//
//            if (Value > 0) {
                //means this is the view part not the add meds part.
//                Cursor rs = mydb.getOnePillCursor(id);
//                id_To_Update = id;
//                rs.moveToFirst();
//
//                String rxID = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_RXCUI));
//                String dose = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_DOSAGE));
//                String doctor = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_DOCTOR));
//
//                if (!rs.isClosed()) {
//                    rs.close();
//                }
//                Button b = (Button) findViewById(R.id.button1);
//                b.setVisibility(View.INVISIBLE);
//
//                RXid.setText((CharSequence) rxID);
//                RXid.setFocusable(false);
//                RXid.setClickable(false);
//
//                dosage.setText((CharSequence) dose);
//                dosage.setFocusable(false);
//                dosage.setClickable(false);
//
//                doc.setText((CharSequence) doctor);
//                doc.setFocusable(false);
//                doc.setClickable(false);


            }
        }
//    }
//}