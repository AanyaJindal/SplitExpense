package com.aanyajindal.splitexpense;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddIndividualExpense extends AppCompatActivity {

    EditText etInputMode, etInputCategory, etInputDate, etInputTime, etInputTags, etInputDesc, etInputAmount, etInputTitle;
    Calendar myCalendar = Calendar.getInstance();
    Button btnSaveIndividualExpense, btnCancelIndividualExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_individual_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Expense");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnSaveIndividualExpense = (Button) findViewById(R.id.btn_save_individual_expense);
        btnCancelIndividualExpense = (Button) findViewById(R.id.btn_cancel_individual_expense);

        etInputAmount = (EditText) findViewById(R.id.et_input_amount);
        etInputTitle = (EditText) findViewById(R.id.et_input_title);
        etInputDesc = (EditText) findViewById(R.id.et_input_desc);
        etInputTags = (EditText) findViewById(R.id.et_input_tags);
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
                openDatePickerDialog();
            }
        });
        etInputTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog();
            }
        });

        btnSaveIndividualExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etInputTitle.getText().toString();
                String amount = etInputAmount.getText().toString();
                String desc  = etInputDesc.getText().toString();
                String tags = etInputTags.getText().toString();
                String mode = etInputMode.getText().toString();
                String category = etInputCategory.getText().toString();
                String date = etInputDate.getText().toString();
                String time = etInputTime.getText().toString();
            }
        });
        btnCancelIndividualExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AddIndividualExpense.this,HomeActivity.class));
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
        final String[] categories = {"Food", "Transportation", "Bills and utilities", "Medical", "Household", "Clothes", "Gifts", "Education", "Leisure"};
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

    public void openDatePickerDialog() {
        new DatePickerDialog(AddIndividualExpense.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(year, month, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                etInputDate.setText(sdf.format(myCalendar.getTime()));
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void openTimePickerDialog() {
        new TimePickerDialog(AddIndividualExpense.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                etInputTime.setText(hourOfDay + ":" + minute);
            }
        }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false).show();
    }

}
