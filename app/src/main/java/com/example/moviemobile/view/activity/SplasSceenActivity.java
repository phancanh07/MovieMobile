package com.example.moviemobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemobile.MainActivity;
import com.example.moviemobile.R;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.Timer;
import java.util.TimerTask;

public class SplasSceenActivity extends AppCompatActivity {
    boolean isPressed = false;
    private int time = 0;
    private Timer timer;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splas_sceen);
        textView = findViewById(R.id.txt_time);
        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time++;
                textView.setText(time + "");
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplasSceenActivity.this, LoginUserActivity.class));
                finish();
            }
        }.start();


    }
}
