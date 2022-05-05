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
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopu.adapters.EstablishmentAdapter;
import com.example.shopu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;

public class UserProfileActivity extends AppCompatActivity {

    static final int CAMERA_REQUEST = 1;

    ActivityResultLauncher<String> mGetContentGallery;
    ActivityResultLauncher<Uri> mGetContentCamera;

    ActivityResultLauncher<String> getSinglePermissionGallery;
    ActivityResultLauncher<String> getSinglePermissionCamera;

    Bitmap imageBitmap;
    File file;

    private FirebaseAuth mAuth;
    Button logOut, changePic, changePicGal;
    EditText name, lastname, phone;
    TextView email;
    DatabaseReference ref;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private BottomNavigationView menu;
    ImageView profilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        logOut = findViewById(R.id.logOut);
        profilePic = findViewById(R.id.profilePic);
        changePic = findViewById(R.id.changePic);
        changePicGal = findViewById(R.id.changePicGal);


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

        changePicGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContentGallery.launch("image/*");
            }
        });

        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                takePicture();
            }
        });

        menu = findViewById(R.id.navigation);

        mAuth = FirebaseAuth.getInstance();

        ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        User usuario = task.getResult().getValue(User.class);

                        name.setText(usuario.getName());
                        lastname.setText(usuario.getLastName());
                        email.setText(usuario.getEmail());
                        phone.setText(usuario.getPhone());

                    }
                }
            }
        });

        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemClicked = item.getItemId();

                switch (itemClicked){


                    case R.id.home:
                        startActivity(new Intent(UserProfileActivity.this, HomeActivity.class));
                    case R.id.car:

                }
                return false;
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
                startActivity(new Intent(UserProfileActivity.this,MainActivity.class));

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
        profilePic.setImageURI(uri);
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
            profilePic.setImageBitmap(imageBitmap);
        }
    }


}