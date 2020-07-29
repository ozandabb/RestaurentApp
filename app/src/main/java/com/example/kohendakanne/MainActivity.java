package com.example.kohendakanne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kohendakanne.Restaurant.RestaurantsActivity;

import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    ImageView logoutBtn;
    RelativeLayout main02;
    private TextView count_Restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseFirestore = FirebaseFirestore.getInstance();

        setupBottomNavBar();

        mAuth = FirebaseAuth.getInstance();
        main02 = findViewById(R.id.main02);
        logoutBtn = findViewById(R.id.logoutBtn);
        count_Restaurants = findViewById(R.id.count_Restaurants);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                sendToLogin();
            }
        });

        main02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goAccount = new Intent(MainActivity.this , RestaurantsActivity.class);
                startActivity(goAccount);
            }
        });

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
                        Intent homeIntent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(homeIntent);
                        finish();
                        break;
                    case R.id.ic_category:
                        Intent ExerciseIntent = new Intent(MainActivity.this, RestaurantsActivity.class);
                        startActivity(ExerciseIntent);
                        finish();
                        break;
                    case R.id.ic_add:
                        Intent runIntent = new Intent(MainActivity.this, AccountSetting.class);
                        startActivity(runIntent);
                        finish();
                        break;
                    case R.id.ic_device:
                        Intent deviceIntent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(deviceIntent);
                        finish();
                        break;
                }

                return false;
            }
        });
    }










    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (CurrentUser == null) {
            sendToLogin();
        } else {
            // No user is signed in
        }
    }

    private void sendToLogin() {
        Intent goLogin = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(goLogin);
        finish();
    }
}
