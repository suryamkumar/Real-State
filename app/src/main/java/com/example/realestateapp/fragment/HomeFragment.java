package com.example.realestateapp.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.realestateapp.R;
import com.example.realestateapp.adapters.HomeAdapter;
import com.example.realestateapp.adapters.PropertyHomeAdapter;
import com.example.realestateapp.listeners.ItemListener;
import com.example.realestateapp.model.Item;
import com.example.realestateapp.model.Property;
import com.example.realestateapp.screens.AddPropertyActivity;
import com.example.realestateapp.screens.DetailsActivity;
import com.example.realestateapp.screens.Notification;
import com.example.realestateapp.screens.SearchActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ItemListener {

    private HomeAdapter adapter;
    private List<Item> itemList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        ImageView btnNotification = view.findViewById(R.id.btn_notification);
        RecyclerView topDealRV = view.findViewById(R.id.top_deal_RV);
        RecyclerView topDealRV1 = view.findViewById(R.id.top_deal_RV1);
        EditText searchField = view.findViewById(R.id.search);
        Button btnSell = view.findViewById(R.id.btn_sell);
        Button btnRent = view.findViewById(R.id.btn_rent);
        Button btnBuy = view.findViewById(R.id.btn_buy);// Added initialization for btnRent

        // Set click listeners
        searchField.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SearchActivity.class);
            startActivity(intent);
        });

        btnSell.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddPropertyActivity.class);
            startActivity(intent);
        });

        btnRent.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddPropertyActivity.class);
            startActivity(intent);
        });

        btnBuy.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SearchActivity.class);
            startActivity(intent);
        });

        btnNotification.setOnClickListener((v -> {
            Intent intent = new Intent(requireContext(), Notification.class);
            startActivity(intent);
        }));

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Fetch categories for the second RecyclerView
        db.collection("Category")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Property> propertyList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("title");
                            String imageUri = document.getString("imageuri");
                            String category = document.getString("category");

                            Property property = new Property(imageUri, title, category);
                            propertyList.add(property);
                        }

                        PropertyHomeAdapter adapter2 = new PropertyHomeAdapter(getContext(), propertyList, this);
                        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
                        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
                        topDealRV1.setLayoutManager(layoutManager2);
                        topDealRV1.setAdapter(adapter2);
                    } else {
                        Log.d(TAG, "Error getting property categories: ", task.getException());
                    }
                });

        // Fetch properties for the first RecyclerView
        itemList = new ArrayList<>();
        db.collection("Properties")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String location = document.getString("location");
                            String price = document.getString("price");
                            String shortDescription = document.getString("shortdescription");
                            String imageUri = document.getString("imageuri");
                            String ownerName = document.getString("ownername");
                            String type = document.getString("type");
                            String contactNo = document.getString("contactno");
                            String description = document.getString("description");

                            Item item = new Item(location, price, shortDescription, imageUri, contactNo, ownerName, type, description);
                            itemList.add(item);
                        }

                        adapter = new HomeAdapter(getContext(), itemList, this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        topDealRV.setLayoutManager(linearLayoutManager);
                        topDealRV.setAdapter(adapter);
                    } else {
                        Log.d(TAG, "Error getting properties: ", task.getException());
                    }
                });
    }

    @Override
    public void OnItemPosition(int position) {
        Item selectedItem = itemList.get(position);

        Intent intent = new Intent(requireContext(), DetailsActivity.class);
        intent.putExtra("location", selectedItem.getLocation());
        intent.putExtra("price", selectedItem.getPrice());
        intent.putExtra("shortdescription", selectedItem.getShortDescription());
        intent.putExtra("imageuri", selectedItem.getImageUri());
        intent.putExtra("contactno", selectedItem.getContactNo());
        intent.putExtra("description", selectedItem.getDescription());
        intent.putExtra("type", selectedItem.getType());
        intent.putExtra("ownername", selectedItem.getOwnerName());

        startActivity(intent);
    }
}
