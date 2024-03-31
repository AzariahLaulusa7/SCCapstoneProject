package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
public class ChatbotUI extends AppCompatActivity{
    Button send = findViewById(R.id.send_chat);
    Button back;
    RecyclerView recycler;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbot_ui);

        back = findViewById(R.id.Back);

        Intent goBack = new Intent(ChatbotUI.this, CrisisLines.class);

        back.setOnClickListener(v -> {
            startActivity(goBack);
        });

        send.setOnClickListener(v -> {
            //code away
        });

    }


}
