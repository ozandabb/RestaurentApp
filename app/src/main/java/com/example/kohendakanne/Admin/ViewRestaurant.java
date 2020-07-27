package com.example.kohendakanne.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kohendakanne.Models.Restaurant;
import com.example.kohendakanne.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class ViewRestaurant extends AppCompatActivity {

    private RecyclerView viewUser_Recycler;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapterMain;
    private Button GoADMIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurant);

        viewUser_Recycler = findViewById(R.id.viewUser_Recycler);
        firebaseFirestore = FirebaseFirestore.getInstance();
        GoADMIN = findViewById(R.id.GoADMIN);

        GoADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goAdmin = new Intent(ViewRestaurant.this, AdminDashboard.class);
                startActivity(goAdmin);
                finish();
            }
        });

        Query query = firebaseFirestore.collection("RestaurantsNames");
        final FirestoreRecyclerOptions<Restaurant> optionsMainRes = new FirestoreRecyclerOptions.Builder<Restaurant>()
                .setQuery(query, Restaurant.class)
                .build();

        adapterMain = new FirestoreRecyclerAdapter<Restaurant, RestaurantViewHolder>(optionsMainRes) {
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
        };

        viewUser_Recycler.setHasFixedSize(true);
        viewUser_Recycler.setLayoutManager(new LinearLayoutManager(this));
        viewUser_Recycler.setAdapter(adapterMain);
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
        adapterMain.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterMain.stopListening();
    }
}
