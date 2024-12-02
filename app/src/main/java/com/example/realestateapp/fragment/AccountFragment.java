package com.example.realestateapp.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateapp.R;
import com.example.realestateapp.screens.AddPropertyActivity;
import com.example.realestateapp.screens.Helps;
import com.example.realestateapp.screens.LoginActivity;
import com.example.realestateapp.screens.Settings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private CircleImageView userProfile;
    private AppCompatButton updateButton, signOutButton;
    private Button addProperty;
    private TextView userName;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference storageRef;

    private LinearLayout btnSettings, btnHelps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase components
        storageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Get references to UI elements
        userProfile = view.findViewById(R.id.profile_image);
        updateButton = view.findViewById(R.id.update_button);
        signOutButton = view.findViewById(R.id.sign_out);
        addProperty = view.findViewById(R.id.add_property);
        userName = view.findViewById(R.id.user_names);
        btnSettings = view.findViewById(R.id.settings_section);
        btnHelps = view.findViewById(R.id.help_section);

        // Set click listener for profile image to choose image
        userProfile.setOnClickListener(v -> chooseImage());

        // Update user data on button click
        updateButton.setOnClickListener(v -> updateUserData());

        // Set click listener for sign out button
        signOutButton.setOnClickListener(v -> signOut());

        // Set click listener for add property button
        addProperty.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddPropertyActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Settings.class);
            startActivity(intent);
        });

        btnHelps.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Helps.class);
            startActivity(intent);
        });

        // Get current user and fetch data
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Set user's name from Firestore to TextView
            db.collection("users")
                    .document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String name = document.getString("name");
                                userName.setText("Welcome back, " + name);
                            }
                        }
                    });
        }
    }

    private void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish(); // Finish the current activity to prevent the user from navigating back
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            userProfile.setImageURI(imageUri); // Set the selected image as profile picture
        }
    }

    private void updateUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getActivity(), "No user signed in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        Map<String, Object> newData = new HashMap<>();
        DocumentReference userRef = db.collection("users").document(userId);

        if (imageUri != null) {
            // Upload image to Firebase Storage
            StorageReference profileImageRef = storageRef.child("profile_images/" + userId + ".jpg");
            profileImageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get the image URL
                        profileImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            newData.put("imageUrl", uri.toString());
                            // Update Firestore document with new data
                            userRef.set(newData, SetOptions.merge())
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getActivity(), "Profile Picture updated successfully", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("AccountFragment", "Error updating data", e);
                                        Toast.makeText(getActivity(), "Failed to update Profile Picture", Toast.LENGTH_SHORT).show();
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        Log.e("AccountFragment", "Error uploading image", e);
                        Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Update Firestore document with new data (without image)
            userRef.set(newData, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getActivity(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("AccountFragment", "Error updating data", e);
                        Toast.makeText(getActivity(), "Failed to update data", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}



/*import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.realestateapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private CircleImageView userProfile;
    private EditText userName, userEmail;
    private AppCompatButton updateButton;


    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri imageUri;

    private String userId;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String storageRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inside onViewCreated() method

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();




        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userProfile = view.findViewById(R.id.profile_image);
        userName = view.findViewById(R.id.user_name);
        userEmail = view.findViewById(R.id.user_email);
        updateButton = view.findViewById(R.id.update_button);

        // Fetch user data from Firestore
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
            DocumentReference userRef = db.collection("users").document(userId);
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.getString("name");
                    String email = documentSnapshot.getString("email");
                    String imageUrl = documentSnapshot.getString("imageUrl");

                    userName.setText(name);
                    userEmail.setText(email);
                    if (imageUrl != null) {
                        // Load profile image using Glide or any other image loading library
                        // For example:
                        // Glide.with(this).load(imageUrl).into(userProfile);
                        // This requires adding Glide library to your project dependencies
                    }

                } else {
                    // Handle the case where the document doesn't exist
                    Toast.makeText(getActivity(), "User data not found", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                // Handle failure
                Log.e("AccountFragment", "Error fetching user data", e);
                Toast.makeText(getActivity(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
            });
        }

        // Set click listener for profile image to choose image
        userProfile.setOnClickListener(v -> chooseImage());

        // Update user data on button click
        updateButton.setOnClickListener(v -> updateUserData());


    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            userProfile.setImageURI(imageUri);
        }
    }

    private void updateUserData() {
        String newName = userName.getText().toString().trim();
        String newEmail = userEmail.getText().toString().trim();

        // Update data in Firestore
        DocumentReference userRef = db.collection("users").document(userId);
        Map<String, Object> newData = new HashMap<>();
        newData.put("name", newName);
        newData.put("email", newEmail);

        if (imageUri != null) {
            // Upload image to Firebase Storage
            StorageReference profileImageRef = storageRef.child("profile_images/" + userId + ".jpg");
            profileImageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get the image URL
                        profileImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            newData.put("imageUrl", uri.toString());
                            // Update Firestore document with new data
                            userRef.set(newData, SetOptions.merge())
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getActivity(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("AccountFragment", "Error updating data", e);
                                        Toast.makeText(getActivity(), "Failed to update data", Toast.LENGTH_SHORT).show();
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        Log.e("AccountFragment", "Error uploading image", e);
                        Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Update Firestore document with new data (without image)
            userRef.set(newData, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getActivity(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("AccountFragment", "Error updating data", e);
                        Toast.makeText(getActivity(), "Failed to update data", Toast.LENGTH_SHORT).show();
                    });
        }


    }
}
*/



