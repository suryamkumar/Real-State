package com.example.realestateapp.screens;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private TextView haveAccount;
    private EditText userName, userEmail, userPassword, confirmPassword;
    private AppCompatButton signupButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize UI elements
        haveAccount = findViewById(R.id.have_account);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        confirmPassword = findViewById(R.id.user_confirm_password);
        signupButton = findViewById(R.id.sign_up_button);

        // Initialize Firebase instances
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        // Check if the user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // If the user is already signed in, navigate to the HomeActivity
            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
            finish();
            return;  // Exit the onCreate method
        }

        // Redirect to LoginActivity if already have an account
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });

        // Handle Sign-Up button click
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });
    }

    private void handleSignUp() {
        String name = userName.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Enter a password", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            createUser(email, password, name);
        }
    }

    private void createUser(String email, String password, String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign-up success
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                userId = user.getUid();
                                saveUserDataToFirestore(userId, name, email);

                                // Save login status in SharedPreferences
                                SharedPreferences prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.apply();

                                // Redirect to HomeActivity
                                Toast.makeText(SignUpActivity.this, "Sign-up successful.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                finish();
                            }
                        } else {
                            // Sign-up failed
                            Exception exception = task.getException();
                            if (exception != null) {
                                String message = exception.getMessage();
                                Toast.makeText(SignUpActivity.this, "Sign-up failed: " + message, Toast.LENGTH_LONG).show();
                                Log.w(TAG, "Sign up failed.", exception);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Sign-up failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    private void saveUserDataToFirestore(String userId, String name, String email) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", email);

        mFirestore.collection("users").document(userId).set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User data successfully stored to Firestore");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing user data to Firestore", e);
                    }
                });
    }
}
