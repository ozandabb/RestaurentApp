package com.example.kohendakanne.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kohendakanne.Models.MenuItems;
import com.example.kohendakanne.R;
import com.example.kohendakanne.Restaurant.SingleRestaurantActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class menuAdapter extends FirestoreRecyclerAdapter<MenuItems, menuAdapter.menuHolder> {

    public menuAdapter(@NonNull FirestoreRecyclerOptions<MenuItems> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull menuHolder holder, int position, @NonNull MenuItems model) {
        holder.meal_name.setText(model.getMeal_name());
        holder.price.setText(model.getPrice());
        Picasso.get().load(model.getImage_url()).into(holder.item_image);

    }

    @NonNull
    @Override
    public menuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_menu_item, parent, false);

        return new menuHolder(view);
    }

    class menuHolder extends RecyclerView.ViewHolder{

        private ImageView item_image;
        private TextView meal_name, price;

        public menuHolder(@NonNull View itemView) {
            super(itemView);

            meal_name = itemView.findViewById(R.id.meal_name);
            item_image = itemView.findViewById(R.id.item_image);
            price = itemView.findViewById(R.id.price);

        }
    }
}
