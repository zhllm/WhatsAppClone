package com.example.firebasecrud;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasecrud.starup.WelcomeScreenActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, WelcomeScreenActivity.class));
                finish();
            }
        }, 20);
    }
}

/**

 keytool -genkey -alias crud  -keyalg RSA  -keypass 123456 -storepass 123456  -keystore keystore.jks
 */