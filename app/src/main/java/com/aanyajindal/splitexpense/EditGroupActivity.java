package com.aanyajindal.splitexpense;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aanyajindal.splitexpense.Models.Group;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditGroupActivity extends AppCompatActivity {

    private static final String TAG = "EditGroupActivity";
    Group group;
    ImageButton ibAddMember;
    EditText adEtName, adEtDesc;
    TextView tvGrpDesc;
    ImageView ivGroupDp;
    DatabaseReference groupRef;
    ActionBar actionBar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        String id = getIntent().getStringExtra("group-id");
        Log.d(TAG, id);
        ibAddMember = (ImageButton) findViewById(R.id.iv_add_member);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Group Title");
        groupRef = FirebaseDatabase.getInstance().getReference().child("groups").child(id);
        tvGrpDesc = (TextView) findViewById(R.id.tv_grp_desc);
        ivGroupDp = (ImageView) findViewById(R.id.iv_profile_pic);

        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                group = dataSnapshot.getValue(Group.class);
                collapsingToolbarLayout.setTitle(group.getName());
                tvGrpDesc.setText(group.getDesc());
                Glide.with(EditGroupActivity.this).load(group.getPic()).into(ivGroupDp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_edit_group);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditDialog();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_group, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void openEditDialog() {

        LayoutInflater li = LayoutInflater.from(this);
        View editDialogView = li.inflate(R.layout.dialog_edit_group, null);
        AlertDialog.Builder editDialog = new AlertDialog.Builder(this);
        editDialog.setView(editDialogView);

        adEtName = (EditText) editDialogView.findViewById(R.id.ad_et_name);
        adEtDesc = (EditText) editDialogView.findViewById(R.id.ad_et_description);

        editDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                groupRef
                        .setValue(new Group(adEtName.getText().toString(),adEtDesc.getText().toString(),group.getCreationTime(),group.getPic()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                            }
                        });
            }
        });

        editDialog.setNegativeButton("Cancel", null);
        editDialog.create().show();
    }
}
