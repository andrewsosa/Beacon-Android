package com.andrewsosa.beacon;

/**
 * Created by andrewsosa on 12/6/15.
 */
public class MessageModel {

    String message;
    String uid;

    public MessageModel() {}

    public MessageModel(String message, String uid) {
        this.message = message;
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public String getUid() {
        return uid;
    }
}
