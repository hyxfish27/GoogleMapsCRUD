package com.example.myapplication.Myplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Controller.DatabaseHandler;
import com.example.myapplication.Model.Place;

public class PostActivity extends AppCompatActivity {

//    private TextView latitude, longitude;
    private String latitude, longitude;
    private EditText title;
    private Button save;
    private DatabaseHandler db;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

//        latitude = findViewById(R.id.latitudetext);
//        longitude = findViewById(R.id.longitudetext);
        title = findViewById(R.id.titleEditText);
        save = findViewById(R.id.buttonSave);

        db = new DatabaseHandler(this);
        extras = getIntent().getExtras();

        if(extras != null) {
            latitude = String.valueOf(extras.getDouble("latitude"));
            longitude = String.valueOf(extras.getDouble("longitude"));
        }

        save.setOnClickListener(v -> {
            db.addPlace(new Place(latitude,longitude
                    ,title.getText().toString(),null));

            Intent intent = new Intent(PostActivity.this, MapsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}