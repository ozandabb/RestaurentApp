package com.example.kohendakanne;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddRestaurantActivity extends AppCompatActivity {

    private static final String TAG = "AddRestaurantActivity";

    private static final int MAX_LENGTH = 100;


    private EditText LatitudeText , LongitudeText;

    private ImageView post_image;
    private EditText newPostDescription, newPostDistrict;
    private Button newPostBtn;
    private Uri postImageUri;
    private ProgressBar addpostProgree;
    private String user_id;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        LatitudeText = findViewById(R.id.LatitudeText);
        LongitudeText = findViewById(R.id.Longitude);

        post_image = findViewById(R.id.addPostImage);
        newPostDescription = findViewById(R.id.addPostText);
        newPostDistrict = findViewById(R.id.addPostText2);
        newPostBtn = findViewById(R.id.post_btn);
        addpostProgree = findViewById(R.id.add_post_progressBar);

        post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512,512)
                        .setAspectRatio(1,1)
                        .start(AddRestaurantActivity.this);
            }
        });

        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String RestName = newPostDescription.getText().toString();
                final String district = newPostDistrict.getText().toString();
                final String Latitude = LatitudeText.getText().toString();
                double lat = Double.parseDouble(Latitude);
                final String Longitude = LongitudeText.getText().toString();
                double lon = Double.parseDouble(Longitude);
                final GeoPoint geoPoint = new GeoPoint(lat,lon);
                if (!TextUtils.isEmpty(RestName) && !TextUtils.isEmpty(district) && postImageUri != null){
                    addpostProgree.setVisibility(View.VISIBLE);
                    final String randomName = random();
                    StorageReference filePath = storageReference.child("restaurant_images").child(randomName + ".jpg");
                    final UploadTask uploadTask = filePath.putFile(postImageUri);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String download_url = uri.toString();
                                    Map<String,Object> postMap = new HashMap<>();
                                    postMap.put("image_url",download_url);
                                    postMap.put("restaurant_id" , randomName);
                                    postMap.put("restaurant_name",RestName);
                                    postMap.put("district",district);
                                    postMap.put("GeoPoint",geoPoint);

                                    firebaseFirestore.collection("Restaurants").document(RestName + "-" +district).collection("details").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(AddRestaurantActivity.this,"Restaurant added" , Toast.LENGTH_LONG).show();
                                                String id = firebaseFirestore.collection("Restaurants").document().getId();
                                                Intent goAddMenu = new Intent(AddRestaurantActivity.this, AddMenuItems.class);
                                                goAddMenu.putExtra("restaurant_id", randomName);
                                                goAddMenu.putExtra("Restaurant_Name" ,RestName );
                                                goAddMenu.putExtra("district",district );
                                                Log.d(TAG, "onComplete: "+ getTaskId());
                                                startActivity(goAddMenu);

                                            }else {
                                                String err = task.getException().getMessage();
                                                Toast.makeText(AddRestaurantActivity.this,"Upload error" + err, Toast.LENGTH_LONG).show();

                                            }
                                            addpostProgree.setVisibility(View.INVISIBLE);
                                        }
                                    });

                                    firebaseFirestore.collection("RestaurantsNames").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(AddRestaurantActivity.this,"Restaurant added" , Toast.LENGTH_LONG).show();

                                            }else {
                                                String err = task.getException().getMessage();
                                                Toast.makeText(AddRestaurantActivity.this,"Upload error" + err, Toast.LENGTH_LONG).show();

                                            }
                                            addpostProgree.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }
                            });

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                postImageUri = result.getUri();
                post_image.setImageURI(postImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }




}
