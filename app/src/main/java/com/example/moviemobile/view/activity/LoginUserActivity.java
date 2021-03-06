package com.example.moviemobile.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemobile.MainActivity;
import com.example.moviemobile.R;
import com.example.moviemobile.config.DataLocalManager;
import com.example.moviemobile.config.ShowToast;
import com.example.moviemobile.model.acount.Acount;
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

public class LoginUserActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private EditText userName, password;
    private Button btnLogin;
    private CheckBox rememberpassword;
    private TextView creatacout, forgotPassword;
    public static final String REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        initUI();
        btnLogin.setOnClickListener(this::onClick);
        creatacout.setOnClickListener(this::onClick);
        forgotPassword.setOnClickListener(this::onClick);
    }

    private void initUI() {
        userName = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        creatacout = findViewById(R.id.creatacout);
        forgotPassword = findViewById(R.id.forgotPassword);
        rememberpassword = findViewById(R.id.rememberpassword);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        Acount acount = DataLocalManager.getAcount();
        rememberpassword.setChecked(DataLocalManager.getChecked());
        if (rememberpassword.isChecked()) {
            if (acount != null) {
                userName.setText(acount.getEmail());
                password.setText(acount.getPassWord());
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin: {
                checkValidate();
                break;
            }
            case R.id.creatacout: {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
            }
            case R.id.forgotPassword: {
                startActivity(new Intent(getApplicationContext(), ForgotPassActivity.class));
                break;
            }
            default:
                break;
        }
    }

    private void sigIn() {
        auth.signInWithEmailAndPassword(userName.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                ShowToast.showToast("LOGIN SUCCSESS", getApplicationContext());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                ShowToast.showToast("ERROR", getApplicationContext());
            }
        });
    }

    private void checkValidate() {
        boolean bchk = rememberpassword.isChecked();
        try {
            if (!userName.getText().toString().isEmpty() || !password.getText().toString().isEmpty()) {
                if (userName.getText().toString().matches(REGEX) && password.getText().toString().length() >= 6) {
                    Acount acount = new Acount(userName.getText().toString(), password.getText().toString());
                    DataLocalManager.setUser(acount);
                    DataLocalManager.setChecked(bchk);
                    sigIn();
                } else {
                    ShowToast.showToast("Check Email Or Password", this);
                }
            } else {
                ShowToast.showToast("Please! Enter Text", this);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}