package com.example.kohendakanne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountSetting extends AppCompatActivity {

    private CircleImageView accountImage;
    private Uri mainImageUri = null;
    private EditText accountName;
    private TextView accName;
    private Button accountButton;
    private ProgressBar accountProgreess;
    private String user_id;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        accountImage = findViewById(R.id.account_image);
        accountName = findViewById(R.id.account_name);
        accountButton = findViewById(R.id.account_submit_btn);
        accountProgreess = findViewById(R.id.account_progressbar);
        accName = findViewById(R.id.accountName);

        accountProgreess.setVisibility(View.VISIBLE);
        accountButton.setEnabled(false);

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        String name = task.getResult().getString("name");
                        String image = task.getResult().getString("image");

//                        mainImageUri = Uri.parse(image);

                        accName.setText(name);
                        RequestOptions placeholder = new RequestOptions();
                        placeholder.placeholder(R.drawable.camera);
                        Glide.with(AccountSetting.this).setDefaultRequestOptions(placeholder).load(image).into(accountImage);
                    }
                }else {
                    String err = task.getException().getMessage();
                    Toast.makeText(AccountSetting.this,"Firestore retrive errr" + err, Toast.LENGTH_LONG).show();
                }
                accountProgreess.setVisibility(View.INVISIBLE);
                accountButton.setEnabled(true);
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
        Intent goLogin = new Intent(AccountSetting.this, LoginActivity.class);
        startActivity(goLogin);
        finish();
    }
}
