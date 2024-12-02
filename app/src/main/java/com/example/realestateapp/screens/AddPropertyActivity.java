package com.example.realestateapp.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddPropertyActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText locationEditText, descriptionEditText, shortDescriptionEditText, ownerNameEditText,
            contactNoEditText, priceEditText, categoryEditText;

    private Spinner typeSpinner;

    private ImageView imageViewUploaded;
    private Button buttonUploadImage, buttonSubmit;

    private ImageButton backButton;

    private Uri imageUri;
    private StorageReference storageReference;
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_property_listing);

        // Initialize views
        locationEditText = findViewById(R.id.property_location);
        typeSpinner = findViewById(R.id.property_type);
        descriptionEditText = findViewById(R.id.property_description);
        shortDescriptionEditText = findViewById(R.id.property_shortdescription);
        ownerNameEditText = findViewById(R.id.property_ownername);
        contactNoEditText = findViewById(R.id.property_contactno);
        priceEditText = findViewById(R.id.property_price);
        categoryEditText = findViewById(R.id.property_category);
        imageViewUploaded = findViewById(R.id.imageViewUploaded);
        buttonUploadImage = findViewById(R.id.buttonUploadImage);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        backButton = findViewById(R.id.back_button);

        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        // Set up Spinner with predefined options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.property_type_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        // Back button click listener
        backButton.setOnClickListener(v -> finish());

        // Image upload button listener
        buttonUploadImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // Submit button listener
        buttonSubmit.setOnClickListener(v -> {
            // Get form inputs
            String location = locationEditText.getText().toString().trim();
            String type = typeSpinner.getSelectedItem().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String shortDescription = shortDescriptionEditText.getText().toString().trim();
            String ownerName = ownerNameEditText.getText().toString().trim();
            String contactNo = contactNoEditText.getText().toString().trim();
            String price = priceEditText.getText().toString().trim();
            String category = categoryEditText.getText().toString().trim();

            // Validate inputs
            if (location.isEmpty() || type.isEmpty() || description.isEmpty() || shortDescription.isEmpty() ||
                    ownerName.isEmpty() || contactNo.isEmpty() || price.isEmpty() || category.isEmpty()) {
                Toast.makeText(AddPropertyActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (imageUri == null) {
                Toast.makeText(AddPropertyActivity.this, "Please upload an image", Toast.LENGTH_SHORT).show();
                return;
            }

            // Upload image to Firebase Storage
            StorageReference imageRef = storageReference.child("images/" + System.currentTimeMillis() + ".jpg");
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get the image URL
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Prepare property data
                            Map<String, Object> propertyData = new HashMap<>();
                            propertyData.put("location", location);
                            propertyData.put("type", type);
                            propertyData.put("description", description);
                            propertyData.put("shortdescription", shortDescription);
                            propertyData.put("ownername", ownerName);
                            propertyData.put("contactno", contactNo);
                            propertyData.put("price", price);
                            propertyData.put("category", category);
                            propertyData.put("imageuri", uri.toString());

                            // Save to Firestore
                            db.collection("Properties").add(propertyData)
                                    .addOnSuccessListener(documentReference -> {
                                        Toast.makeText(AddPropertyActivity.this, "Property added successfully", Toast.LENGTH_SHORT).show();
                                        clearForm();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(AddPropertyActivity.this, "Failed to add property", Toast.LENGTH_SHORT).show());
                        });
                    })
                    .addOnFailureListener(e -> Toast.makeText(AddPropertyActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageViewUploaded.setImageURI(imageUri);
        }
    }

    private void clearForm() {
        locationEditText.setText("");
        descriptionEditText.setText("");
        shortDescriptionEditText.setText("");
        ownerNameEditText.setText("");
        contactNoEditText.setText("");
        priceEditText.setText("");
        categoryEditText.setText("");
        typeSpinner.setSelection(0);
        imageViewUploaded.setImageDrawable(null);
        imageUri = null;
    }
}
