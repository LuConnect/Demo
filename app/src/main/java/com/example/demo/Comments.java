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
                    String time = String.valueOf(System.currentTimeMillis());
                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    database = FirebaseDatabase.getInstance();
                    DatabaseReference root =  database.getReference("Post").child(post_id).child("Comment");
                    adapt2 Comment = new adapt2(currentuser, comment);
                    //String key = root.push().getKey();
                    root.child(time).setValue(Comment);

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

    @Override
    protected void onStart() {
        super.onStart();
        String time = String.valueOf(System.currentTimeMillis());
        FirebaseRecyclerOptions<Mcomment> options =
                new FirebaseRecyclerOptions.Builder<Mcomment>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Post").child(time).child("Comment"), Mcomment.class)
                        .build();


        FirebaseRecyclerAdapter<Mcomment, CommentAdaptor> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Mcomment, CommentAdaptor>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CommentAdaptor holder, int position, @NonNull Mcomment model) {

                firestore = FirebaseFirestore.getInstance();
                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DocumentReference documentReference = firestore.collection("Users").document(currentUser);

                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String userName = documentSnapshot.getString("name");
                            holder.commentusername.setText(userName);
                            Glide.with(holder.commentuserpic.getContext()).load(documentSnapshot.getString("image")).into(holder.commentuserpic);


                            holder.comments.setText(model.getComments());
                            System.out.println("caption: "+ model.getComments());

                        }

                    }

                });


            }

            @NonNull
            @Override
            public CommentAdaptor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.everycomment, parent, false);
                return new CommentAdaptor(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        comment_recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}