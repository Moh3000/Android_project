package com.example.project;

import java.util.ArrayList;
import java.util.Date;

public class Task {
    public ArrayList<Task> tasks = new ArrayList<Task>();
    private String taskTitle;
    private String taskBody;
    //0: NOT COMPLETED; 1: COMPLETED
    private boolean taskCompleted;
    private Date taskDate;
    private int taskId;

    public Task() {

    }
    public Task(int id, String title, String body, Date date, boolean completed) {
        this.taskId = id;
        this.taskTitle = title;
        this.taskBody = body;
        this.taskCompleted = completed;
        this.taskDate.setDate(date.getDate());
    }
    public int getTaskId() {
        return this.taskId;
    }
    public void setTaskId(int id) {
        if(id > 0) {
            this.taskId = id;
        }
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }
    public void setTaskTitle(String title) {
        this.taskTitle = title;
    }

    public String getTaskBody() {
        return this.taskBody;
    }
    public void setTaskBody(String body) {
        this.taskBody = body;
    }

    public boolean isTaskCompleted() {
        return this.taskCompleted;
    }
    public void setTaskComplete(boolean complete) {
        this.taskCompleted = complete;
    }

    public Date getTaskDate() {
        return this.taskDate;
    }
    public void setTaskDate(Date date) {
        this.taskDate = date;
    }

    public String printTask() {
        String task = "Id: " + this.taskId
                +"\nDate: " + this.taskDate.getDate()
                +"\nTitle: " + this.taskTitle
                +"\n\nBody: " + this.taskBody;
        if(this.taskCompleted)
            return task + "\n\nState: Completed";
        else
            return task + "\n\nState: Not completed";
    }
}
