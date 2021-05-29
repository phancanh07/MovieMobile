package com.example.moviemobile.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviemobile.MainActivity;
import com.example.moviemobile.R;
import com.example.moviemobile.config.DataLocalManager;
import com.example.moviemobile.model.acount.Acount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText userName, password, number;
    Button button;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        button.setOnClickListener(this::onClick);

    }

    private void initUI() {
        userName = findViewById(R.id.dk_email);
        password = findViewById(R.id.dk_password);
        button = findViewById(R.id.btn_dkacount);
        number = findViewById(R.id.dk_number);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dkacount: {
                createAcount();
            }
        }
    }

    private void createAcount() {
        auth.createUserWithEmailAndPassword(userName.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = auth.getCurrentUser();
                Toast.makeText(getApplicationContext(), "CREATE ", Toast.LENGTH_SHORT).show();
                DocumentReference reference = firestore.collection("User").document(user.getUid());
                Map<String, Object> map = new HashMap<>();
                map.put("username", userName.getText().toString());
                map.put("password", password.getText().toString());
                map.put("number", number.getText().toString());
                reference.set(map);
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
}