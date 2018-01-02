package com.emi.jonat.beerhaus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import static com.emi.jonat.beerhaus.BeerFragment.LOG_TAG;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView emailAddress;
    private TextView shortBio;
    private TextView UserName;
    private TextView PhoneNumber;
    private FirebaseAuth mFirebaseAuth;
    private String UserNameInfo;
    private FirebaseUser mFirebaseUser;
    public static final String ANONYMOUS = "anonymous";
    private String profileImages;
    private String email;
    private String metadata;
    private String mPhonenumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserNameInfo = ANONYMOUS;

        profileImage = (ImageView) findViewById(R.id.user_profile_photo);
        emailAddress = (TextView) findViewById(R.id.email_address);
        shortBio = (TextView) findViewById(R.id.user_profile_short_bio);
        UserName = (TextView) findViewById(R.id.user_profile_name);
        PhoneNumber = (TextView) findViewById(R.id.phone_number);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            UserNameInfo = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                profileImages = mFirebaseUser.getPhotoUrl().toString();
            }
            if (mFirebaseUser.getEmail() != null) {
                email = mFirebaseUser.getEmail();
            }

            if(mFirebaseUser.getPhoneNumber() != null) {
                mPhonenumbers = mFirebaseUser.getPhoneNumber();
                Log.d(LOG_TAG, "phone number" + mPhonenumbers);
            }else{
                mPhonenumbers = getResources().getString(R.string.unavailabledata);
            }
        }

        if(profileImages != null) {
            Picasso.with(getApplicationContext()).load(profileImages)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(profileImage);
        }
        emailAddress.setText(email);
        UserName.setText(UserNameInfo);
        if(mPhonenumbers!= null || mPhonenumbers.contains("")) {
            PhoneNumber.setText(mPhonenumbers);
        }else{
            PhoneNumber.setText(getResources().getString(R.string.unavailabledata));
        }
        shortBio.setText(getResources().getString(R.string.somme));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
