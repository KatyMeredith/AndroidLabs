package com.example.androidlabs;

enum MessageType { SENT, RECEIVED }

public class Message {
    private String message;
    private MessageType type;


    public Message(String message, MessageType type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public MessageType getType() {
        return this.type;
    }
}
