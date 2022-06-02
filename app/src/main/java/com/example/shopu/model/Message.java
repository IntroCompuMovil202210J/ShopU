package com.example.shopu.model;

import android.net.Uri;

public class Message {

    String chat;
    String senderName;
    String message;
    String senderId;
    Uri senderUri;

    public Message(){}

    public Message(String senderName, String message, Uri senderUri,String senderId) {
        this.senderName = senderName;
        this.message = message;
        this.senderUri = senderUri;
        this.senderId = senderId;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Uri getSenderUri() {
        return senderUri;
    }

    public void setSenderUri(Uri senderUri) {
        this.senderUri = senderUri;
    }
}
