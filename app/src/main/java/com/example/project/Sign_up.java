package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Sign_up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Sign_up.this.setTitle("Sign Up");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final EditText email =
                (EditText)findViewById(R.id.signUpEmailage);
        final EditText firstName =
                (EditText)findViewById(R.id.signUpFirstNameage);
        final EditText lastName =
                (EditText)findViewById(R.id.signUpLastNameage);
        final EditText password =
                (EditText)findViewById(R.id.signUpPasswordage);
        final EditText confirmPassword =
                (EditText)findViewById(R.id.signUpConfirmPasswordage);
        Button btnret = (Button) findViewById(R.id.ret);
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Sign_up.this,FirstPage.class);

                Sign_up.this.startActivity(intent);
                finish();

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
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

                    Toast message = Toast.makeText(Sign_up.this, "You Need To Fill Up All Fields!!!", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }
                DataBaseHelper db =new DataBaseHelper(Sign_up.this,"TasksDB",null,1);
                Cursor user = db.searchUser(emailAddress);





                if(user != null && user.getCount() > 0){
                    email.setError("Email Already Exists for another acoount");
                    Toast message = Toast.makeText(Sign_up.this, "Email Already Exists for another acoount!!", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }
                if (!emailAddress.matches(emailPattern) || emailAddress.length() == 0)
                {
                    email.setError("Invalid Email");
                    Toast.makeText(Sign_up.this,"Invalid Email !!!",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!passwordText.equals(confirmPasswordText)){
                    confirmPassword.setError("Passwords Doesn't Match");
                    Toast message = Toast.makeText(Sign_up.this, "Passwords Doesn't Match!!!", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }else if(firstNameText.length() < 3 || firstNameText.length() >20){
                    firstName.setError("fist Name Must Be At Least 3 and Max 20 Characters Length");
                    Toast message = Toast.makeText(Sign_up.this, "fist Name Must Be At Least 3 and Max 20 Characters Length", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }
                else if(lastNameText.length() < 3 || lastNameText.length() >20){
                    lastName.setError("Last Name Must Be At Least 3 and Max 20 Characters Length");
                    Toast message = Toast.makeText(Sign_up.this, "Last Name Must Be At Least 3 and Max 20 Characters Length", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }else if(!validatePassword(passwordText)){
                    password.setError("Password Must Be At Least 8 Characters Length And AT most 15 Characters Contains At least  one lowercase letter, one uppercase letter" +
                            "and One Number");
                    Toast message = Toast.makeText(Sign_up.this, "Password Must Be At Least 8 Characters Length And AT most 15 Characters Contains At least  one lowercase letter, one uppercase letter" +
                            "and One Number", Toast.LENGTH_LONG);
                    message.show();
                    return;
                }

                User newUser = new User( emailAddress,firstNameText ,lastNameText,passwordText);
                db.insertUser(newUser);
                Intent intent=new Intent(Sign_up.this, FirstPage.class);

                Sign_up.this.startActivity(intent);
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
