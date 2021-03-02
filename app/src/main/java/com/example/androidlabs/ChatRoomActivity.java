package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<Message> messages;
    private BaseAdapter listAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        MessageDatabase.initialize(this);
        updateMessages();

        this.listView = findViewById(R.id.listView);


        initializeListAdapter();

        findViewById(R.id.sendBtn).setOnClickListener( v -> {
            MessageDatabase.addMessage(popMessageText(), MessageType.SENT);
            updateMessages();
            listAdapter.notifyDataSetChanged();
        });

        findViewById(R.id.receiveBtn).setOnClickListener( v -> {
            MessageDatabase.addMessage(popMessageText(), MessageType.RECEIVED);
            updateMessages();
            listAdapter.notifyDataSetChanged();
        });

        this.listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(ChatRoomActivity.this)
                    .setTitle(R.string.delete_confirm)
                    .setMessage("The selected row is: " + position + "\nThe selected database id is: " + messages.get(position).getId())
                    .setPositiveButton(R.string.yes, (click, arg) -> {
                        MessageDatabase.deleteMessage(messages.get(position).getId());
                        updateMessages();
                        listAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.no, (click, arg) -> {

                    })
                    .create()
                    .show();
            return true;
        });
    }

    private void updateMessages() {
        this.messages = MessageDatabase.getAllMessages();
    }

    private String popMessageText() {
        EditText messageTxt = findViewById(R.id.messageTxt);

        String message = messageTxt.getText().toString();

        messageTxt.setText("");

        return message;
    }

    private void initializeListAdapter() {
        this.listAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return ChatRoomActivity.this.messages.size();
            }

            @Override
            public Object getItem(int position) {
                return ChatRoomActivity.this.messages.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View old, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                Message message = ChatRoomActivity.this.messages.get(position);

                View row;

                row = message.getType() == MessageType.SENT
                        ? inflater.inflate(R.layout.chat_sent_row, parent, false)
                        : inflater.inflate(R.layout.chat_received_row, parent, false);

                TextView messageTxt = row.findViewById(R.id.rowMessageTxt);
                messageTxt.setText(message.getMessage());

                return row;
            }
        };

        this.listView.setAdapter(this.listAdapter);
    }
}
