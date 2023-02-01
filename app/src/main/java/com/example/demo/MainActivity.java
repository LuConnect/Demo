package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edname,edpass;
    TextView signin,signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edname = findViewById(R.id.edname);
        edpass = findViewById(R.id.edpass);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();





        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edname.getText().toString();
                String pass = edpass.getText().toString();

                if (networkInfo!=null && networkInfo.isConnected()){

                    if (name.length()>0 ){

                        if (pass.length()>0){
                            Toast.makeText(getApplicationContext(),"LOGGED IN SUCCESSFULLY!",Toast.LENGTH_SHORT)
                                    .show();

                            startActivity(new Intent(MainActivity.this,MainActivity3.class));
                        }
                        else {
                            edpass.setError("Password");

                        }


                    }
                    else {
                        edname.setError("Input your name");

                    }

                }
                else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("No Internet")
                            .setMessage("Connect to the Network.")
                            .show();
                }





            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));

            }
        });




    }
}