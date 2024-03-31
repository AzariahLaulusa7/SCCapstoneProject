package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatbotUI extends AppCompatActivity{


    Button send;
    Button back;
    RecyclerView recycler;
    MessageListAdapter adapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbot_ui);

        back = findViewById(R.id.Back);
        send = findViewById(R.id.send_chat);
        Intent goBack = new Intent(ChatbotUI.this, CrisisLines.class);

        back.setOnClickListener(v -> {
            startActivity(goBack);
        });

        ArrayList<Message> messages = new ArrayList<Message>();


        send.setOnClickListener(v -> {

            EditText editText = (EditText) findViewById(R.id.messageInput);
            String content = editText.getText().toString();

            Message prompt;
            prompt = new Message("user", content);
            Message response = new Message("bot", content);

            messages.add(prompt);
            messages.add(response);

        });

        recycler = findViewById(R.id.recyclerViews);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageListAdapter(messages, this);
        recycler.setAdapter(adapter);

    }


}
