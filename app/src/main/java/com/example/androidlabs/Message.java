package com.example.androidlabs;

enum MessageType { SENT, RECEIVED }

public class Message {
    private long id;
    private String message;
    private MessageType type;


    public Message(long id, String message, MessageType type) {
        this.id = id;
        this.message = message;
        this.type = type;
    }

    public long getId() { return this.id; }

    public String getMessage() {
        return this.message;
    }

    public MessageType getType() {
        return this.type;
    }
}
