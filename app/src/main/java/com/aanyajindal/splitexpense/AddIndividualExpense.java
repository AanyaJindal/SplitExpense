package com.aanyajindal.splitexpense;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddIndividualExpense extends AppCompatActivity {

    EditText etInputMode, etInputCategory, etInputDate, etInputTime;
    Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_individual_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Expense");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etInputMode = (EditText) findViewById(R.id.et_input_mode);
        etInputCategory = (EditText) findViewById(R.id.et_input_category);
        etInputTime = (EditText) findViewById(R.id.et_input_time);
        etInputDate = (EditText) findViewById(R.id.et_input_date);


        etInputMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openModeDialog();
            }
        });

        etInputCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDialog();
            }
        });

        etInputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddIndividualExpense.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(year,month,dayOfMonth);
                        String myFormat = "MM/dd/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                        etInputDate.setText(sdf.format(myCalendar.getTime()));
                    }
                },myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etInputTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddIndividualExpense.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        myCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        myCalendar.set(Calendar.MINUTE,minute);
                        etInputTime.setText(hourOfDay+":"+minute);
                    }
                },myCalendar.get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE),false).show();
            }
        });

    }

    public void openModeDialog() {
        AlertDialog.Builder chooseMode = new AlertDialog.Builder(AddIndividualExpense.this);
        chooseMode.setTitle("Choose mode of payment");
        final String[] modes = {"Cash", "Credit card", "Net Banking"};
        chooseMode.setSingleChoiceItems(modes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etInputMode.setText(modes[which]);
            }
        });
        chooseMode.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        chooseMode.setNegativeButton("Cancel", null);
        chooseMode.create().show();
    }

    public void openCategoryDialog() {
        AlertDialog.Builder category = new AlertDialog.Builder(AddIndividualExpense.this);
        category.setTitle("Choose category");
        final String[] categories = {"Food", "Transportation","Bills and utilities","Medical","Household","Clothes","Gifts", "Education","Leisure"};
        category.setSingleChoiceItems(categories, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etInputCategory.setText(categories[which]);
            }
        });
        category.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        category.setNegativeButton("Cancel", null);
        category.create().show();
    }

}
