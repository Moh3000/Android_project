package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button saveTaskButton = (Button) findViewById(R.id.update_task_button);
        Button backToMainActivityButton = (Button) findViewById(R.id.back_from_update_to_main_activity_button);

        EditText editTextTitle = (EditText) findViewById(R.id.update_title_text);
        EditText editTextBody = (EditText) findViewById(R.id.update_body_text);
//        EditText editTextDate = (EditText) findViewById(R.id.update_date_text);

        backToMainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                AddTaskActivity.this.startActivity(intent);
                finish();
            }
        });

        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task();
                if(editTextTitle.getText().toString().isEmpty())
                    task.setTaskTitle("No Title");
                else
                    task.setTaskTitle(editTextTitle.getText().toString());

                if(editTextBody.getText().toString().isEmpty())
                    task.setTaskBody("No Body");
                else
                    task.setTaskBody(editTextBody.getText().toString());

//                if(editTextDate.getText().toString().isEmpty()){
//                    Date date = new Date();
//                    task.setTaskDate(date);
//                }
//                else {
//                    StringTokenizer st = new StringTokenizer(editTextDate.getText().toString(), "/");
//                    int day = Integer.parseInt(st.nextToken());
//                    int month = Integer.parseInt(st.nextToken());
//                    int year = Integer.parseInt(st.nextToken());
//                    Date date = new Date(year, month, day);
//                    task.setTaskDate(date);
//                }
                CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
                Date date = new Date(calendarView.getDate());
                date.setHours(0);
                date.setMinutes(0);
                date.setSeconds(0);
                task.setTaskDate(date);
                System.out.println(task.getTaskDate().getTime());

                DataBaseHelper dataBaseHelper = new DataBaseHelper(AddTaskActivity.this, "TasksDB", null, 1);
                dataBaseHelper.insertTask(task);

                Toast toast = Toast.makeText(AddTaskActivity.this, "Task Saved", Toast.LENGTH_LONG);
                toast.show();

                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                AddTaskActivity.this.startActivity(intent);
                finish();
            }
        });
    }
}