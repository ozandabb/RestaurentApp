package com.example.kohendakanne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kohendakanne.Admin.AdminLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailText, loginPasswordText;
    private Button loginBtn, loginRegBtn;
    private FirebaseAuth mAuth;
    private ProgressBar loginProgressBar;
    private TextView regTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginEmailText = (EditText) findViewById(R.id.setup_name);
        loginPasswordText = (EditText) findViewById(R.id.re);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginProgressBar = findViewById(R.id.login_progressBar);
        regTextView = findViewById(R.id.regTextView);

        regTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRegister =new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goRegister);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginEmail = loginEmailText.getText().toString();
                String loginPass = loginPasswordText.getText().toString();
                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)) {
                    if (loginEmail.equals("kohenda.kanne@gmail.com") && loginPass.equals("Kohend@90SL"))
                    {
                        mAuth.signInWithEmailAndPassword(loginEmail,loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent goMain = new Intent(LoginActivity.this, AdminLogin.class);
                                    startActivity(goMain);
                                    finish();
                                }else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(LoginActivity.this,"Invalid Login",Toast.LENGTH_LONG).show();
                                }
                                loginProgressBar.setVisibility(View.INVISIBLE);

                            }
                        });

//                        finish();
                    }
                    else {
                        loginProgressBar.setVisibility(View.VISIBLE);
                        mAuth.signInWithEmailAndPassword(loginEmail,loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    sendToMain();
                                }else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(LoginActivity.this,"Invalid Login",Toast.LENGTH_LONG).show();
                                }
                                loginProgressBar.setVisibility(View.INVISIBLE);

                            }
                        });
                    }
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser CurrentUser = mAuth.getCurrentUser();
        if (CurrentUser != null) {
            sendToMain();
        } else {
            // No user is signed in
        }

    }

    private void sendToMain() {
        Intent goMain = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(goMain);
        finish();
    }
}
