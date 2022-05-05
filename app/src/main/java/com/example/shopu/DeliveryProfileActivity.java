package com.example.shopu;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopu.model.DeliveryMan;
import com.example.shopu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

public class DeliveryProfileActivity extends AppCompatActivity {

    static final int CAMERA_REQUEST = 1;

    ActivityResultLauncher<String> mGetContentGallery;
    ActivityResultLauncher<Uri> mGetContentCamera;

    ActivityResultLauncher<String> getSinglePermissionGallery;
    ActivityResultLauncher<String> getSinglePermissionCamera;

    Bitmap imageBitmap;
    File file;


    private FirebaseAuth mAuth;
    Button logOut, DchangePic, DchangePicGal;
    EditText Dname, Dlastname,  Dphone;
    TextView score, Demail, profit;
    DatabaseReference ref;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ImageView btnHome, DprofilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_profile);

        Dname = findViewById(R.id.Dname);
        Dlastname = findViewById(R.id.Dlastname);
        Demail = findViewById(R.id.Demail);
        Dphone = findViewById(R.id.Dphone);
        logOut = findViewById(R.id.logOut);
        score = findViewById(R.id.score);
        profit = findViewById(R.id.profit);
        btnHome = findViewById(R.id.btn_home);
        DchangePic = findViewById(R.id.DchangePic);
        DchangePicGal = findViewById(R.id.DchangePicGal);
        DprofilePic = findViewById(R.id.DprofilePic);


        file = new File(getFilesDir(), "picFromCamera");

        requestCameraPermission();
        requestReadStoragePermission();
        getSinglePermissionCamera.launch(Manifest.permission.CAMERA);

        mGetContentGallery = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uriLocal) {
                        loadImage(uriLocal);
                    }
                });

        DchangePicGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContentGallery.launch("image/*");
            }
        });

        DchangePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeliveryProfileActivity.this,DeliveryHomeActivity.class));
            }
        });


        ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){

                        DeliveryMan usuario = task.getResult().getValue(DeliveryMan.class);
                        Dname.setText(usuario.getName());
                        Dlastname.setText(usuario.getLastName());
                        Demail.setText(usuario.getEmail());
                        Dphone.setText(usuario.getPhone());
                        score.setText(usuario.getScore().toString());
                        profit.setText(usuario.getProfit().toString());


                    }
                }
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                startActivity(new Intent(DeliveryProfileActivity.this,MainActivity.class));

            }
        });
        getSinglePermissionGallery.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public void requestCameraPermission() {
        getSinglePermissionCamera = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result == true) {

                        } else {
                        }
                    }
                });


    }


    public void requestReadStoragePermission() {
        getSinglePermissionGallery = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result == true) {

                        } else {
                        }
                    }
                });



    }

    public void loadImage(Uri uri) {
        DprofilePic.setImageURI(uri);
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        } catch (ActivityNotFoundException e) {
            Log.e("PERMISSION_APP", e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("hereeeee");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            DprofilePic.setImageBitmap(imageBitmap);
        }
    }
}
