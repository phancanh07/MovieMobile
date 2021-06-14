package com.example.moviemobile.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemobile.R;
import com.example.moviemobile.config.ShowToast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class ForgotPassActivity extends AppCompatActivity {
    private TextView sendMail;
    private Button btn_send;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        sendMail = findViewById(R.id.inputsendEmail);
        btn_send = findViewById(R.id.btnsendEmail);
        auth = FirebaseAuth.getInstance();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmail();
            }
        });
    }

    private void checkEmail() {
        if (sendMail.getText().toString().isEmpty() || !sendMail.getText().toString().matches(ShowToast.REGEX)) {
            ShowToast.showToast("Please ! Enter Text ", this);
        } else {
            auth.sendPasswordResetEmail(sendMail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    ShowToast.showToast("Reset Link Send To Your Email!", getApplicationContext());
                    startActivity(new Intent(getApplicationContext(), LoginUserActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    ShowToast.showToast("Reset Link is Not Send!", getApplicationContext());
                }
            });
        }
    }

}