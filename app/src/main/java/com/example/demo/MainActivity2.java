package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText suser,sid,spass1,semail,spass2;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        suser = findViewById(R.id.user);
        sid = findViewById(R.id.id);
        spass1 = findViewById(R.id.pass1);
        semail = findViewById(R.id.email);
        spass2 = findViewById(R.id.pass2);
        signup = findViewById(R.id.signup);


        //,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user= suser.getText().toString();
                String id= sid.getText().toString();
                String pass1= spass1.getText().toString();
                String email= semail.getText().toString();
                String pass2= spass2.getText().toString();


                if (user.length()>0){
                    if(id.length()>0){
                        if(email.length()>0){

                            if(pass1.length()>0){
                                if(pass2.length()>0){
                                    Toast.makeText(getApplicationContext(),"Registration Successfull!",Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(MainActivity2.this,MainActivity3.class));
                                }
                                else{
                                    spass2.setError("Password");
                                }

                            }
                            else {
                                spass1.setError("Password");
                            }
                        }
                        else {
                            semail.setError("Email!");
                        }

                    }
                    else{
                        sid.setError("Student id");

                    }
                }
                else {
                    suser.setError("Input Name");
                    sid.setError("Student id");
                    semail.setError("Email!");
                    spass1.setError("Password");
                    spass2.setError("Password");
                }


            }






        });




    }
}