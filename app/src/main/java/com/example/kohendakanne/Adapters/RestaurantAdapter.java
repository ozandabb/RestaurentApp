package com.example.kohendakanne.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kohendakanne.Models.Restaurant;
import com.example.kohendakanne.R;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private ArrayList<Restaurant> mNotes = new ArrayList<>();

    public RestaurantAdapter(ArrayList<Restaurant> notes){
        this.mNotes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_restaurant_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.restaurant_name.setText(mNotes.get(position).getRestaurant_name());
        holder.restaurant_district.setText(mNotes.get(position).getDistrict());

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView restaurant_name;
        private TextView restaurant_district;
        private ImageView restaurant_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurant_name = itemView.findViewById(R.id.restaurant_name);
            restaurant_district = itemView.findViewById(R.id.restaurant_district);
            restaurant_image = itemView.findViewById(R.id.restaurant_image);
        }
    }

    public interface onNoteListner{
        void OnNoteClick(int position);
    }
}
