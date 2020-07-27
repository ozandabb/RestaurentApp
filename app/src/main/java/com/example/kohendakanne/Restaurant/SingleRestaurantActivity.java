package com.example.kohendakanne.Restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kohendakanne.Models.MenuItems;
import com.example.kohendakanne.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class SingleRestaurantActivity extends AppCompatActivity {

    private TextView textView;
    String resName, resDistrict, resID, image;
    private FirebaseFirestore firebaseFirestore;
    ImageView restImage;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView menu_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_restaurant);

        resName = getIntent().getStringExtra("Restaurant_Name");
        resDistrict = getIntent().getStringExtra("district");
        resID = getIntent().getStringExtra("restaurant_id");
        image = getIntent().getStringExtra("image");

        textView = findViewById(R.id.textView1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        restImage = findViewById(R.id.restImage);
        menu_list = findViewById(R.id.menu_list);

        textView.setText(resName);
        int converted =Integer.parseInt(image);
        restImage.setImageResource(converted);


        Query query = firebaseFirestore.collection("MenuItems").whereEqualTo("restaurant_id",resID).orderBy("price");
        FirestoreRecyclerOptions<MenuItems> options2 = new FirestoreRecyclerOptions.Builder<MenuItems>()
                .setQuery(query, MenuItems.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<MenuItems, MenuViewHolder>(options2) {
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
        menu_list.setAdapter(adapter);


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
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
