package com.example.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

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

    String image, name;
    String userName, Image;
    String field1, field2;



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

        storageReference = FirebaseStorage.getInstance().getReference();
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


                //String captio = mcaptionButton.getText().toString();

                DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(currentUserId);
                // Query the Firestore document to get the data you want to copy
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Get the data fields you want to copy
                            field1 = documentSnapshot.getString("name");

                            field2 = documentSnapshot.getString("image");

                            // ...

                            // Create a map with the data fields you want to copy
                            Map<String, Object> data = new HashMap<>();
                            data.put("name", field1);
                            data.put("image", field2);
                            String time = String.valueOf(System.currentTimeMillis());

                            // ...
                            // Get a reference to the Realtime Database location where you want to paste the data
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("PostUser");
                            // Paste the data to the Realtime Database location
                            dbRef.setValue(data);
                        }
                    }
                });


                mprogressbar.setVisibility(View.VISIBLE);
                String caption = mcaptionButton.getText().toString();
                if (!caption.isEmpty()) {



                    String time = String.valueOf(System.currentTimeMillis());
                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    userName=field1;
                    Image=field2;
                    database = FirebaseDatabase.getInstance();
                    DatabaseReference root = database.getReference("Post");
                    adapt1 Post = new adapt1(currentuser, caption, userName, Image, time);
                    //String key = root.push().getKey();
                    root.child(time).setValue(Post);



                    mprogressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(addpostActivity.this, "Post Added Successfully !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(addpostActivity.this, MainActivity.class));
                    finish();


                } else {
                    mprogressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(addpostActivity.this, "Please Write Post!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}