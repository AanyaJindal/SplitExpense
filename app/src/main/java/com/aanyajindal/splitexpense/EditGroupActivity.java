package com.aanyajindal.splitexpense;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class EditGroupActivity extends AppCompatActivity {

    ImageButton ibAddMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        ibAddMember = (ImageButton) findViewById(R.id.iv_add_member);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Group Title");
        getSupportActionBar().setSubtitle("Created on: "+ "creation time");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_edit_group);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ibAddMember.setOnClickListener(new View.OnClickListener() {
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
        openEditDialog();
        return super.onOptionsItemSelected(item);
    }

    public void openEditDialog() {

        LayoutInflater li = LayoutInflater.from(this);
        View editDialogView = li.inflate(R.layout.dialog_edit_group, null);
        AlertDialog.Builder editDialog = new AlertDialog.Builder(this);
        editDialog
                .setTitle("Edit name and description")
                .setView(editDialogView);

        editDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        editDialog.setNegativeButton("Cancel", null);
        editDialog.create().show();
    }
}
