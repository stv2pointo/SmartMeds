package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.database.DBHelper;

public class EditMed extends AppCompatActivity {

    TextView textRXid;
    TextView textdosage;
    TextView textdoc;
    Button btnSave;
    Bundle bundle;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_med);
        textRXid = (TextView) findViewById(R.id.editRXid1);
        textdosage = (TextView) findViewById(R.id.editDosage1);
        textdoc = (TextView) findViewById(R.id.editDoc1);
        btnSave = (Button) findViewById(R.id.btnSave);
        String name = null;
        String dose= null;
        String doc= null;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        db = new DBHelper(this);


        bundle = getIntent().getExtras();
        if(bundle != null)
        {
            Cursor rs = db.getOnePillCursor(bundle.getInt("RXid"));
            rs.moveToFirst();

            while (!rs.isAfterLast()) {

                name = rs.getString(1);
                dose = rs.getString(2);
                doc = rs.getString(3);

                rs.moveToNext();

            }
            if (!rs.isClosed()) {
                rs.close();
            }

            textRXid.setText(name);
            textRXid.setFocusable(false);
            textRXid.setClickable(false);

            textdosage.setText(dose);
            textdosage.setEnabled(true);
            textdosage.setFocusableInTouchMode(true);
            textdosage.setClickable(true);


            textdoc.setText(doc);
            textdoc.setFocusableInTouchMode(true);
            textdoc.setEnabled(true);
            textdoc.setClickable(true);

        }
    }

    public void run(View view) {
//        int x = bundle.getInt("RXid");
//        Integer rxint = null;
//            if(x>0){
//                if(db.updatePill(x,textRXid.getText().toString(), textdosage.getText().toString(), textdoc.getText().toString(), null,null))
//                {
//                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(),MyMedsActivity.class);
//                    startActivity(intent);
//                } else{
//                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
//                }
//            } else{
//
//                try
//                {
//                    rxint = Integer.parseInt(textRXid.getText().toString());
//                }
//                catch(NumberFormatException nfe)
//                {
//                }
//
//                if(db.insertRX(rxint.toString(), textdosage.getText().toString(), textdoc.getText().toString(), null, null)){
//                    Toast.makeText(getApplicationContext(), "done",
//                            Toast.LENGTH_SHORT).show();
//                } else{
//                    Toast.makeText(getApplicationContext(), "not done",
//                            Toast.LENGTH_SHORT).show();
//                }
//                Intent intent = new Intent(getApplicationContext(),MyMedsActivity.class);
//                startActivity(intent);
//            }
        }
//    private void update(View view){
//        String RXID = textRXid.getText().toString();
//        String newDosage = textdosage.getText().toString();
//        String newDoc = textdoc.getText().toString();
//
//        db.updateMed(bundle.getInt("RXid"), RXID, newDosage, newDoc);
//    }
}
