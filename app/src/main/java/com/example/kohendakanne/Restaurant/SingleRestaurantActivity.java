package com.example.kohendakanne.Restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kohendakanne.R;

public class SingleRestaurantActivity extends AppCompatActivity {

    private TextView textView;
    String resName, resDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_restaurant);

        Intent intent = getIntent();
        resName = intent.getStringExtra("Restaurant_Name");
//        resDistrict = intent.getStringExtra("district");

        textView = findViewById(R.id.textView1);

        textView.setText(resName + " - " + resDistrict);


    }
}
