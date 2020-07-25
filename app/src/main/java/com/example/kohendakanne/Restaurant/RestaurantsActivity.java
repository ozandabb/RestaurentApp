package com.example.kohendakanne.Restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kohendakanne.AccountSetting;
import com.example.kohendakanne.MainActivity;
import com.example.kohendakanne.Map.MapsActivity;
import com.example.kohendakanne.Models.Restaurant;
import com.example.kohendakanne.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    private RecyclerView restaurant_list;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter, searchRecycleAdapter;
    private Spinner spinner;
    private EditText restaurantSearch;
    private ImageView searchBtn;

    private static final int ACTIVITY_NUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        setupBottomNavBar();

        firebaseFirestore = FirebaseFirestore.getInstance();
        restaurant_list = findViewById(R.id.restaurant_list);
        spinner = findViewById(R.id.spinner1);
        restaurantSearch = findViewById(R.id.restaurant_search);
        searchBtn = findViewById(R.id.searchBtn);

        spinner.setOnItemSelectedListener(this);

        Query query = firebaseFirestore.collection("RestaurantsNames").orderBy("restaurant_name");
        FirestoreRecyclerOptions<Restaurant> options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                .setQuery(query, Restaurant.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<Restaurant, RestaurantViewHolder>(options) {
            @NonNull
            @Override
            public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_restaurant_item, parent, false);

                return new RestaurantViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position, @NonNull Restaurant model) {
                holder.restaurant_name.setText(model.getRestaurant_name());
                holder.restaurant_district.setText(model.getDistrict());
                Picasso.get().load(model.getImage_url()).into(holder.restaurant_image);
            }
        } ;

        restaurant_list.setHasFixedSize(true);
        restaurant_list.setLayoutManager(new LinearLayoutManager(this));
        restaurant_list.setAdapter(adapter);

        Log.d("RestaurantsActivity", "onCreate: " + adapter);


        restaurantSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Query query = firebaseFirestore.collection("RestaurantsNames").whereEqualTo("restaurant_name",s.toString()).orderBy("restaurant_name");
                FirestoreRecyclerOptions<Restaurant> options2 = new FirestoreRecyclerOptions.Builder<Restaurant>()
                        .setQuery(query, Restaurant.class)
                        .build();

                adapter.updateOptions(options2);
            }
        });

        String Text = spinner.getSelectedItem().toString();
        Log.d("RestaurantsActivity", "onCreate: "+ Text);

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                Query query3 = firebaseFirestore.collection("RestaurantsNames").whereEqualTo("district",Text).orderBy("restaurant_name");
//                FirestoreRecyclerOptions<Restaurant> options3 = new FirestoreRecyclerOptions.Builder<Restaurant>()
//                        .setQuery(query3, Restaurant.class)
//                        .build();
//
//                adapter.updateOptions(options3);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        firebaseFirestore.collection("Users").orderBy("district").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurant_name;
        private TextView restaurant_district;
        private ImageView restaurant_image;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurant_name = itemView.findViewById(R.id.restaurant_name);
            restaurant_district = itemView.findViewById(R.id.restaurant_district);
            restaurant_image = itemView.findViewById(R.id.restaurant_image);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void setupBottomNavBar(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent homeIntent = new Intent(RestaurantsActivity.this,MainActivity.class);
                        startActivity(homeIntent);
                        finish();
                        break;
                    case R.id.ic_category:
                        Intent ExerciseIntent = new Intent(RestaurantsActivity.this, RestaurantsActivity.class);
                        startActivity(ExerciseIntent);
                        finish();
                        break;
                    case R.id.ic_add:
                        Intent runIntent = new Intent(RestaurantsActivity.this, MapsActivity.class);
                        startActivity(runIntent);
                        finish();
                        break;
                    case R.id.ic_device:
                        Intent deviceIntent = new Intent(RestaurantsActivity.this, AccountSetting.class);
                        startActivity(deviceIntent);
                        finish();
                        break;
                }

                return false;
            }
        });
    }


}
