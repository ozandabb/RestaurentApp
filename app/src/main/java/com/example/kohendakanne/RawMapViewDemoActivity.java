package com.example.kohendakanne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

public class RawMapViewDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private GeoPoint geoPoint;
    String resName, resDistrict;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw_map_view_demo);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        resName = intent.getStringExtra("Restaurant_Name");
        resDistrict = intent.getStringExtra("district");

        firebaseFirestore.collection("Users").document(resName + "-" + resDistrict).collection("details").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng srilanka = new LatLng(0, 0);
        map.addMarker(new MarkerOptions().position(srilanka));
        map.moveCamera(CameraUpdateFactory.newLatLng(srilanka));

    }

}
