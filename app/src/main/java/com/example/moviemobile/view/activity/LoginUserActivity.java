package com.example.moviemobile.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviemobile.MainActivity;
import com.example.moviemobile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LoginUserActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    EditText userName, password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        initUI();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signInWithEmailAndPassword(userName.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.d("ERROR", e.getMessage());
                    }
                });
            }
        });
    }

    private void initUI() {
        userName = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        button = findViewById(R.id.btnLogin);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }
}