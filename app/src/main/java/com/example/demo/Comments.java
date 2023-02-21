package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Comments extends AppCompatActivity {

    private EditText commentEdit;
    private Button add_comment;
    private RecyclerView comment_recyclerView;
    private FirebaseFirestore firestore;
    private String post_id;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        commentEdit = findViewById(R.id.comment_edit);
        add_comment = findViewById(R.id.add_comment);
        comment_recyclerView = findViewById(R.id.comment_recyclerView);
        firestore = FirebaseFirestore.getInstance();

        post_id = getIntent().getStringExtra("post_id");


        comment_recyclerView.setHasFixedSize(true);
        comment_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEdit.getText().toString();
                if (!comment.isEmpty()){
                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    database = FirebaseDatabase.getInstance();
                    DatabaseReference root =  database.getReference().child("Post").child(post_id).child("Comment");
                    adapt2 Comment = new adapt2(currentuser, comment);
                    String key = root.push().getKey();
                    root.child(key).setValue(Comment);

                    Toast.makeText(Comments.this, "Comment Added Successfully !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Comments.this , MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(Comments.this,"Please add Comment",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}