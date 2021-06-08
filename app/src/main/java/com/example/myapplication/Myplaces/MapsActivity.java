package com.example.myapplication.Myplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.example.myapplication.databinding.ActivityMapsBinding;

import com.example.myapplication.Controller.DatabaseHandler;
import com.example.myapplication.Model.Place;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener{

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    Location lastLocation;

    private GoogleMap mMap;
    DatabaseHandler db;
    Dialog dialog;

    private Button save, choose;
    private String latitude,longitude;
    private EditText title;
    private TextView textviewtitle;
    //private ActivityMapsBinding binding;

    private LatLng es = new LatLng(22.996653432836347, 120.22164848778881);
    List<Marker> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityMapsBinding.inflate(getLayoutInflater());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);

        mMap = googleMap;
        db = new DatabaseHandler(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);

        markerList = new ArrayList<>();
        List<Place> placeList = db.getAllPlaces();

        int height = 200;
        int width = 200;
        BitmapDrawable NewsIcon = (BitmapDrawable)getResources().getDrawable(R.drawable.icon_sale);
        Bitmap newsicon = NewsIcon.getBitmap();
        Bitmap news_icon_Marker = Bitmap.createScaledBitmap(newsicon, width, height, false);

        BitmapDrawable LeisureIcon = (BitmapDrawable)getResources().getDrawable(R.drawable.icon_leisure);
        Bitmap leisureicon = LeisureIcon.getBitmap();
        Bitmap leisure_icon_Marker = Bitmap.createScaledBitmap(leisureicon, width, height, false);

        BitmapDrawable SportIcon = (BitmapDrawable)getResources().getDrawable(R.drawable.icon_sport);
        Bitmap sporticon = SportIcon.getBitmap();
        Bitmap sport_icon_Marker = Bitmap.createScaledBitmap(sporticon, width, height, false);

        BitmapDrawable ClassIcon = (BitmapDrawable)getResources().getDrawable(R.drawable.icon_class);
        Bitmap classicon = ClassIcon.getBitmap();
        Bitmap class_icon_Marker = Bitmap.createScaledBitmap(classicon, width, height, false);

        BitmapDrawable PublicIcon = (BitmapDrawable)getResources().getDrawable(R.drawable.icon_public);
        Bitmap publicicon = PublicIcon.getBitmap();
        Bitmap public_icon_Marker = Bitmap.createScaledBitmap(publicicon, width, height, false);

        for(Place p: placeList){
            String myInfo = "ID: " + p.getId() + "Latitude: " + p.getPlatitude()
                    + "Longitude: " + p.getPlongitude() + "Title: " + p.getTitle();
            //Log.d("myInfo", myInfo);

            markerList.add(mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(p.getPlatitude())
                            ,Double.parseDouble(p.getPlongitude()))).title(p.getTitle())
                    .zIndex(p.getId()).snippet(p.getSnippet())
                    .icon(BitmapDescriptorFactory.fromBitmap(news_icon_Marker))));
        }

        //markerList.add(mMap.addMarker(new MarkerOptions()
        //        .position(es).title("Hello Tainan")));


        for(Marker m : markerList) {
            // Add markers and move the camera
            LatLng latLng = new LatLng(m.getPosition().latitude,m.getPosition().longitude);
            //mMap.addMarker(new MarkerOptions().position(latLng));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(es)      // Sets the center of the map to Mountain View
                    .zoom(16)                   // Sets the zoom
                    .tilt(60)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        requestLocationDialog();
        checkPermissionAndRequestLocation();
    }

    private TextView poster;
    @Override
    public boolean onMarkerClick(Marker marker) {
        //Toast.makeText(this,marker.getPosition().toString(),Toast.LENGTH_SHORT).show();
        //return false;
        dialog = new Dialog(this);

        dialog.setContentView(R.layout.activity_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button btnLike = dialog.findViewById(R.id.btnLike);

        textviewtitle = dialog.findViewById(R.id.textViewTitle);
        poster = dialog.findViewById(R.id.Poster);
        textviewtitle.setText(marker.getTitle());
        poster.setText(marker.getSnippet());

        imageViewClose.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(MapsActivity.this, "Close Page", Toast.LENGTH_SHORT).show();
        });
        dialog.show();
//        Intent intent = new Intent(MapsActivity.this, ShowActivity.class);
//        intent.putExtra("latitude", marker.getPosition().latitude);
//        intent.putExtra("longitude", marker.getPosition().longitude);
//        intent.putExtra("title", marker.getTitle());
//        intent.putExtra("id", marker.getZIndex());
//        startActivity(intent);
        return false;
    }

    private EditText personName;
    @Override
    public void onMapLongClick(LatLng latLng) {
//        mMap.addMarker(new MarkerOptions().position(latLng).title("new Marker")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        dialog = new Dialog(this);

        dialog.setContentView(R.layout.activity_post);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        save = dialog.findViewById(R.id.buttonSave);
        choose = dialog.findViewById(R.id.btnChoose);

        latitude = String.valueOf(latLng.latitude);
        longitude = String.valueOf(latLng.longitude);
        title = dialog.findViewById(R.id.titleEditText);
        personName = dialog.findViewById(R.id.PersonName);
        //DatabaseHandler db = new DatabaseHandler(this);

        imageViewClose.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(MapsActivity.this, "Close Page", Toast.LENGTH_SHORT).show();
        });

        save.setOnClickListener(v -> {
            db.addPlace(new Place(latitude,longitude
                    ,title.getText().toString()
                    ,personName.getText().toString()));
            dialog.dismiss();
            Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
            startActivity(intent);
            finish();
        });
        dialog.show();

//        Intent intent = new Intent(MapsActivity.this, PostActivity.class);
//        intent.putExtra("latitude",latLng.latitude);
//        intent.putExtra("longitude",latLng.longitude);
//        startActivity(intent);
    }



    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for(Location location: locationResult.getLocations()){
                lastLocation = location;
            }

//            if(marker!=null)
//            {
//                marker.remove();
//            }

            LatLng latLng = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
//            markerOptions.title("Current Location");
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//
//            marker = mMap.addMarker(markerOptions);

            // Move the camera instantly to Sydney with a zoom of 15.
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kfc, 16));

            // Zoom in, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.zoomIn());

            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 10000, null);

            //Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)      // Sets the center of the map to Mountain View
                    .zoom(18)                   // Sets the zoom
                    .tilt(65)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    };

    private void checkPermissionAndRequestLocation()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                        locationCallback, Looper.myLooper());

                mMap.setMyLocationEnabled(true);
            }
            else
            {
                checkForLocationPermission();
            }
        }
        else
        {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                    locationCallback, Looper.myLooper());

            mMap.setMyLocationEnabled(true);
        }
    }

    private void checkForLocationPermission()
    {
        if(!checkPermissions())
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION},1001);
        }
    }

    private boolean checkPermissions()
    {
        if(Build.VERSION.SDK_INT >= 23)
        {
            int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            return result == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestLocationDialog()
    {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(locationSettingsRequest);
        task.addOnSuccessListener(locationSettingsResponse -> {
        });

        task.addOnFailureListener(e -> {
        });
    }
}