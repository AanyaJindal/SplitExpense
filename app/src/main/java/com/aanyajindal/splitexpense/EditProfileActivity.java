package com.aanyajindal.splitexpense;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aanyajindal.splitexpense.Models.User;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {


    public static final int PICK_IMAGE_REQUEST = 51;
    EditText etName,etEmail,etContact;
    de.hdodenhof.circleimageview.CircleImageView ivCurrentDP;
    Button btnSaveChanges,btnChangeDP;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mainDatabase,usersList;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ivCurrentDP = (CircleImageView) findViewById(R.id.iv_current_dp);
        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etContact = (EditText) findViewById(R.id.et_contact);
        btnChangeDP = (Button) findViewById(R.id.btn_change_dp);
        btnSaveChanges = (Button) findViewById(R.id.btn_save_changes);

        etName.setText(user.getDisplayName());
        etName.setSelection(etName.getText().length());
        etEmail.setText(user.getEmail());
        etEmail.setSelection(etEmail.getText().length());
        //TODO:set contact to current value by retrieving from database
        etContact.setText("");
        etContact.setSelection(etContact.getText().length());
        Glide
                .with(this)
                .load(user.getPhotoUrl())
                .fitCenter()
                .into(ivCurrentDP);

        btnChangeDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        mainDatabase = FirebaseDatabase.getInstance().getReference();
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User(etName.getText().toString(), etEmail.getText().toString(), etContact.getText().toString(), user.getPhotoUrl().toString());
                usersList = mainDatabase.child("users");
                usersList.child(user.getUid()).setValue(newUser);

                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newUser.getName())
                       // .setPhotoUri()
                        .build();
                user.updateProfile(userProfileChangeRequest)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Intent i = new Intent(getApplicationContext(), AccountsActivity.class);
                                    startActivity(i);
                                }
                            }
                        });



            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();
            Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
            Crop.of(uri,destination).asSquare().start(this);
            Glide.with(this).load(destination).fitCenter().into(ivCurrentDP);
            //uploadPhoto(uri);
        }
    }
}
