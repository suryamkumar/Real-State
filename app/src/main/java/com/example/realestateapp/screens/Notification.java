package com.example.realestateapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateapp.R;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // Initialize the back button
        ImageView btnNotBack = findViewById(R.id.notification_back);

        // Set up the back button click listener
        btnNotBack.setOnClickListener(v -> {
            Intent intent = new Intent(Notification.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
