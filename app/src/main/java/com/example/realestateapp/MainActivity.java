package com.example.realestateapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.airbnb.lottie.LottieAnimationView;
import com.example.realestateapp.screens.LoginActivity;
import com.example.realestateapp.screens.HomeActivity; // Assume this is the main activity after login

public class MainActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 3000; // 3 seconds
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animationView = findViewById(R.id.animationView);
        animationView.playAnimation();

        // Check if the user is already logged in
        SharedPreferences prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn) {
                    // If logged in, go to the main home activity
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                } else {
                    // Otherwise, go to the login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                finish(); // Finish the splash activity
            }
        }, SPLASH_DELAY);
    }
}
