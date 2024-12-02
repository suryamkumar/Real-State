// Generated by view binder compiler. Do not edit!
package com.example.realestateapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.realestateapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AddPropertyListingBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton backButton;

  @NonNull
  public final Button buttonSubmit;

  @NonNull
  public final Button buttonUploadImage;

  @NonNull
  public final TextView headings;

  @NonNull
  public final ImageView imageViewUploaded;

  @NonNull
  public final EditText propertyCategory;

  @NonNull
  public final EditText propertyContactno;

  @NonNull
  public final EditText propertyDescription;

  @NonNull
  public final EditText propertyLocation;

  @NonNull
  public final EditText propertyOwnername;

  @NonNull
  public final EditText propertyPrice;

  @NonNull
  public final EditText propertyShortdescription;

  @NonNull
  public final Spinner propertyType;

  private AddPropertyListingBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageButton backButton, @NonNull Button buttonSubmit,
      @NonNull Button buttonUploadImage, @NonNull TextView headings,
      @NonNull ImageView imageViewUploaded, @NonNull EditText propertyCategory,
      @NonNull EditText propertyContactno, @NonNull EditText propertyDescription,
      @NonNull EditText propertyLocation, @NonNull EditText propertyOwnername,
      @NonNull EditText propertyPrice, @NonNull EditText propertyShortdescription,
      @NonNull Spinner propertyType) {
    this.rootView = rootView;
    this.backButton = backButton;
    this.buttonSubmit = buttonSubmit;
    this.buttonUploadImage = buttonUploadImage;
    this.headings = headings;
    this.imageViewUploaded = imageViewUploaded;
    this.propertyCategory = propertyCategory;
    this.propertyContactno = propertyContactno;
    this.propertyDescription = propertyDescription;
    this.propertyLocation = propertyLocation;
    this.propertyOwnername = propertyOwnername;
    this.propertyPrice = propertyPrice;
    this.propertyShortdescription = propertyShortdescription;
    this.propertyType = propertyType;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AddPropertyListingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AddPropertyListingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.add_property_listing, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AddPropertyListingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.back_button;
      ImageButton backButton = ViewBindings.findChildViewById(rootView, id);
      if (backButton == null) {
        break missingId;
      }

      id = R.id.buttonSubmit;
      Button buttonSubmit = ViewBindings.findChildViewById(rootView, id);
      if (buttonSubmit == null) {
        break missingId;
      }

      id = R.id.buttonUploadImage;
      Button buttonUploadImage = ViewBindings.findChildViewById(rootView, id);
      if (buttonUploadImage == null) {
        break missingId;
      }

      id = R.id.headings;
      TextView headings = ViewBindings.findChildViewById(rootView, id);
      if (headings == null) {
        break missingId;
      }

      id = R.id.imageViewUploaded;
      ImageView imageViewUploaded = ViewBindings.findChildViewById(rootView, id);
      if (imageViewUploaded == null) {
        break missingId;
      }

      id = R.id.property_category;
      EditText propertyCategory = ViewBindings.findChildViewById(rootView, id);
      if (propertyCategory == null) {
        break missingId;
      }

      id = R.id.property_contactno;
      EditText propertyContactno = ViewBindings.findChildViewById(rootView, id);
      if (propertyContactno == null) {
        break missingId;
      }

      id = R.id.property_description;
      EditText propertyDescription = ViewBindings.findChildViewById(rootView, id);
      if (propertyDescription == null) {
        break missingId;
      }

      id = R.id.property_location;
      EditText propertyLocation = ViewBindings.findChildViewById(rootView, id);
      if (propertyLocation == null) {
        break missingId;
      }

      id = R.id.property_ownername;
      EditText propertyOwnername = ViewBindings.findChildViewById(rootView, id);
      if (propertyOwnername == null) {
        break missingId;
      }

      id = R.id.property_price;
      EditText propertyPrice = ViewBindings.findChildViewById(rootView, id);
      if (propertyPrice == null) {
        break missingId;
      }

      id = R.id.property_shortdescription;
      EditText propertyShortdescription = ViewBindings.findChildViewById(rootView, id);
      if (propertyShortdescription == null) {
        break missingId;
      }

      id = R.id.property_type;
      Spinner propertyType = ViewBindings.findChildViewById(rootView, id);
      if (propertyType == null) {
        break missingId;
      }

      return new AddPropertyListingBinding((RelativeLayout) rootView, backButton, buttonSubmit,
          buttonUploadImage, headings, imageViewUploaded, propertyCategory, propertyContactno,
          propertyDescription, propertyLocation, propertyOwnername, propertyPrice,
          propertyShortdescription, propertyType);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
