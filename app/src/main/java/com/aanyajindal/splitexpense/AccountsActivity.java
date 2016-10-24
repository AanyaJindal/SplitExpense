package com.aanyajindal.splitexpense;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountsActivity extends AppCompatActivity {

    //TODO: change the way we are displaying text, take from firebase databse

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(user.getDisplayName());




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountsActivity.this,EditProfileActivity.class);
                startActivity(intent);
            }
        });

        ImageView ivProfilePic = (ImageView) findViewById(R.id.iv_profile_pic);
        TextView tvEmailItem = (TextView) findViewById(R.id.tv_email_item);


        Glide
                .with(this)
                .load(user.getPhotoUrl())
                .placeholder(R.drawable.ic_person_black_24dp)
                .crossFade()
                .centerCrop()
                .into(ivProfilePic);

        tvEmailItem.setText(user.getEmail());


        //TODO: display contact number and groups when they are available through the database
    }
}
