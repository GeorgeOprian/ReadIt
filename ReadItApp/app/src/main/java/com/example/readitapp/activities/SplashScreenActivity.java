package com.example.readitapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.readitapp.R;
import com.example.readitapp.utils.FirebaseConstants;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> startApp(), 2000);
    }

    private void startApp() {
        if(FirebaseConstants.user != null) {
            Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        } else {
            Intent signInIntent = new Intent(SplashScreenActivity.this, SignInActivity.class);
            startActivity(signInIntent);
            finish();
        }
    }
}