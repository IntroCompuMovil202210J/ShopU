package com.example.shopu;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopu.adapters.ChatMessageAdapter;
import com.example.shopu.adapters.OrdersAdapter;
import com.example.shopu.model.Message;
import com.example.shopu.model.Order;
import com.example.shopu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView chatDisplay;
    Button sendMessage;
    EditText txtMessage;
    ChatMessageAdapter adapter;
    ArrayList<Message> messages;
    Uri profileImage;

    FirebaseUser user;
    User  myUser;

    DatabaseReference ref;


    FirebaseAuth mAuth;

    View root;

    private String mParam1;
    private String mParam2;

    public chatFragment() {
        // Required empty public constructor
    }

    public static chatFragment newInstance(String param1, String param2) {
        chatFragment fragment = new chatFragment();
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

        root = inflater.inflate(R.layout.fragment_chat, container, false);

        mAuth = FirebaseAuth.getInstance();

        chatDisplay = root.findViewById(R.id.chatDisplay);
        sendMessage = root.findViewById(R.id.sendMensaje);
        txtMessage = root.findViewById(R.id.newMessageTxt);
        messages = new ArrayList<>();

        findMyUser();
        loadMessages();

        adapter = new ChatMessageAdapter(getContext(), messages);
        chatDisplay.setAdapter(adapter);

        sendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendMessage();
            }

        });

        return root;
    }

    public void findMyUser(){

        user = mAuth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){

                    if(task.getResult().exists()){
                        myUser = task.getResult().getValue(User.class);
                    }

                }
            }
        });



    }

    public void sendMessage(){

        Message a = new Message();

        a.setSenderId(mAuth.getCurrentUser().getUid());
        a.setMessage(txtMessage.getText().toString());
        a.setSenderName(myUser.getName());

        String time = LocalTime.now().toString();
        a.setTime(time.substring(0,time.length()-4));
        FirebaseDatabase.getInstance().getReference("chats").push().setValue(a);

    }

    public void loadMessages() {

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("chats").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    messages.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Message order = ds.getValue(Message.class);
                        messages.add(order);
                    }
                    adapter = new ChatMessageAdapter(getContext(),messages);
                    chatDisplay.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

    }
}