package com.example.demo;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.adaptor.PostAdaptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginpage extends AppCompatActivity {

    EditText edemail,edpass;
    TextView signin,signup;

    //String emailReg = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

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

        String adminEmail = "^(?=^[A-Za-z0-9._%+-]+@)(?=.*admin\\.com$).+";
        Pattern adminPattern = Pattern.compile(adminEmail);
        Matcher adminMatcher = adminPattern.matcher(email);

        String userEmail = "[a-z]{2,4}_[^0]{1}[0-9]{9}+@lus.ac.bd";
        Pattern userPattern = Pattern.compile(userEmail);
        Matcher userMatcher = userPattern.matcher(email);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(adminMatcher.matches()){
            if (networkInfo!=null && networkInfo.isConnected()){

                if(!email.matches(adminEmail))
                {
                    edemail.setError("Enter correct email");
                }else if(pass.isEmpty() || pass.length()<8)
                {
                    edpass.setError("Enter proper password");
                }
                else{
                    progressDialog.setMessage("please wait while login is completed...");
                    progressDialog.setTitle("login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                PostAdaptor.count = "1";
                                Intent intent = new Intent(loginpage.this, MainActivity.class);
                                startActivity(intent);
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
        else if(userMatcher.matches()){
            if (networkInfo!=null && networkInfo.isConnected()){

                if(!email.matches(userEmail))
                {
                    edemail.setError("Enter correct email");
                }else if(pass.isEmpty() || pass.length()<8)
                {
                    edpass.setError("Enter proper password");
                }
                else{
                    progressDialog.setMessage("please wait while login is completed...");
                    progressDialog.setTitle("login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                PostAdaptor.count = "2";
                                Intent intent = new Intent(loginpage.this, MainActivity.class);
                                startActivity(intent);

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

    }

}