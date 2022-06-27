package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class FirstPage extends AppCompatActivity {

    private Button sinup ;
    private Button login ;

    public static String name ;
    public static String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        sinup=(Button) findViewById(R.id.btnSignUp);
        login=(Button) findViewById(R.id.btnSignIn);
        EditText email = (EditText) findViewById(R.id.signInEmail);
        EditText password = (EditText) findViewById(R.id.signInPassword);
        CheckBox rememberMe = (CheckBox) findViewById(R.id.rememberMe);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstPage.this, Sign_up.class);

                startActivity(intent);
                finish();
            }
        });
        email.setText(sharedPrefManager.readString("Email", ""));
        password.setText(sharedPrefManager.readString("Password", ""));
        login.setOnClickListener(v -> {
            mail = email.getText().toString();
            String pass = password.getText().toString();
            Intent intent = new Intent(FirstPage.this, Sign_up.class);
            if (mail.length() == 0 || pass.length() == 0) {
                Toast toast = Toast.makeText(FirstPage.this, "Enter both email and password", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }else{

                DataBaseHelper db = new DataBaseHelper(FirstPage.this, "TasksDB", null, 1);
                Cursor user = db.searchUser(email.getText().toString());


                if (user == null || user.getCount() == 0  ) {

                    Toast message = Toast.makeText(FirstPage.this, "Wrong email !!", Toast.LENGTH_LONG);
                    message.show();
                    return ;
                }
                user.moveToNext();
                String correctPassword = "";
                correctPassword=user.getString(3);
                System.out.println(correctPassword);
                if(!correctPassword.equals(pass)){
                    Toast message = Toast.makeText(FirstPage.this, "Wrong password !!", Toast.LENGTH_LONG);
                    message.show();
                    return ;
                }else {
                    if (rememberMe.isChecked()) {
                        sharedPrefManager.writeString("Email", mail);
                        sharedPrefManager.writeString("Password",password.getText().toString());
                        Toast.makeText(FirstPage.this, "Email saved to shared Preferences successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            Intent homeIntent = new Intent(FirstPage.this, MainActivity.class);
            startActivity(homeIntent);
            finish();





        });


    }
}