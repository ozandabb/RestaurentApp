package com.example.kohendakanne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.kohendakanne.Models.MenuItems;
import com.example.kohendakanne.Models.Restaurant;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    String resName, resID;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        resName = getIntent().getStringExtra("Restaurant_Name");
        resID = getIntent().getStringExtra("restaurant_id");

        firebaseFirestore = FirebaseFirestore.getInstance();


       firebaseFirestore.collection("RestaurantsNames").document().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()){

               }
           }
       });



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        LatLng srilanka = new LatLng(61.588810, 98.858847);
        map.addMarker(new MarkerOptions().position(srilanka).title("Sri Lanka"));
        map.moveCamera(CameraUpdateFactory.newLatLng(srilanka));

    }

}
