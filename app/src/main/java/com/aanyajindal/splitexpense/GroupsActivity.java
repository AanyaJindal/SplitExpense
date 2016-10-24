package com.aanyajindal.splitexpense;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GroupsActivity extends AppCompatActivity {

    FloatingActionButton fabAddGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabAddGroup = (FloatingActionButton) findViewById(R.id.fab_add_group);
        fabAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddGroupActivity.class);
                startActivity(i);
            }
        });

    }
}
