package com.example.myapplication.Myplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Controller.DatabaseHandler;
import com.example.myapplication.Model.Place;

public class AddActivity extends AppCompatActivity {

    private TextView latitude, longitude;
    private EditText title;
    private Button save;
    private DatabaseHandler db;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        latitude = findViewById(R.id.latitudetext);
        longitude = findViewById(R.id.longitudetext);
        title = findViewById(R.id.titleEditText);
        save = findViewById(R.id.buttonSave);

        db = new DatabaseHandler(this);
        extras = getIntent().getExtras();

        if(extras != null) {
            latitude.setText(String.valueOf(extras.getDouble("latitude")));
            longitude.setText(String.valueOf(extras.getDouble("longitude")));
        }

        save.setOnClickListener(v -> {

            db.addPlace(new Place(latitude.getText().toString()
            ,longitude.getText().toString()
                    ,title.getText().toString()
                    ,null));

            Intent intent = new Intent(AddActivity.this, MapsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}