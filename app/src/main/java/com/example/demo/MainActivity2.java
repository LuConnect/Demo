package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {

    EditText suser,sid,spass1,semail,spass2;
    TextView signup;
    String emailReg = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

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

        if(user.length()<0)
        {
            suser.setError("invalid username");
        }
        else if(id.length()>11 && id.length()<9){
            sid.setError("invalid student id");
        }
        else if(!email.matches(emailReg))
        {
            semail.setError("Enter correct email");
        }else if(pass1.isEmpty() || pass1.length()<8)
        {
            spass1.setError("Enter minimum 8 length of password");
        }
        else if(!pass1.equals(pass2)){
            spass2.setError("password not matched");
        }
        else{
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
                        Toast.makeText(MainActivity2.this, "registration successful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity2.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }



    }

    private void sendusertologin() {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}