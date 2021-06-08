package com.example.moviemobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemobile.MainActivity;
import com.example.moviemobile.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.Wave;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.Timer;
import java.util.TimerTask;

public class SplasSceenActivity extends AppCompatActivity {
    private int time = 0;
    TextView textView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splas_sceen);
        textView = findViewById(R.id.txt_time);
        progressBar = findViewById(R.id.progessbar_splass);
        Sprite doubleBounce = new Wave();
        doubleBounce.setColor(Color.RED);
        progressBar.setIndeterminateDrawable(doubleBounce);
        new CountDownTimer(5000, 1000) {
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
