package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.demo.adaptor.PostAdaptor;
import com.example.demo.model.post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class postview extends AppCompatActivity {

    ImageView exit,profile;

    private FloatingActionButton fab;
    private FirebaseAuth firebaseAuth;
    //private StorageReference storageReference;
    private FirebaseFirestore firestore;
    private RecyclerView mRecyclerView;
    private PostAdaptor adapter;
    private List<post> list;
    private Query query;
    private ListenerRegistration listenerRegistration;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        mRecyclerView = findViewById(R.id.recyclerview1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(postview.this));

        list= new ArrayList<>();
        adapter = new PostAdaptor(postview.this, list);


        exit = findViewById(R.id.exit);
        profile = findViewById(R.id.Bprofile);
        fab = findViewById(R.id.floatingActionButton4);

        mRecyclerView.setAdapter(adapter);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(postview.this, addpostActivity.class));
            }
        });



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(postview.this)
                        .setTitle("Confirm Exit")
                        .setMessage("Are you sure you want to log out?")

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                  finishAndRemoveTask();
                                    startActivity(new Intent(postview.this, loginpage.class));
                                }
                            }
                        })

                        .show();
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(postview.this, setprofile.class));
            }
        });


    }


}