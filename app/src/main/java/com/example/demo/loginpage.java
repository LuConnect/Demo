package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class loginpage extends AppCompatActivity {

    EditText edemail,edpass;
    TextView signin,signup;

    String emailReg = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edemail = findViewById(R.id.edemail);
        edpass = findViewById(R.id.edpass);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginpage.this, registrationpage.class));

            }
        });

    }

    private void performLogin() {

        String email = edemail.getText().toString();
        String pass = edpass.getText().toString();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()){

            if(!email.matches(emailReg))
            {
                edemail.setError("Enter correct email");
            }else if(pass.isEmpty() || pass.length()<8)
            {
                edpass.setError("Enter proper password");
            }
            else{
                progressDialog.setMessage("please wait while login is completing...");
                progressDialog.setTitle("login");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            sendusertologin();
                            Toast.makeText(loginpage.this, "Login successful", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(loginpage.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
        else {
            new AlertDialog.Builder(loginpage.this)
                    .setTitle("No Internet")
                    .setMessage("Connect to the Network.")
                    .show();

        }

    }
    private void sendusertologin() {
        Intent intent = new Intent(loginpage.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}