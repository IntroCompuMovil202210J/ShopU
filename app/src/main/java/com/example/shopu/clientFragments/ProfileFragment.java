package com.example.shopu.clientFragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
import com.example.shopu.model.User;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    ActivityResultLauncher<String> mGetContentGallery;
    ActivityResultLauncher<String> getSinglePermissionGallery;

    StorageReference storageRef;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    Uri img;

    Button changePic,signOut,save;
    TextView userEmail;
    EditText userPhone,userName,userLastname;
    ImageView imagePic;

    View root;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        changePic = root.findViewById(R.id.changePicGal);
        signOut = root.findViewById(R.id.logOut);
        save = root.findViewById(R.id.saveButtonClient);

        userEmail = root.findViewById(R.id.email);
        userName = root.findViewById(R.id.name);
        userPhone = root.findViewById(R.id.phone);
        userLastname = root.findViewById(R.id.lastname);
        imagePic = root.findViewById(R.id.profilePic);

        requestReadStoragePermission();

        loadData();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                startActivity(new Intent(getContext(), MainActivity.class));

            }
        });

        changePic.setOnClickListener(new View.OnClickListener() {
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        return root;
    }

    public void loadImage(Uri uri) {
        imagePic.setImageURI(uri);
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

    public void saveData(){

        ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("name");
        ref.setValue(userName.getText().toString());
        ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("lastname");
        ref.setValue(userLastname.getText().toString());
        ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("phone");
        ref.setValue(userPhone.getText().toString());

        storageRef = FirebaseStorage.getInstance().getReference(user.getUid()+"/");
        storageRef.putFile(img);
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

                        User usuario = task.getResult().getValue(User.class);

                        userName.setText(usuario.getName());
                        userLastname.setText(usuario.getLastName());
                        userEmail.setText(usuario.getEmail());
                        userPhone.setText(usuario.getPhone());

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

                            img = (Uri.fromFile(localFile));
                            imagePic.setImageURI(img);
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
}