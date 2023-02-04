package com.example.demo;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity4 extends AppCompatActivity {

    private CircleImageView circleImageView;
    private EditText profilename;
    private Button Bsave;
    private FirebaseAuth auth;
    private Uri imageuri;
    private StorageReference storageReference;
    private FirebaseFirestore firestore;
    private String Uid;
    private ProgressBar progressBar;
    private Uri downloadUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();
        Uid = auth.getCurrentUser().getUid();

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);


        circleImageView = findViewById(R.id.circleimage);
        profilename = findViewById(R.id.profilename);
        Bsave = findViewById(R.id.button);

        auth = FirebaseAuth.getInstance();




       firestore.collection("Users").document(Uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if(task.isSuccessful()){
                   if (task.getResult().exists()){
                       String name = task.getResult().getString("name");
                       String imageUrl = task.getResult().getString("image");
                       profilename.setText(name);
                       Glide.with(MainActivity4.this).load(imageUrl).into(circleImageView);

                   }

               }
           }
       });



        Bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String name = profilename.getText().toString();

                if (!name.isEmpty() && imageuri != null){
                    StorageReference imageRef = storageReference.child("Profile").child(Uid + ".jpg");

                    imageRef.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.INVISIBLE);
                                saveToFireStore(task, name, imageRef);
                            }
                            else{
                                Toast.makeText(MainActivity4.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity4.this, "please select picture and write your name", Toast.LENGTH_SHORT).show();
                }


            }
        });



        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    int result = ContextCompat.checkSelfPermission( MainActivity4.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

                    if (result != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity4.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                    else{
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1,1)
                                .start(MainActivity4.this);
                    }
                }
            }
        });

    }

    private void saveToFireStore(Task<UploadTask.TaskSnapshot> task, String name, StorageReference imageRef) {
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadUri = uri;
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("image", downloadUri.toString());

                firestore.collection("Users").document(Uid).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity4.this, "profile settings saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity4.this, MainActivity3.class));
                            finish();
                        }else{
                            Toast.makeText(MainActivity4.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                imageuri = result.getUri();
                circleImageView.setImageURI(imageuri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE ){
                Toast.makeText(this, result.getError().getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }
}