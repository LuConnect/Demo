package com.example.demo;


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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity4 extends AppCompatActivity {

    private CircleImageView circleImageView;
    private EditText profilename;
    private Button Bsave;
    private FirebaseAuth auth;
    private Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        circleImageView = findViewById(R.id.circleimage);
        profilename = findViewById(R.id.profilename);
        Bsave = findViewById(R.id.button);

        auth = FirebaseAuth.getInstance();

        Bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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