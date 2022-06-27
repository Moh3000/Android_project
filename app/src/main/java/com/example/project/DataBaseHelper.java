package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE USER(" +
                        "EMAIL TEXT PRIMARY KEY, " +
                        "FNAME TEXT, " +
                        "LNAME TEXT, " +
                        "PASSWOED TEXT" +
                        ")"
        );
        sqLiteDatabase.execSQL(
                "CREATE TABLE TASK(" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "TITLE TEXT, " +
                        "BODY TEXT, " +
                        "DATE INTEGER, " +
                        "COMPLETED INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", user.getEmaile());
        contentValues.put("FNAME", user.getFname());
        contentValues.put("LNAME", user.getLname());
        contentValues.put("PASSWOED", user.getPassword());
        sqLiteDatabase.insert("USER", null, contentValues);
    }

    public Cursor searchUser(String mail) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE EMAIL = ?", new String[] {mail});
    }

    public void updateUser(String email, String first, String last, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("FNAME", first);
        content.put("LNAME", last);
        content.put("PASSWOED", pass);
        db.update("USER", content, "EMAIL = ?", new String[] {email});
    }

    public void deleteUser(String mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM USER WHERE EMAIL = " + mail);
    }

    public Cursor getAllUser() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USER", null);
    }

    public void insertTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        String title = task.getTaskTitle();
        String body = task.getTaskBody();
        int date = (int) task.getTaskDate().getTime();
        int completed = (task.isTaskCompleted() ? 1 : 0); //0: NOT COMPLETED; 1: COMPLETED
        db.execSQL("INSERT INTO TASK " +
                "(TITLE, BODY, DATE, COMPLETED) " +
                "VALUES(" + '\"' + title + '\"' + "," + '\"' + body + '\"' + "," + '\"' + date + '\"' + "," + '\"' + completed + '\"' + ")");
    }

    public void updateTaskDate(int id, int date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "UPDATE TASK SET " +
                        "DATE = " + '\"' + date + '\"' + //date in seconds from 1/1/1970 00:00:00
                        "WHERE ID = " + '\"' + id + '\"'
        );
    }

    public void updateTaskTitle(int id, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "UPDATE TASK SET " +
                        "TITLE = " + '\"' + title + '\"' +
                        "WHERE ID = " + id
        );
    }

    public void updateTaskBody(int id, String body) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "UPDATE TASK SET " +
                        "BODY = " + '\"' + body + '\"' +
                        "WHERE ID = " + id
        );
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "DELETE FROM TASK WHERE ID = " + id
        );
    }

    public void completeTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "UPDATE TASK SET COMPLETED = 1 WHERE ID = " + id
        );
    }

    public Cursor searchAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM TASK WHERE COMPLETED = 0", null);
    }

    public Cursor searchTaskUsingId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM TASK WHERE ID = " + id + " AND COMPLETED = 0", null);
    }

    public Cursor searchTaskUsingDate(int date) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM TASK WHERE DATE = " + date + " AND COMPLETED = 0", null);
    }

    public Cursor searchTaskUsing2Dates(int date1, int date2) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM TASK WHERE DATE >= " + date1 + " AND DATE <= " + date2 + " AND COMPLETED = 0", null);
    }

    public boolean isAllComplete() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT * FROM TASK",null );
        Cursor cursor2 = db.rawQuery("SELECT * FROM TASK WHERE COMPLETED = 0", null);
        if(cursor1.getCount() > 0 && cursor2.getCount() == 0)
            return true;
        else
            return false;
    }
}
