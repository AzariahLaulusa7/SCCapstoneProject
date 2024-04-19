package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        back = findViewById(R.id.Back_home_chatbot);
        send = findViewById(R.id.send_chat);

        ArrayList<Message> messages = new ArrayList<Message>();
        recycler = findViewById(R.id.recyclerViews);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageListAdapter(messages, this);
        recycler.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home = new Intent(ChatbotUI.this, CrisisLines.class);
                startActivity(Home);
                finish();
            }
        });




        send.setOnClickListener(v -> {

            EditText editText = (EditText) findViewById(R.id.messageInput);
            String content = editText.getText().toString();

            Message prompt;
            prompt = new Message("user", content);
            Message response = new Message("bot", content);

            messages.add(prompt);
            messages.add(response);

        });



    }


}
