package com.example.kohendakanne;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kohendakanne.Admin.AdminDashboard;
import com.example.kohendakanne.Models.MenuItems;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddMenuItems extends AppCompatActivity {

    String resName, resID, resDistrict;

    private static final int MAX_LENGTH = 100;

    private ImageView post_image;
    private EditText newPostDescription, newPostDistrict, Ingredients;
    private Button newPostBtn ,finishBtn;
    private Uri postImageUri;
    private ProgressBar addpostProgree;
    private String user_id;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_items);

        Intent intent = getIntent();
        resName = intent.getStringExtra("Restaurant_Name");
        resID = intent.getStringExtra("restaurant_id");
        resDistrict = intent.getStringExtra("district");

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        post_image = findViewById(R.id.addPostImage);
        newPostDescription = findViewById(R.id.addPostText);
        newPostDistrict = findViewById(R.id.addPostText2);
        Ingredients = findViewById(R.id.Ingredients);
        newPostBtn = findViewById(R.id.post_btn);
        finishBtn = findViewById(R.id.finishBtn);
        addpostProgree = findViewById(R.id.add_post_progressBar);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goAdmin = new Intent(AddMenuItems.this, AdminDashboard.class);
                startActivity(goAdmin);
                finish();
            }
        });

        post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512,512)
                        .setAspectRatio(1,1)
                        .start(AddMenuItems.this);
            }
        });

        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String RestName = newPostDescription.getText().toString();
                final String district = newPostDistrict.getText().toString();
                final String Ingred = Ingredients.getText().toString();
                if (!TextUtils.isEmpty(RestName) && !TextUtils.isEmpty(district) && !TextUtils.isEmpty(Ingred) && postImageUri != null){
                    addpostProgree.setVisibility(View.VISIBLE);
                    String randomName = random();
                    StorageReference filePath = storageReference.child("Meu_Items").child(randomName + ".jpg");
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
                                    postMap.put("restaurant_id", resID);
                                    postMap.put("meal_name",RestName);
                                    postMap.put("price",district);
                                    postMap.put("ingredients",Ingred);

                                    firebaseFirestore.collection("Restaurants")
                                            .document(resName + "-" + resDistrict)
                                            .collection("Menu")
                                            .add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            addpostProgree.setVisibility(View.INVISIBLE);
                                        }
                                    });

                                    firebaseFirestore.collection("MenuItems").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(AddMenuItems.this,"Menu added" , Toast.LENGTH_LONG).show();

                                            }else {
                                                String err = task.getException().getMessage();
                                                Toast.makeText(AddMenuItems.this,"Upload error" + err, Toast.LENGTH_LONG).show();

                                            }
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
