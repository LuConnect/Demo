package com.example.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class addpostActivity extends AppCompatActivity {

    private Button mpostButton;
    private EditText mcaptionButton;
    private ImageView mpostImage;
    private ProgressBar mprogressbar;
    private Uri postImageUri = null;
    private StorageReference storageReference;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private String currentUserId;
    private Toolbar postToolbar;
    private FirebaseDatabase database;

    String image,name;
    String userName, Image;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        mpostButton = findViewById(R.id.post_button);
        mcaptionButton = findViewById(R.id.cratepost);
        //mpostImage = findViewById(R.id.createimg);

        mprogressbar = findViewById(R.id.progressBar3);
        mprogressbar.setVisibility(View.INVISIBLE);

        postToolbar = findViewById(R.id.addposttoolbar);
        setSupportActionBar(postToolbar);
        getSupportActionBar().setTitle("Add Post");

//        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();

        //FirebaseUser firebaseUser = auth.getCurrentUser();

        //.....................................................................
        //.....................................................................
        String post_id = getIntent().getStringExtra("post_id");



        mpostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mprogressbar.setVisibility(View.VISIBLE);
                String caption = mcaptionButton.getText().toString();
                if (!caption.isEmpty()) {

                    firestore = FirebaseFirestore.getInstance();
                    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DocumentReference documentReference = firestore.collection("Users").document(currentUser);
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override

                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){
                                name = documentSnapshot.getString("name");
                                image = documentSnapshot.getString("image");


                                String time = String.valueOf(System.currentTimeMillis());
                                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                database = FirebaseDatabase.getInstance();
                                DatabaseReference root = database.getReference("Post");
                                adapt1 Post = new adapt1(currentuser, caption, name, image, time);
                                //String key = root.push().getKey();
                                root.child(time).setValue(Post);

                            }
                        }
                    });

                    mprogressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(addpostActivity.this, "Post Added Successfully !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(addpostActivity.this, MainActivity.class));
                    finish();


                } else {
                    mprogressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(addpostActivity.this, "Please Write Your caption", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}