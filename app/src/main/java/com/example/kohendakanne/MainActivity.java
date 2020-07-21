package com.example.kohendakanne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.GeoPoint;

import static com.example.kohendakanne.constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.example.kohendakanne.constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btn, btn2, btn3, btn4;
    private FirebaseAuth mAuth;
    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRestAdd = new Intent(MainActivity.this , AddRestaurantActivity.class);
                startActivity(goRestAdd);
            }
        });


//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goRest = new Intent(MainActivity.this , RawMapViewDemoActivity.class);
//                startActivity(goRest);
//            }
//        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goAccount = new Intent(MainActivity.this , AccountSetting.class);
                startActivity(goAccount);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                sendToLogin();
            }
        });
    }

//    private void getLastKnownLocation(){
//        Log.d(TAG, "getLastKnownLocation: called");
//
//        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                if (task.isSuccessful()){
//                    Location location = task.getResult();
//                    GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
//                }
//
//            }
//        });
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (checkMapServices()){
//            if (mLocationPermissionGranted){
////                Intent goRest = new Intent(MainActivity.this , RawMapViewDemoActivity.class);
////                startActivity(goRest);
//                getLastKnownLocation();
//            }
//            else {
//                getLocationPermission();
//            }
//        }
//    }
//
//    private boolean checkMapServices(){
//        if(isServicesOK()){
//            if(isMapsEnabled()){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void buildAlertMessageNoGps() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }
//
//    public boolean isMapsEnabled(){
//        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
//
//        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
//            buildAlertMessageNoGps();
//            return false;
//        }
//        return true;
//    }
//
//    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
////            getChatrooms();
//            getLastKnownLocation();
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//    }
//
//    public boolean isServicesOK(){
//        Log.d(TAG, "isServicesOK: checking google services version");
//
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
//
//        if(available == ConnectionResult.SUCCESS){
//            //everything is fine and the user can make map requests
//            Log.d(TAG, "isServicesOK: Google Play Services is working");
//            return true;
//        }
//        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            //an error occured but we can resolve it
//            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
////            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
////            dialog.show();
//        }else{
//            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        mLocationPermissionGranted = false;
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mLocationPermissionGranted = true;
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "onActivityResult: called.");
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_ENABLE_GPS: {
//                if(mLocationPermissionGranted){
////                    getChatrooms();
//                    getLastKnownLocation();
//                }
//                else{
//                    getLocationPermission();
//                }
//            }
//        }
//
//    }
//




























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
