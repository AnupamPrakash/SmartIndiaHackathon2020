package com.example.newseye;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
//                here you can have your logic to set text to edittext
            }

            public void onFinish() {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                finish();
            }

        }.start();
    }

}
