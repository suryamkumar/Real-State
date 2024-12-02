package com.example.realestateapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.realestateapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText resetEmailEditText;
    private AppCompatButton resetPasswordButton;
    private FirebaseAuth auth;
    private ImageButton backButton;  // Custom back button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();

        resetEmailEditText = findViewById(R.id.reset_email);
        resetPasswordButton = findViewById(R.id.reset_password_button);
        backButton = findViewById(R.id.onBackPressed);  // Set the back button reference

        // Handle back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogin();
            }
        });

        // Set button click listener for sending the password reset email
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = resetEmailEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgotPassword.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Send password reset email
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPassword.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                                    finish(); // Close the activity and return to login
                                } else {
                                    Toast.makeText(ForgotPassword.this, "Error! Reset link could not be sent", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clears back stack
        startActivity(intent);
        finish(); // Finish current activity to prevent user from going back
    }

    // This method is no longer necessary because back button is handled via ImageButton
    @Override
    public void onBackPressed() {
        // No need to call super.onBackPressed() as you are handling it yourself
        navigateToLogin();
        super.onBackPressed();
    }
}
