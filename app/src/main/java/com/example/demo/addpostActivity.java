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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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


//        mpostImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setAspectRatio(3,2)
//                        .setMinCropResultSize(512,512)
//                        .start(addpostActivity.this);
//            }
//        });


        mpostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String captio = mcaptionButton.getText().toString();

                mprogressbar.setVisibility(View.VISIBLE);
                String caption = mcaptionButton.getText().toString();
                if (!caption.isEmpty() ){

//                                        HashMap<String , Object> postMap = new HashMap<>();
//                                        postMap.put("image" , uri.toString());
//                                        postMap.put("user" , currentUserId);
//                                        postMap.put("caption" , caption);
//                                        postMap.put("time" , FieldValue.serverTimestamp());

                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    database = FirebaseDatabase.getInstance();
                    DatabaseReference root =  database.getReference("Post");
                    adapt1 Post = new adapt1(currentuser, caption);
                    String key = root.push().getKey();
                    root.child(key).setValue(Post);
                    mprogressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(addpostActivity.this, "Post Added Successfully !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(addpostActivity.this , MainActivity.class));
                    finish();


//                                        firestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<DocumentReference> task) {
//                                                if (task.isSuccessful()){
//                                                    mprogressbar.setVisibility(View.INVISIBLE);
//                                                    Toast.makeText(addpostActivity.this, "Post Added Successfully !!", Toast.LENGTH_SHORT).show();
//                                                    startActivity(new Intent(addpostActivity.this , MainActivity.class));
//                                                    finish();
//                                                }else{
//                                                    mprogressbar.setVisibility(View.INVISIBLE);
//                                                    Toast.makeText(addpostActivity.this, task.getException().toString() , Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });

                }else{
                    mprogressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(addpostActivity.this, "Please Add Image and Write Your caption", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK){
//
//                postImageUri = result.getUri();
//                mpostImage.setImageURI(postImageUri);
//            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
//                Toast.makeText(this, result.getError().toString(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}