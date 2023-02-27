package com.example.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.adaptor.PostAdaptor;
import com.example.demo.model.post;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private Toolbar mainToolbar;
    private RecyclerView mRecyclerView;

    private FloatingActionButton fab;

    private PostAdaptor adapter;

    private Query query;
    private ListenerRegistration listenerRegistration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        mRecyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.floatingActionButton);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        list= new ArrayList<>();
//        adapter = new PostAdaptor(MainActivity.this, list);

        //mRecyclerView.setAdapter(adapter);




        mainToolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Lu Connect");



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, addpostActivity.class));
            }
        });



        FirebaseRecyclerOptions<post> options =
                new FirebaseRecyclerOptions.Builder<post>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Post"), post.class)
                        .build();



        adapter = new PostAdaptor(options);
        mRecyclerView.setAdapter(adapter);
        //adapter.startListening();

    }



    protected void onStart() {
        super.onStart();
        adapter.startListening();
        FirebaseUser currentUser =firebaseAuth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(MainActivity.this, loginpage.class));
            finish();
        }
        else{
            String currentUserId = firebaseAuth.getCurrentUser().getUid();
            firestore.collection("Users").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        if (!task.getResult().exists()){
                            startActivity(new Intent(MainActivity.this, setprofile.class));
                            finish();
                        }
                    }
                }
            });

        }


    }

    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processSearch(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });

        return true;

    }




    public void processSearch(String s) {

        FirebaseRecyclerOptions<post> options =
                new FirebaseRecyclerOptions.Builder<post>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Post").orderByChild("caption").startAt(s).endAt(s + "\uf8ff"), post.class)
                        .build();

        adapter = new PostAdaptor(options);
        adapter.startListening();
        mRecyclerView.setAdapter(adapter);

    }



        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profileMenu){
            startActivity(new Intent(MainActivity.this, setprofile.class));
        }
        else if (item.getItemId() == R.id.logoutMenu){
            new AlertDialog.Builder(MainActivity.this)
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
                                startActivity(new Intent(MainActivity.this, loginpage.class));
                            }
                        }
                    })

                    .show();
        }
        return  true;
    }


}