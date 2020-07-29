package com.example.kohendakanne.Restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kohendakanne.AccountSetting;
import com.example.kohendakanne.MainActivity;
import com.example.kohendakanne.MapActivity;
import com.example.kohendakanne.Models.MenuItems;
import com.example.kohendakanne.Models.Restaurant;
import com.example.kohendakanne.ProfileActivity;
import com.example.kohendakanne.R;
import com.example.kohendakanne.RegisterActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

import com.squareup.picasso.Picasso;

public class SingleRestaurantActivity extends AppCompatActivity {

    private static final String TAG = "SingleRestaurantActivit";
    private static final int ACTIVITY_NUM = 1;

    private TextView textView, direction, textAddress;
    String resName, resDistrict, resID, image, id;
    GeoPoint geoPoint;
    private FirebaseFirestore firebaseFirestore;
    ImageView restImage;
    private FirestoreRecyclerAdapter adaptera;
    private RecyclerView menu_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_restaurant);

        setupBottomNavBar();

        resName = getIntent().getStringExtra("Restaurant_Name");
        resDistrict = getIntent().getStringExtra("district");
        resID = getIntent().getStringExtra("restaurant_id");
        image = getIntent().getStringExtra("image");
        id = getIntent().getStringExtra("id");

        textView = findViewById(R.id.textView1);
        textAddress = findViewById(R.id.textAddressGo);
        firebaseFirestore = FirebaseFirestore.getInstance();
        restImage = findViewById(R.id.restImage);
        menu_list = findViewById(R.id.menu_list);
        direction = findViewById(R.id.direction);


        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent2 = new Intent(SingleRestaurantActivity.this, MapActivity.class);
                homeIntent2.putExtra("Restaurant_Name" , resName);
                homeIntent2.putExtra("restaurant_id" ,resID );
                homeIntent2.putExtra("id" ,id );
                startActivity(homeIntent2);
            }
        });

        textAddress.setText(resDistrict);
        textView.setText(resName);
        Picasso.get().load(image).into(restImage);

        Query query = firebaseFirestore.collection("MenuItems").whereEqualTo("restaurant_id",resID).orderBy("price");
        FirestoreRecyclerOptions<MenuItems> options2 = new FirestoreRecyclerOptions.Builder<MenuItems>()
                .setQuery(query, MenuItems.class)
                .build();

        adaptera = new FirestoreRecyclerAdapter<MenuItems, MenuViewHolder>(options2) {
            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_menu_item, parent, false);

                return new MenuViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull MenuItems model) {
                holder.meal_name.setText(model.getMeal_name());
                holder.price.setText(model.getPrice());
                Picasso.get().load(model.getImage_url()).into(holder.item_image);

            }
        };

        menu_list.setHasFixedSize(true);
        menu_list.setLayoutManager(new LinearLayoutManager(this));
        menu_list.setAdapter(adaptera);


//        firebaseFirestore.collection("Restaurants")
//                .document(resName + "-" + resDistrict)
//                .collection("details")
//                .document().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    String image = task.getResult().getString("image_url");
//                    String contact = task.getResult().getString("contact");
//                    RequestOptions placeholder = new RequestOptions();
//                    placeholder.placeholder(R.drawable.logo);
//                    Glide.with(SingleRestaurantActivity.this).setDefaultRequestOptions(placeholder).load(image).into(restImage);
//
//                }else {
//
//                }
//            }
//        });


    }

    private class MenuViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_image;
        private TextView meal_name, price;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            meal_name = itemView.findViewById(R.id.meal_name);
            item_image = itemView.findViewById(R.id.item_image);
            price = itemView.findViewById(R.id.price);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptera.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptera.stopListening();
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
                        Intent homeIntent = new Intent(SingleRestaurantActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        finish();
                        break;
                    case R.id.ic_category:
                        Intent ExerciseIntent = new Intent(SingleRestaurantActivity.this, RestaurantsActivity.class);
                        startActivity(ExerciseIntent);
                        finish();
                        break;
                    case R.id.ic_add:
                        Intent runIntent = new Intent(SingleRestaurantActivity.this, RegisterActivity.class);
                        startActivity(runIntent);
                        finish();
                        break;
                    case R.id.ic_device:
                        Intent deviceIntent = new Intent(SingleRestaurantActivity.this, ProfileActivity.class);
                        startActivity(deviceIntent);
                        finish();
                        break;
                }

                return false;
            }
        });
    }



}
