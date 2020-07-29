package com.example.kohendakanne.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kohendakanne.AddRestaurantActivity;
import com.example.kohendakanne.LoginActivity;
import com.example.kohendakanne.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminDashboard extends AppCompatActivity {

    private LinearLayout AddRestaurants, viewRestaurants ,ViewUsers, logoutLay;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        mAuth = FirebaseAuth.getInstance();

        AddRestaurants = findViewById(R.id.AddRestaurants);
        viewRestaurants = findViewById(R.id.viewRestaurants);
        ViewUsers = findViewById(R.id.ViewUsers);
        logoutLay =findViewById(R.id.logoutLay);

        logoutLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent goRestAdd = new Intent(AdminDashboard.this , LoginActivity.class);
                startActivity(goRestAdd);
                finish();
            }
        });

        AddRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRestAdd = new Intent(AdminDashboard.this , AddRestaurantActivity.class);
                startActivity(goRestAdd);
            }
        });

        viewRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ExerciseIntent = new Intent(AdminDashboard.this, ViewRestaurant.class);
                startActivity(ExerciseIntent);
            }
        });

        ViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ExerciseIntent = new Intent(AdminDashboard.this, ViewUsers.class);
                startActivity(ExerciseIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (CurrentUser == null) {
            Intent goRestAdd = new Intent(AdminDashboard.this , LoginActivity.class);
            startActivity(goRestAdd);
            finish();
        } else {
            // No user is signed in
        }
    }
}
