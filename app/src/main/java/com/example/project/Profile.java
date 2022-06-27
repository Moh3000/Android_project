package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Profile.this.setTitle("Profile page");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        final EditText email =
                (EditText)findViewById(R.id.signUpEmail1);
        final EditText firstName =
                (EditText)findViewById(R.id.signUpFirstName1);
        final EditText lastName =
                (EditText)findViewById(R.id.signUpLastName1);
        final EditText password =
                (EditText)findViewById(R.id.signUpPassword1);
        final EditText confirmPassword =
                (EditText)findViewById(R.id.signUpConfirmPassword1);

        email.setText(FirstPage.mail);
        email.setFocusable(false);
        DataBaseHelper db =new DataBaseHelper(Profile.this,"TasksDB",null,1);
        Cursor user = db.searchUser(FirstPage.mail);
        user.moveToNext();
        firstName.setText( user.getString(1));
        lastName.setText( user.getString(2));
        password.setText( user.getString(3));
        confirmPassword.setText( user.getString(3));

        Button btnret = (Button) findViewById(R.id.ret2);
        Button btnupdate = (Button) findViewById(R.id.btnUpdateProfile1);
        btnret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile.this,FirstPage.class);

                Profile.this.startActivity(intent);
                finish();

            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*System.out.println("*"+signUpLastNameage.getText().toString()+"////////////////////////////////////////");
                if(signUpLastNameage.getText().toString().equals("")){
                    signUpEmailage.setBackgroundColor(Color.parseColor("#ffffff"));

                    //signUpLastNameage.setError("sense");
                    signUpLastNameage.setHighlightColor(android.R.color.black);
                    //signUpLastNameage.setBackgroundColor();

                }
                else {*/
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String emailAddress = email.getText().toString();
                String firstNameText = firstName.getText().toString();
                String lastNameText = lastName.getText().toString();
                String passwordText = password.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();


                if(emailAddress.equals("") || firstNameText.equals("") || lastNameText.equals("") || passwordText.equals("") || confirmPasswordText.equals("")  ){

                    Toast message = Toast.makeText(Profile.this, "You Need To Fill Up All Fields!!!", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }
                Cursor user = db.searchUser(emailAddress);


                if (!emailAddress.matches(emailPattern) || emailAddress.length() == 0)
                {
                    email.setError("Invalid Email");
                    Toast.makeText(Profile.this,"Invalid Email !!!",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!passwordText.equals(confirmPasswordText)){
                    confirmPassword.setError("Passwords Doesn't Match");
                    Toast message = Toast.makeText(Profile.this, "Passwords Doesn't Match!!!", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }else if(firstNameText.length() < 3 || firstNameText.length() >20){
                    firstName.setError("fist Name Must Be At Least 3 and Max 20 Characters Length");
                    Toast message = Toast.makeText(Profile.this, "fist Name Must Be At Least 3 and Max 20 Characters Length", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }
                else if(lastNameText.length() < 3 || lastNameText.length() >20){
                    lastName.setError("Last Name Must Be At Least 3 and Max 20 Characters Length");
                    Toast message = Toast.makeText(Profile.this, "Last Name Must Be At Least 3 and Max 20 Characters Length", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }else if(!validatePassword(passwordText)){
                    password.setError("Password Must Be At Least 8 Characters Length And AT most 15 Characters Contains At least  one lowercase letter, one uppercase letter" +
                            "and One Number");
                    Toast message = Toast.makeText(Profile.this, "Password Must Be At Least 8 Characters Length And AT most 15 Characters Contains At least  one lowercase letter, one uppercase letter" +
                            "and One Number", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }


                db.updateUser(emailAddress,firstNameText ,lastNameText,passwordText);
                Intent intent=new Intent(Profile.this,FirstPage.class);

                Profile.this.startActivity(intent);
                finish();}

        });

    }
    private boolean validatePassword(String password){
        if(password.length() < 8  || password.length() > 15 )
            return false;
        boolean chars = false, specialChars = false, numbers = false;
        for(int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (c >= '0' && c <= '9')
                numbers = true;
            else if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
                chars = true;

        }
        return chars  && numbers;
    }
}

