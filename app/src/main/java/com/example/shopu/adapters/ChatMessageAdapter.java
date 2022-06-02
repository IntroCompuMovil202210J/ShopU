package com.example.shopu.adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopu.R;
import com.example.shopu.clientFragments.EstablishmentFragment;
import com.example.shopu.model.Establishment;
import com.example.shopu.model.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<Message> {


    List<Message> messages;

    public ChatMessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);

        this.messages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_chat_message, parent, false);
        }

        TextView txtName = (TextView) convertView.findViewById(R.id.chatUserName);
        TextView txtMessage = convertView.findViewById(R.id.chatMessage);
        TextView txtTime = convertView.findViewById(R.id.txtTime);

        Message message = this.messages.get(position);

        txtName.setText(message.getSenderName());
        txtMessage.setText(message.getMessage());
        txtTime.setText(message.getTime());




        return convertView;
    }

}
