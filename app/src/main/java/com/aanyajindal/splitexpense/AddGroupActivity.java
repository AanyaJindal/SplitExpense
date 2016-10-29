package com.aanyajindal.splitexpense;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aanyajindal.splitexpense.Models.Group;
import com.aanyajindal.splitexpense.Models.User;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.aanyajindal.splitexpense.EditProfileActivity.PICK_IMAGE_REQUEST;

public class AddGroupActivity extends AppCompatActivity {

    private static final String TAG = "AddGroupActivity";

    EditText etGroupName, etGroupDesct;
    de.hdodenhof.circleimageview.CircleImageView ivGroupPic;
    Button btnAddGroup;
    FloatingActionButton fabAddGroupPic;
    Uri uri;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference storageRef;
    DatabaseReference mainDatabase, groupsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        etGroupDesct = (EditText) findViewById(R.id.et_group_desc);
        etGroupName = (EditText) findViewById(R.id.et_group_name);
        ivGroupPic = (CircleImageView) findViewById(R.id.iv_group_pic);
        btnAddGroup = (Button) findViewById(R.id.btn_add_group);
        fabAddGroupPic = (FloatingActionButton) findViewById(R.id.fab_add_group_photo);

        Glide
                .with(this)
                .load(R.drawable.ic_group_black_24dp)
                .placeholder(R.drawable.ic_group_black_24dp)
                .fitCenter()
                .into(ivGroupPic);

        fabAddGroupPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        mainDatabase = FirebaseDatabase.getInstance().getReference();

        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
                String formattedDate = df.format(c.getTime());
                Log.d(TAG, "onClick: "+ formattedDate);

                Group newGroup = new Group(etGroupName.getText().toString(), etGroupDesct.getText().toString(), formattedDate, " ");
                groupsList = mainDatabase.child("groups");
                String groupId = groupsList.push().getKey();
                groupsList.child(groupId).setValue(newGroup);

                mainDatabase.child("group-users").child(groupId).child(user.getUid()).setValue("true");

                mainDatabase.child("user-groups").child(user.getUid()).child(groupId).setValue("true")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent i = new Intent(getApplicationContext(), EditGroupActivity.class);
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
            Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"+uri.hashCode()));
            Crop.of(uri,destination).asSquare().start(this);
            Glide.with(this).load(destination).fitCenter().into(ivGroupPic);
            //uploadPhoto(uri);
        }
    }
}
