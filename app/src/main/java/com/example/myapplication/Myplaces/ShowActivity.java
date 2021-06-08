package com.example.myapplication.Myplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.DatabaseHandler;
import com.example.myapplication.Model.Place;

public class ShowActivity extends AppCompatActivity {

    private TextView latitude, longitude, title;

    private Button delete, edit;
    private DatabaseHandler db;
    private Bundle extras;
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        latitude = findViewById(R.id.textViewLatitude);
        longitude = findViewById(R.id.textViewLongitude);
        title = findViewById(R.id.textViewName);
        delete = findViewById(R.id.buttonDelete);
        edit = findViewById(R.id.buttonEdit);

        db = new DatabaseHandler(this);
        extras = getIntent().getExtras();

        if(extras != null) {
            latitude.setText(String.valueOf(extras.getDouble("latitude")));
            longitude.setText(String.valueOf(extras.getDouble("longitude")));
            title.setText(String.valueOf(extras.getString("title")));

            Float testNum = extras.getFloat("id");
            num = Math.round(testNum);

            Toast.makeText(this, num+"",Toast.LENGTH_SHORT).show();
        }

        delete.setOnClickListener(v -> {
            if(num != 0) {
                db.deletePlace(new Place(num,latitude.getText().toString()
                        ,longitude.getText().toString()
                        ,title.getText().toString(),null));

                Intent intent = new Intent(ShowActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edit.setOnClickListener(v -> {
            Intent intent = new Intent(ShowActivity.this, EditActivity.class);
            intent.putExtra("id", num);
            intent.putExtra("title",title.getText().toString());
            intent.putExtra("latitude",latitude.getText().toString());
            intent.putExtra("longitude",longitude.getText().toString());
            startActivity(intent);
        });

    }
}