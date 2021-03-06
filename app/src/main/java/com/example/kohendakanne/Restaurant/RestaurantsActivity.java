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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kohendakanne.Adapters.ResAdapter;
import com.example.kohendakanne.MainActivity;
import com.example.kohendakanne.MapActivity;
import com.example.kohendakanne.Models.Restaurant;
import com.example.kohendakanne.ProfileActivity;
import com.example.kohendakanne.R;
import com.example.kohendakanne.RegisterActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class RestaurantsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "RestaurantsActivity";

    private RecyclerView restaurant_list;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private Spinner spinner;
    private EditText restaurantSearch;
    private static final int ACTIVITY_NUM = 1;

    private CollectionReference restaurantAll ;
//    ResAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        setupBottomNavBar();

        firebaseFirestore = FirebaseFirestore.getInstance();
        restaurant_list = findViewById(R.id.restaurant_list);
        restaurantSearch = findViewById(R.id.restaurant_search);
        spinner = findViewById(R.id.spinner1);

//        restaurantAll = firebaseFirestore.collection("RestaurantsNames");
//
//        setUpRecycleView();










//
//        searchBtn = findViewById(R.id.searchBtn);

        spinner.setOnItemSelectedListener(this);

        Query query = firebaseFirestore.collection("RestaurantsNames").orderBy("restaurant_name");
        final FirestoreRecyclerOptions<Restaurant> options = new FirestoreRecyclerOptions.Builder<Restaurant>()
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
            protected void onBindViewHolder(@NonNull RestaurantViewHolder holder, final int position, @NonNull final Restaurant model) {
                holder.restaurant_name.setText(model.getRestaurant_name());
                holder.restaurant_district.setText(model.getDistrict());
                Picasso.get().load(model.getImage_url()).into(holder.restaurant_image);
                holder.single_itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent goSingle = new Intent(RestaurantsActivity.this, SingleRestaurantActivity.class);
                        goSingle.putExtra("Restaurant_Name" ,model.getRestaurant_name() );
                        goSingle.putExtra("restaurant_id" ,model.getRestaurant_id() );
                        goSingle.putExtra("district",model.getDistrict() );
                        goSingle.putExtra("image",model.getImage_url() );
                        startActivity(goSingle);

                    }
                });

                holder.contactResBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent dial = new Intent(Intent.ACTION_DIAL);
                        dial.setData(Uri.parse("tel:" + model.getContact()));
                        startActivity(dial);
                    }
                });

            }
        } ;



        restaurant_list.setHasFixedSize(true);
        restaurant_list.setLayoutManager(new LinearLayoutManager(this));
        restaurant_list.setAdapter(adapter);
//

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String Text = spinner.getSelectedItem().toString();

                Query query3 = firebaseFirestore.collection("RestaurantsNames").whereEqualTo("district",Text);

                Log.d(TAG, "onItemSelected: " + query3.toString());
                FirestoreRecyclerOptions<Restaurant> options3 = new FirestoreRecyclerOptions.Builder<Restaurant>()
                        .setQuery(query3, Restaurant.class)
                        .build();

                if (Text.equals("Select a District")){
                    Query query = firebaseFirestore.collection("RestaurantsNames").orderBy("restaurant_name");
                    FirestoreRecyclerOptions<Restaurant> options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                            .setQuery(query, Restaurant.class)
                            .build();
                    adapter.updateOptions(options);
                }
                else {
                    adapter.updateOptions(options3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    public void setUpRecycleView(){
//        Query query = restaurantAll.orderBy("restaurant_name");
//        FirestoreRecyclerOptions<Restaurant> options = new FirestoreRecyclerOptions.Builder<Restaurant>()
//                .setQuery(query, Restaurant.class)
//                .build();
//
//        adapter = new ResAdapter(options);
//        restaurant_list.setHasFixedSize(true);
//        restaurant_list.setLayoutManager(new LinearLayoutManager(this));
//        restaurant_list.setAdapter(adapter);
//
//        adapter.setOnItemClickListener(new ResAdapter.onItemClickListener() {
//            @Override
//            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//                Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);
//                String id = documentSnapshot.getId();
//
//                Intent goSingle = new Intent(RestaurantsActivity.this, SingleRestaurantActivity.class);
//                goSingle.putExtra("Restaurant_Name" ,restaurant.getRestaurant_name());
//                goSingle.putExtra("restaurant_id" ,restaurant.getRestaurant_id() );
//                goSingle.putExtra("district",restaurant.getDistrict() );
//                goSingle.putExtra("image",restaurant.getImage_url() );
//                goSingle.putExtra("id", id);
//                startActivity(goSingle);
//
//            }
//        });
//
//    }





//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        mListener = listener;
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//
//
    private class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurant_name;
        private TextView restaurant_district;
        private ImageView restaurant_image;
        private Button contactResBtn;
        private LinearLayout single_itemView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurant_name = itemView.findViewById(R.id.restaurant_name);
            restaurant_district = itemView.findViewById(R.id.restaurant_district);
            restaurant_image = itemView.findViewById(R.id.restaurant_image);
            single_itemView = itemView.findViewById(R.id.single_item);
            contactResBtn = itemView.findViewById(R.id.contactResBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

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
                        Intent runIntent = new Intent(RestaurantsActivity.this, MapActivity.class);
                        startActivity(runIntent);
                        finish();
                        break;
                    case R.id.ic_device:
                        Intent deviceIntent = new Intent(RestaurantsActivity.this, ProfileActivity.class);
                        startActivity(deviceIntent);
                        finish();
                        break;
                }

                return false;
            }
        });
    }


}
