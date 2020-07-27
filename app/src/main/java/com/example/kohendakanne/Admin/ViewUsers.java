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
import com.example.kohendakanne.Models.Users;
import com.example.kohendakanne.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class ViewUsers extends AppCompatActivity {

    private RecyclerView viewUser_Recycler;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapterMain;
    private Button GoADMIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        viewUser_Recycler = findViewById(R.id.viewUser_Recycler);
        firebaseFirestore = FirebaseFirestore.getInstance();
        GoADMIN = findViewById(R.id.GoADMIN);

        GoADMIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goAdmin = new Intent(ViewUsers.this, AdminDashboard.class);
                startActivity(goAdmin);
                finish();
            }
        });

        Query query = firebaseFirestore.collection("Users");
        final FirestoreRecyclerOptions<Users> optionsMain = new FirestoreRecyclerOptions.Builder<Users>()
                .setQuery(query, Users.class)
                .build();

        adapterMain = new FirestoreRecyclerAdapter<Users, UserViewHolder>(optionsMain) {
            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_single_user, parent, false);
                return new UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull Users model) {
                holder.name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.image);

            }
        };

        viewUser_Recycler.setHasFixedSize(true);
        viewUser_Recycler.setLayoutManager(new LinearLayoutManager(this));
        viewUser_Recycler.setAdapter(adapterMain);
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.User_name);
            image = itemView.findViewById(R.id.user_image);

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
