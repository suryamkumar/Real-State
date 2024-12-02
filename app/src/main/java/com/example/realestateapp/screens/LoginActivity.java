package com.example.realestateapp.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView createAccount, forgotPasswordText; // Declare forgotPasswordText here
    private FirebaseAuth mAuth;
    private EditText email, password;
    private AppCompatButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        createAccount = findViewById(R.id.dont_have_account);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        loginButton = findViewById(R.id.login_button);
        forgotPasswordText = findViewById(R.id.forgot_password); // Initialize the TextView for Forgot Password

        // Handle the create account click event
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        // Handle the login button click event
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().trim().isEmpty() && emailChecker(email.getText().toString().trim())) {
                    if (!password.getText().toString().isEmpty()) {
                        loginUser(email.getText().toString().trim(), password.getText().toString().trim());
                    } else {
                        Toast.makeText(LoginActivity.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle the Forgot Password click event
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the ForgotPassword activity
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
    }

    // Method to check if the email is valid
    boolean emailChecker(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Method to authenticate user using Firebase Authentication
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Save login status in SharedPreferences
                            SharedPreferences prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();

                            // Navigate to HomeActivity
                            navigateToNextActivity();
                            Toast.makeText(LoginActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign-in fails, display a message to the user
                            Toast.makeText(LoginActivity.this, "Authentication failed. Please check your email or password.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    // Method to navigate to the next activity
    private void navigateToNextActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Optional, to prevent the user from going back to the login page using the back button
    }
}
