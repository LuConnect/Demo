package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demo.adaptor.CommentAdaptor;
import com.example.demo.model.Mcomment;
import com.example.demo.model.post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Comments extends AppCompatActivity {

    private EditText commentEdit;
    private Button add_comment;
    private RecyclerView comment_recyclerView;
    private FirebaseFirestore firestore;
    private String post_id;
    private FirebaseDatabase database;
    CommentAdaptor adaptor;

    String name, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        commentEdit = findViewById(R.id.comment_edit);
        add_comment = findViewById(R.id.add_comment);
        comment_recyclerView = findViewById(R.id.comment_recyclerView);
        firestore = FirebaseFirestore.getInstance();

        post_id = getIntent().getStringExtra("post_id");


        //comment_recyclerView.setHasFixedSize(true);
        comment_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEdit.getText().toString();
                if (!comment.isEmpty()){



                    firestore = FirebaseFirestore.getInstance();
                    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DocumentReference documentReference = firestore.collection("Users").document(currentUser);
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override

                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){
                                name = documentSnapshot.getString("name");
                                image = documentSnapshot.getString("image");


                                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                database = FirebaseDatabase.getInstance();
                                DatabaseReference root =  database.getReference("Post").child(post_id).child("Comment");
                                adapt2 Comment = new adapt2(currentuser, comment, name, image);
                                String key = root.push().getKey();
                                root.child(key).setValue(Comment);

                            }
                        }
                    });
                    //String time = String.valueOf(System.currentTimeMillis());


                    Toast.makeText(Comments.this, "Comment Added Successfully !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Comments.this , MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(Comments.this,"Please add Comment",Toast.LENGTH_SHORT).show();
                }

            }
        });

        String time = String.valueOf(System.currentTimeMillis());
        FirebaseRecyclerOptions<Mcomment> options =
                new FirebaseRecyclerOptions.Builder<Mcomment>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Post").child(post_id).child("Comment"), Mcomment.class)
                        .build();

        adaptor= new CommentAdaptor(options);
        comment_recyclerView.setAdapter(adaptor);

    }

    @Override
    protected void onStart() {
        super.onStart();

        adaptor.startListening();
    }
}