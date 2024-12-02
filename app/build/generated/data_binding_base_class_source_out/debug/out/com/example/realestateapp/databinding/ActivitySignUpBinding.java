// Generated by view binder compiler. Do not edit!
package com.example.realestateapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.realestateapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignUpBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView haveAccount;

  @NonNull
  public final ImageView iconImage;

  @NonNull
  public final TextView realestateApp;

  @NonNull
  public final AppCompatButton signUpButton;

  @NonNull
  public final EditText userConfirmPassword;

  @NonNull
  public final EditText userEmail;

  @NonNull
  public final EditText userName;

  @NonNull
  public final EditText userPassword;

  @NonNull
  public final TextView welcome;

  private ActivitySignUpBinding(@NonNull RelativeLayout rootView, @NonNull TextView haveAccount,
      @NonNull ImageView iconImage, @NonNull TextView realestateApp,
      @NonNull AppCompatButton signUpButton, @NonNull EditText userConfirmPassword,
      @NonNull EditText userEmail, @NonNull EditText userName, @NonNull EditText userPassword,
      @NonNull TextView welcome) {
    this.rootView = rootView;
    this.haveAccount = haveAccount;
    this.iconImage = iconImage;
    this.realestateApp = realestateApp;
    this.signUpButton = signUpButton;
    this.userConfirmPassword = userConfirmPassword;
    this.userEmail = userEmail;
    this.userName = userName;
    this.userPassword = userPassword;
    this.welcome = welcome;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.have_account;
      TextView haveAccount = ViewBindings.findChildViewById(rootView, id);
      if (haveAccount == null) {
        break missingId;
      }

      id = R.id.icon_image;
      ImageView iconImage = ViewBindings.findChildViewById(rootView, id);
      if (iconImage == null) {
        break missingId;
      }

      id = R.id.realestate_app;
      TextView realestateApp = ViewBindings.findChildViewById(rootView, id);
      if (realestateApp == null) {
        break missingId;
      }

      id = R.id.sign_up_button;
      AppCompatButton signUpButton = ViewBindings.findChildViewById(rootView, id);
      if (signUpButton == null) {
        break missingId;
      }

      id = R.id.user_confirm_password;
      EditText userConfirmPassword = ViewBindings.findChildViewById(rootView, id);
      if (userConfirmPassword == null) {
        break missingId;
      }

      id = R.id.user_email;
      EditText userEmail = ViewBindings.findChildViewById(rootView, id);
      if (userEmail == null) {
        break missingId;
      }

      id = R.id.user_name;
      EditText userName = ViewBindings.findChildViewById(rootView, id);
      if (userName == null) {
        break missingId;
      }

      id = R.id.user_password;
      EditText userPassword = ViewBindings.findChildViewById(rootView, id);
      if (userPassword == null) {
        break missingId;
      }

      id = R.id.welcome;
      TextView welcome = ViewBindings.findChildViewById(rootView, id);
      if (welcome == null) {
        break missingId;
      }

      return new ActivitySignUpBinding((RelativeLayout) rootView, haveAccount, iconImage,
          realestateApp, signUpButton, userConfirmPassword, userEmail, userName, userPassword,
          welcome);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
