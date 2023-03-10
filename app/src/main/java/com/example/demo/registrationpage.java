package com.example.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registrationpage extends AppCompatActivity {

    EditText suser,sid,spass1,semail,spass2;
    TextView signup;
    String emailReg = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String userEmail = "[a-z]{2,4}_[^0]{1}[0-9]{9}+@lus.ac.bd";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationpage);
        suser = findViewById(R.id.user);
        sid = findViewById(R.id.id);
        spass1 = findViewById(R.id.pass1);
        semail = findViewById(R.id.email);
        spass2 = findViewById(R.id.pass2);
        signup = findViewById(R.id.signup);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        //,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performAuth();

            }

        });

    }

    private void performAuth() {

        String user= suser.getText().toString();
        String id= sid.getText().toString();
        String pass1= spass1.getText().toString();
        String email= semail.getText().toString();
        String pass2= spass2.getText().toString();



        if (user.length()>0){
            if(id.length()<11 && id.length()>9){
                if(email.matches(userEmail)){

                    if(pass1.length()>8){
                        if(pass1.equals(pass2)){
                            progressDialog.setMessage("please wait while registration is completing...");
                            progressDialog.setTitle("Registration");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            mAuth.createUserWithEmailAndPassword(email,pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        sendusertologin();
                                        Toast.makeText(registrationpage.this, "registration successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(registrationpage.this, setprofile.class));
                                        finish();
                                    }
                                    else{

                                        Toast.makeText(registrationpage.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

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

    private void sendusertologin() {
        Intent intent = new Intent(registrationpage.this, loginpage.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}