package com.example.kohendakanne.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kohendakanne.Models.Restaurant;
import com.example.kohendakanne.R;
import com.example.kohendakanne.Restaurant.RestaurantsActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class ResAdapter extends FirestoreRecyclerAdapter<Restaurant, ResAdapter.resHolder> {
    private onItemClickListener listener;

    public ResAdapter(@NonNull FirestoreRecyclerOptions<Restaurant> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull resHolder holder, int position, @NonNull Restaurant model) {
        holder.restaurant_name.setText(model.getRestaurant_name());
        holder.restaurant_district.setText(model.getDistrict());
        Picasso.get().load(model.getImage_url()).into(holder.restaurant_image);

    }

    @NonNull
    @Override
    public resHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_restaurant_item, parent, false);
        return new resHolder(view);
    }

    class resHolder extends RecyclerView.ViewHolder{

        private TextView restaurant_name;
        private TextView restaurant_district;
        private ImageView restaurant_image;
        private Button contactResBtn;

        public resHolder(@NonNull View itemView) {
            super(itemView);

            restaurant_name = itemView.findViewById(R.id.restaurant_name);
            restaurant_district = itemView.findViewById(R.id.restaurant_district);
            restaurant_image = itemView.findViewById(R.id.restaurant_image);
            contactResBtn = itemView.findViewById(R.id.contactResBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
