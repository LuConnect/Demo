package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class addpostActivity extends AppCompatActivity {

    private Button mpostButton;
    private EditText mcaptionButton;
    private ProgressBar mprogressbar;
    private StorageReference storageReference;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        mpostButton = findViewById(R.id.post_button);
        mcaptionButton = findViewById(R.id.cratepost);

        mprogressbar = findViewById(R.id.progressBar3);
        mprogressbar.setVisibility(View.INVISIBLE);

        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();

        mpostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mprogressbar.setVisibility(View.VISIBLE);
//                String caption = mcaptionButton.getText().toString();
                //String timestamp = FieldValue.serverTimestamp().toString();
                HashMap<String , Object> postMap = new HashMap<>();

                postMap.put("user" , currentUserId);
                postMap.put("caption" , mcaptionButton);
                postMap.put("time" , FieldValue.serverTimestamp());

                firestore.collection("Post").document(currentUserId).set(new postAdaptor(mcaptionButton.getText().toString(), FieldValue.serverTimestamp().toString()));
                Toast.makeText(addpostActivity.this, "Post Created", Toast.LENGTH_SHORT).show();



           }
        });


    }
}