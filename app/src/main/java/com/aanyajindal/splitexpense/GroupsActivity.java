package com.aanyajindal.splitexpense;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aanyajindal.splitexpense.Models.Group;
import com.aanyajindal.splitexpense.adapters.GroupsAdapter;
import com.github.bluzwong.swipeback.SwipeBackActivityHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupsActivity extends AppCompatActivity {

    private static final String TAG = "GroupsActivity";
    FloatingActionButton fabAddGroup;
    ArrayList<String> groupIDArrayList = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    GroupsAdapter adapter;
    SwipeBackActivityHelper helper = new SwipeBackActivityHelper();
    RecyclerView rvGroupsList;
    ArrayList<Group> groupsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        helper.setEdgeMode(true)
                .setParallaxMode(true)
                .setParallaxRatio(3)
                .setNeedBackgroundShadow(true)
                .init(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rvGroupsList = (RecyclerView) findViewById(R.id.rv_groups_list);
        fabAddGroup = (FloatingActionButton) findViewById(R.id.fab_add_group);
        rvGroupsList.setLayoutManager(new LinearLayoutManager(this));

        getGroupIDs(user);

        fabAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddGroupActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        helper.finish();
    }

    public void getGroupIDs(final FirebaseUser user) {
        DatabaseReference userGroupsList = FirebaseDatabase.getInstance().getReference()
                .child("user-groups")
                .child(user.getUid());
        final DatabaseReference groupsRef = FirebaseDatabase.getInstance().getReference().child("groups");

        userGroupsList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    String string = dataSnap.getKey();
                    Log.d(TAG, "onDataChange: " + string);
                    groupsRef.child(string).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {
                            Group group = dataSnapshot1.getValue(Group.class);
                            groupsList.add(group);
                            Log.d(TAG, "onDataChange: "+groupsList);
                            GroupsAdapter gadapter = new GroupsAdapter(groupsList,GroupsActivity.this);
                            rvGroupsList.setAdapter(gadapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
