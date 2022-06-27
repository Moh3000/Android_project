package com.example.project;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button profileButton = (Button) findViewById(R.id.profile_button);
        Button logoutButton = (Button) findViewById(R.id.logout_button);
        Button displayTasksButton = (Button) findViewById(R.id.display_button);
        Button addTaskButton = (Button) findViewById(R.id.add_task_button);
        Button editTaskButton = (Button) findViewById(R.id.edit_task_button);
        Button deleteTaskButton = (Button) findViewById(R.id.delete_task_button);
        Button completeTaskButton = (Button) findViewById(R.id.complete_task_button);

        EditText idEditText = (EditText) findViewById(R.id.edit_text_id);

        String[] options = {"Today", "This Week", "All", "Custom"};
        Spinner displaySpinner = (Spinner) findViewById(R.id.display_spinner);
        ArrayAdapter<String> objDisplayArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        displaySpinner.setAdapter(objDisplayArr);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        displayTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout scrollView = (LinearLayout) findViewById(R.id.scroll_layout);

                String displayOption = displaySpinner.getSelectedItem().toString();

                DataBaseHelper dbh = new DataBaseHelper(MainActivity.this, "TasksDB", null, 1);

                if(displayOption.equals(options[0])) {
                    Date today = new Date();
                    today.setMinutes(0);
                    today.setHours(0);
                    today.setSeconds(0);
                    Cursor allTasksCursor = dbh.searchTaskUsingDate((int)today.getTime());
                    scrollView.removeAllViews();
                    while(allTasksCursor.moveToNext()) {
                        TextView textView = new TextView(MainActivity.this);
                        textView.setPadding(10,10,10,10);
                        String text = "Id: " + allTasksCursor.getString(0)
                                +"\nTitle: " + allTasksCursor.getString(1)
                                +"\nBody: " + allTasksCursor.getString(2);
                        Date date = new Date(allTasksCursor.getString(3));
                        text = text + date.toString();
                        textView.setText(text);
                        scrollView.addView(textView);
                    }
                    System.out.println("shows today's tasks");
                }
                else if(displayOption.equals(options[1])) {
                    Date today = new Date();
                    today.setHours(0);
                    today.setMinutes(0);
                    today.setSeconds(0);
                    Date lastDayOfThisWeek = new Date();
                    lastDayOfThisWeek.setHours(0);
                    lastDayOfThisWeek.setMinutes(0);
                    lastDayOfThisWeek.setSeconds(0);
                    lastDayOfThisWeek.setDate(today.getDate()+7);
                    Cursor allTasksCursor = dbh.searchTaskUsing2Dates( (int) (today.getTime()), (int) (lastDayOfThisWeek.getTime()));
                    scrollView.removeAllViews();
                    while(allTasksCursor.moveToNext()) {
                        TextView textView = new TextView(MainActivity.this);
                        textView.setPadding(10,10,10,10);
                        String text = "Id: " + allTasksCursor.getString(0)
                                +"\nTitle: " + allTasksCursor.getString(1)
                                +"\nBody: " + allTasksCursor.getString(2);
                        Date date = new Date(allTasksCursor.getString(3));
                        text = text + date.toString();
                        textView.setText(text);
                        scrollView.addView(textView);
                    }
                    System.out.println("shows this week's tasks");
                }
                else if(displayOption.equals(options[2])) {
                    Cursor allTasksCursor = dbh.searchAllTasks();
                    scrollView.removeAllViews();
                    while(allTasksCursor.moveToNext()) {
                        TextView textView = new TextView(MainActivity.this);
                        textView.setPadding(10,10,10,10);
                        String text = "Id: " + allTasksCursor.getString(0)
                                +"\nTitle: " + allTasksCursor.getString(1)
                                +"\nBody: " + allTasksCursor.getString(2);
                        Date date = new Date(Integer.parseInt(allTasksCursor.getString(3)));
                        text = text + "\nDate: " + date.toString();
                        textView.setText(text);
                        scrollView.addView(textView);
                    }
                    System.out.println("shows all tasks");
                }
                else if(displayOption.equals(options[3])) {
                    EditText fromEditText = (EditText) findViewById(R.id.fromDate_text);
                    EditText toEditText = (EditText) findViewById(R.id.toDate_text);
                    StringTokenizer fromst = new StringTokenizer(fromEditText.getText().toString(), "/");
                    StringTokenizer tost = new StringTokenizer(toEditText.getText().toString(), "/");
                    Date dateFrom = new Date();
                    Date dateTo = new Date();

                    dateFrom.setDate(Integer.parseInt(fromst.nextToken()));
                    dateFrom.setMonth(Integer.parseInt(fromst.nextToken()));
                    dateFrom.setYear(Integer.parseInt(fromst.nextToken()));
                    dateFrom.setHours(0);
                    dateFrom.setMinutes(0);
                    dateFrom.setSeconds(0);

                    dateTo.setDate(Integer.parseInt(tost.nextToken()));
                    dateTo.setMonth(Integer.parseInt(tost.nextToken()));
                    dateTo.setYear(Integer.parseInt(tost.nextToken()));
                    dateTo.setHours(0);
                    dateTo.setMinutes(0);
                    dateTo.setSeconds(0);

                    Cursor allTasksCursor = dbh.searchTaskUsing2Dates((int) dateFrom.getTime(),(int) dateTo.getTime());
                    scrollView.removeAllViews();
                    while(allTasksCursor.moveToNext()) {
                        TextView textView = new TextView(MainActivity.this);
                        textView.setPadding(10,10,10,10);
                        String text = "Id: " + allTasksCursor.getString(0)
                                +"\nTitle: " + allTasksCursor.getString(1)
                                +"\nBody: " + allTasksCursor.getString(2);
                        Date date = new Date(Integer.parseInt(allTasksCursor.getString(3)));
                        text = text + "\nDate: " + date.toString();
                        textView.setText(text);
                        scrollView.addView(textView);
                    }

                    System.out.println("shows tasks according to date given by user");
                }
//                System.out.println(dbh.isAllComplete() + "ajkfahkjfh");
                if(dbh.isAllComplete()) {
                    Toast.makeText(MainActivity.this, "Congratulations, all tasks are complete", Toast.LENGTH_LONG).show();
                }
            }
        });

        String[] editOptions = {"Title", "Body", "Date"};
        Spinner editSpinner = (Spinner) findViewById(R.id.edit_type_spinner);
        ArrayAdapter<String> objEditArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, editOptions);
        editSpinner.setAdapter(objEditArr);

        editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dbh = new DataBaseHelper(MainActivity.this, "TasksDB", null, 1);
                EditText editSpace = (EditText) findViewById(R.id.edit_task_space);

                String editOption = editSpinner.getSelectedItem().toString();

                if(editOption.equals(editOptions[0])) {
                    dbh.updateTaskTitle(Integer.parseInt(idEditText.getText().toString()), editSpace.getText().toString());
                }
                else if(editOption.equals(editOptions[1])) {
                    dbh.updateTaskBody(Integer.parseInt(idEditText.getText().toString()), editSpace.getText().toString());
                }
                else if(editOption.equals(editOptions[2])) {
                    StringTokenizer st = new StringTokenizer(editSpace.getText().toString(),"/");
                    int day = Integer.parseInt(st.nextToken());
                    int month = Integer.parseInt(st.nextToken());
                    int year = Integer.parseInt(st.nextToken());

                    Date date = new Date();
                    date.setYear(year);
                    date.setMonth(month);
                    date.setDate(day);
                    date.setHours(0);
                    date.setMinutes(0);
                    date.setSeconds(0);

                    dbh.updateTaskDate(Integer.parseInt(idEditText.getText().toString()), (int) date.getTime());
                }

                Toast.makeText(MainActivity.this, "Task was updated", Toast.LENGTH_SHORT).show();
            }
        });

        deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper db = new DataBaseHelper(MainActivity.this, "TasksDB", null, 1);

                db.deleteTask(Integer.parseInt(idEditText.getText().toString()));

                Toast.makeText(MainActivity.this, "Task was deleted", Toast.LENGTH_SHORT).show();
            }
        });

        completeTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper db = new DataBaseHelper(MainActivity.this, "TasksDB", null, 1);

                db.completeTask(Integer.parseInt(idEditText.getText().toString()));

                Toast.makeText(MainActivity.this, "Task was completed", Toast.LENGTH_SHORT).show();


            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FirstPage.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

    }
}