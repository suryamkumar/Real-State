// Generated by view binder compiler. Do not edit!
package com.example.realestateapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.realestateapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class TopDealsBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ImageView bg;

  @NonNull
  public final ImageView favouriteDeal;

  @NonNull
  public final TextView location;

  @NonNull
  public final TextView price;

  @NonNull
  public final TextView shortDescription;

  private TopDealsBinding(@NonNull CardView rootView, @NonNull ImageView bg,
      @NonNull ImageView favouriteDeal, @NonNull TextView location, @NonNull TextView price,
      @NonNull TextView shortDescription) {
    this.rootView = rootView;
    this.bg = bg;
    this.favouriteDeal = favouriteDeal;
    this.location = location;
    this.price = price;
    this.shortDescription = shortDescription;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static TopDealsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TopDealsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.top_deals, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TopDealsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bg;
      ImageView bg = ViewBindings.findChildViewById(rootView, id);
      if (bg == null) {
        break missingId;
      }

      id = R.id.favourite_deal;
      ImageView favouriteDeal = ViewBindings.findChildViewById(rootView, id);
      if (favouriteDeal == null) {
        break missingId;
      }

      id = R.id.location;
      TextView location = ViewBindings.findChildViewById(rootView, id);
      if (location == null) {
        break missingId;
      }

      id = R.id.price;
      TextView price = ViewBindings.findChildViewById(rootView, id);
      if (price == null) {
        break missingId;
      }

      id = R.id.short_description;
      TextView shortDescription = ViewBindings.findChildViewById(rootView, id);
      if (shortDescription == null) {
        break missingId;
      }

      return new TopDealsBinding((CardView) rootView, bg, favouriteDeal, location, price,
          shortDescription);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
