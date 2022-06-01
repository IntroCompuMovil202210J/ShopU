package com.example.shopu.deliveryFragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopu.MainActivity;
import com.example.shopu.R;
import com.example.shopu.model.DeliveryMan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class DeliveryProfileFragment extends Fragment {


    ActivityResultLauncher<String> mGetContentGallery;
    ActivityResultLauncher<String> getSinglePermissionGallery;
    ActivityResultLauncher<String> getSinglePermissionCamera;

    StorageReference storageRef;

    private FirebaseAuth mAuth;
    Button logOut, DchangePic, DchangePicGal;
    EditText Dname, Dlastname,  Dphone;
    TextView score, Demail, profit;
    DatabaseReference ref;
    FirebaseUser user;
    Button btnSave;
    ImageView DprofilePic;
    Uri img;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View root;

    public DeliveryProfileFragment() {
    }


    public static DeliveryProfileFragment newInstance(String param1, String param2) {
        DeliveryProfileFragment fragment = new DeliveryProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_delivery_profile, container, false);

        Dname = root.findViewById(R.id.Dname);
        Dlastname = root.findViewById(R.id.Dlastname);
        Demail = root.findViewById(R.id.Demail);
        Dphone = root.findViewById(R.id.Dphone);
        logOut = root.findViewById(R.id.logOut);
        score = root.findViewById(R.id.score);
        profit = root.findViewById(R.id.profit);
        btnSave = root.findViewById(R.id.btn_home);
        DchangePic = root.findViewById(R.id.DchangePic);
        DchangePicGal = root.findViewById(R.id.DchangePicGal);
        DprofilePic = root.findViewById(R.id.DprofilePic);


        requestCameraPermission();
        requestReadStoragePermission();

        loadData();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                startActivity(new Intent(getContext(), MainActivity.class));

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        DchangePicGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSinglePermissionGallery.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED){
                    mGetContentGallery.launch("image/*");
                }else{

                }

            }
        });

        mGetContentGallery = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uriLocal) {

                        loadImage(uriLocal);
                        img = uriLocal;
                    }
                });



        return root;
    }

    public void loadImage(Uri uri) {
        DprofilePic.setImageURI(uri);
    }

    public void loadData(){

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        storageRef = FirebaseStorage.getInstance().getReference("/");

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

        try {
            File localFile = File.createTempFile("images", "jpeg");
            StorageReference imageRef = storageRef.child(user.getUid());
            System.out.println(user.getUid());

            imageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            DprofilePic.setImageURI(Uri.fromFile(localFile));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData(){

       ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("name");
       ref.setValue(Dname.getText().toString());
       ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("lastname");
       ref.setValue(Dlastname.getText().toString());
       ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("phone");
       ref.setValue(Dphone.getText().toString());

        storageRef = FirebaseStorage.getInstance().getReference(user.getUid()+"/");
        storageRef.putFile(img);
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


}