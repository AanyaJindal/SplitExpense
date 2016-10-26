package com.aanyajindal.splitexpense;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aanyajindal.splitexpense.Models.User;
import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";

    public static final int PICK_IMAGE_REQUEST = 51;
    EditText etName,etEmail,etContact;
    de.hdodenhof.circleimageview.CircleImageView ivCurrentDP;
    Button btnSaveChanges;
    FloatingActionButton fabChangeDP;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference storageRef;
    DatabaseReference mainDatabase,usersList;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivCurrentDP = (CircleImageView) findViewById(R.id.iv_current_dp);
        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etContact = (EditText) findViewById(R.id.et_contact);
        fabChangeDP = (FloatingActionButton) findViewById(R.id.fab_changePhoto);
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

        fabChangeDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        mainDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://split-expense-23af6.appspot.com/user-dps/" + user.getUid());

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

            Log.d(TAG, "onActivityResult: "+uri.hashCode());
            Uri destination = Uri.fromFile(new File(getCacheDir(),"cropped"+uri.hashCode()));
            Crop.of(uri,destination).asSquare().start(this);
            Glide.with(this).load(destination).fitCenter().into(ivCurrentDP);
            uploadPhoto(uri);

        }


    }

    protected void uploadPhoto(Uri uri) {

        Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show();

        storageRef.putFile(uri)
                .addOnSuccessListener(EditProfileActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "uploadPhoto:onSuccess:" + taskSnapshot.getMetadata().getReference().getPath());

                        Log.d(TAG, "onSuccess: " + storageRef.getDownloadUrl().toString());

                        Toast.makeText(EditProfileActivity.this, "Image uploaded",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "uploadPhoto:onError", e);
                        Toast.makeText(EditProfileActivity.this, "Upload failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
