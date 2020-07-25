package com.example.kohendakanne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kohendakanne.Models.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RawMapViewDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "RawMapViewDemoActivity";

    private GoogleMap map;
    private Location location;
    String resName, resDistrict;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw_map_view_demo);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        firebaseFirestore = FirebaseFirestore.getInstance();
//
//        Intent intent = getIntent();
//        resName = intent.getStringExtra("Restaurant_Name");
//        resDistrict = intent.getStringExtra("district");
//
//                CollectionReference stores = firebaseFirestore.collection("Restaurants")
//                .document(resName + "-" + resDistrict)
//                .collection("details");
//
//        stores.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.i(TAG,document.getData().get("GeoPoint").toString());
//                        GeoPoint geoPoint = document.getGeoPoint("GeoPoint");
//                        double lat = geoPoint.getLatitude();
//                        double lng = geoPoint.getLongitude ();
//                        latLng = new LatLng(lat, lng);
//                    }
//                } else {
//                    Log.w(TAG, "Error getting documents.", task.getException());
//                }
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (CurrentUser == null) {
            sendToLogin();
        } else {
            // No user is signed in
        }
    }

    private void sendToLogin() {
        Intent goLogin = new Intent(RawMapViewDemoActivity.this, LoginActivity.class);
        startActivity(goLogin);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

//        LatLng Srilanka = new LatLng(5.943779,80.549317);
//        map.addMarker(new MarkerOptions().position(Srilanka));
//        map.moveCamera(CameraUpdateFactory.newLatLng(Srilanka));

//        LatLng Srilanka = new LatLng(5.943779,80.549317);
//        map.addMarker(new MarkerOptions().position(Srilanka));
//        map.moveCamera(CameraUpdateFactory.newLatLng(Srilanka));

    }

}
