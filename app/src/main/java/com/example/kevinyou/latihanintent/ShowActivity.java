package com.example.kevinyou.latihanintent;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kevinyou.latihanintent.data.DataMuridContract;
import com.example.kevinyou.latihanintent.data.DataMuridContract.DataMuridEntry;
import com.example.kevinyou.latihanintent.data.DataMuridDBHelper;

import org.w3c.dom.Text;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

//        TextView name = findViewById(R.id.name);
//        TextView phone = findViewById(R.id.phone);
//        TextView gender = findViewById(R.id.gender);
//        TextView hobbies = findViewById(R.id.hobbies);
//        TextView shoeSize = findViewById(R.id.shoeSize);

//        Intent intent = getIntent();
//        Bundle bd = intent.getExtras();
//
//        String nameStr = (String) bd.get("name");
//        String phoneStr = (String) bd.get("phone");
//        String genderStr = (String) bd.get("gender");
//        String hobbiesStr = (String) bd.get("hobbies");
//        String shoeSizeStr = (String) bd.get("shoeSize");
//
//        name.setText(nameStr);
//        phone.setText(phoneStr);
//        gender.setText(genderStr);
//        hobbies.setText(hobbiesStr);
//        shoeSize.setText(shoeSizeStr);

        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        DataMuridDBHelper dataMuridDBHelper = new DataMuridDBHelper(this);

        SQLiteDatabase db = dataMuridDBHelper.getWritableDatabase();

        String[] projection = {
                DataMuridContract.DataMuridEntry._ID,
                DataMuridContract.DataMuridEntry.COLUMN_NAME,
                DataMuridContract.DataMuridEntry.COLUMN_PHONE
        };

        Cursor cursor = db.query(
                DataMuridContract.DataMuridEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null
        );

        TextView displayView = findViewById(R.id.dataSiswa);

        try {
            displayView.setText("Table datasiswa berisi : " + cursor.getCount() + " records");

            displayView.append(DataMuridEntry._ID + " - " +
                                DataMuridEntry.COLUMN_NAME + " - " +
                                DataMuridEntry.COLUMN_PHONE + "\n");

            int idColumnIndex = cursor.getColumnIndex(DataMuridEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(DataMuridEntry.COLUMN_NAME);
            int phoneColumnIndex = cursor.getColumnIndex(DataMuridEntry.COLUMN_PHONE);

            while(cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentPhone = cursor.getString(phoneColumnIndex);

                displayView.append("\n" + currentID + " - " +
                                        currentName + " - " +
                                        currentPhone + "\n");
            }
        } finally {
            cursor.close();
        }
    }
}
