package com.aanyajindal.splitexpense;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.bluzwong.swipeback.SwipeBackActivityHelper;

public class GroupsActivity extends AppCompatActivity {

    FloatingActionButton fabAddGroup;

    SwipeBackActivityHelper helper = new SwipeBackActivityHelper();

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

        fabAddGroup = (FloatingActionButton) findViewById(R.id.fab_add_group);
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
}
