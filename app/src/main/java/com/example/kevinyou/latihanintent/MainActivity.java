package com.example.kevinyou.latihanintent;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinyou.latihanintent.data.DataMuridContract;
import com.example.kevinyou.latihanintent.data.DataMuridContract.DataMuridEntry;
import com.example.kevinyou.latihanintent.data.DataMuridDBHelper;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.example.kevinyou.latihanintent.data.TestDBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText name;
    private EditText phone;
    private Button submit;
    private Button call;
    private Button switchIntent;

    private RadioGroup genderRadioGroup;
    private String genderStr;

    private TextView shoeSize;
    private Button incrementShoeSize;
    private Button substractShoeSize;

    private CheckBox readingCheckBox;
    private CheckBox sportCheckBox;
    private CheckBox computerCheckBox;

    private int size;

    private TextView finalData;

    private int radioGroupId;

    private String nameStr;
    private String phoneStr;
    private String shoeSizeStr;
    private String data;
    private String hobbies;

    private FirebaseAnalytics mFirebaseAnalytics;

    //private String extraString = phone.getText().toString();

    ArrayList<CheckBox> items = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        genderRadioGroup = findViewById(R.id.genderRadioGroup);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        incrementShoeSize = findViewById(R.id.add);
        substractShoeSize = findViewById(R.id.substract);
        size = 35;

        readingCheckBox = findViewById(R.id.readingCheckBox);
        computerCheckBox = findViewById(R.id.computerCheckBox);
        sportCheckBox = findViewById(R.id.sportCheckBox);

        items.add(readingCheckBox);
        items.add(computerCheckBox);
        items.add(sportCheckBox);

        shoeSize = findViewById(R.id.shoeSize);
        finalData = findViewById(R.id.result);

        submit = findViewById(R.id.submit);
        call = findViewById(R.id.call);

        switchIntent = findViewById(R.id.switchIntent);

        //Toast.makeText(this, extraString, Toast.LENGTH_SHORT).show();

        incrementShoeSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size += 1;
                displayShoeSize(size);
            }
        });

        substractShoeSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size <= 20) {
                    Toast.makeText(getApplicationContext(), "Shoe size is too small", Toast.LENGTH_SHORT).show();
                    return;
                }

                size -= 1;
                displayShoeSize(size);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                submitData();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneStr = phone.getText().toString();

                if (phoneStr.length() < 5) {
                    Toast.makeText(MainActivity.this, "Phone number should be longer", Toast.LENGTH_SHORT).show();
                    return;
                }

                callPhone(phoneStr);
            }
        });

        switchIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passIntentData();
            }
        });

    }

    public void getData() {
        nameStr = name.getText().toString();
        phoneStr = phone.getText().toString();
        shoeSizeStr = shoeSize.getText().toString();

        radioGroupId = genderRadioGroup.getCheckedRadioButtonId();
        genderStr = ((RadioButton) findViewById(radioGroupId)).getText().toString();

        data = "Name : " + nameStr + "\n";
        data += "Phone : " + phoneStr + "\n";
        data += "Shoe size : " + shoeSizeStr + "\n";
        data += "Gender : " + genderStr + "\n";

        for (CheckBox item : items) {
            if(item.isChecked())
                data += item.getText().toString() + "\n";
        }

        displayFinalData(data);
    }

    public void passIntentData() {
        nameStr = name.getText().toString();
        phoneStr = phone.getText().toString();
        shoeSizeStr = shoeSize.getText().toString();

        radioGroupId = genderRadioGroup.getCheckedRadioButtonId();
        genderStr = ((RadioButton) findViewById(radioGroupId)).getText().toString();

        Intent intent = new Intent(MainActivity.this, ShowActivity.class);

        intent.putExtra("name", nameStr);
        intent.putExtra("phone", phoneStr);
        intent.putExtra("shoeSize", shoeSizeStr);
        intent.putExtra("gender", genderStr);

        for (CheckBox item : items) {
            if(item.isChecked())
                intent.putExtra("hobbies",item.getText().toString() + "\n");
        }

        startActivity(intent);
    }

    public void displayFinalData(String message) {
        finalData.setText(message);
    }

    public void displayShoeSize(int size) {
        shoeSize.setText(String.valueOf(size));
    }

    public void dialPhone (String phoneNumber) {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
        phoneIntent.setData(Uri.parse("tel:" + phoneNumber));

        if (phoneIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(phoneIntent);
        }
    }
    
    public void callPhone (String phoneNumber) {
        Intent newIntent = new Intent(Intent.ACTION_CALL);
        newIntent.setData(Uri.parse("tel:" + phoneNumber));

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                1);
        
        if (newIntent.resolveActivity(getPackageManager()) != null) {
            try {
                startActivity(newIntent);
            } catch (SecurityException e) {
                Toast.makeText(this, "Phone permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionShow:
                Intent actionIntent = new Intent(MainActivity.this, ShowActivity.class);
                actionIntent.putExtra(Intent.EXTRA_TEXT, "Data dari halaman sebelumnya");
                startActivity(actionIntent);

                return true;

            case R.id.actionDelete:
                Intent deleteIntent = new Intent(MainActivity.this, DeleteActivity.class);
                deleteIntent.putExtra(Intent.EXTRA_TEXT, "Delete all");
                startActivity(deleteIntent);

                return true;

            case R.id.actionToast:
                Toast.makeText(this, "You tapped on a toast!", Toast.LENGTH_SHORT).show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void submitData() {
        DataMuridDBHelper dataMuridDBHelper = new DataMuridDBHelper(this);

        SQLiteDatabase db = dataMuridDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataMuridEntry.COLUMN_NAME, nameStr);
        values.put(DataMuridEntry.COLUMN_PHONE, phoneStr);

        long newRowId = db.insert(DataMuridEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error saving", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Successfully saved " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }


}