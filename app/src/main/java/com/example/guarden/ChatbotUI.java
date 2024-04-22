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

import static com.example.guarden.Message.LayoutOne;
import static com.example.guarden.Message.LayoutTwo;
//Creates main chatbot view
public class ChatbotUI extends AppCompatActivity{

    Button send;
    Button back;
    RecyclerView recycler;
    MessageListAdapter messageListAdapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbot_ui);

        back = findViewById(R.id.Back_home_chatbot);
        send = findViewById(R.id.send_chat);

        ChatbotBrain chatbot = new ChatbotBrain();

        ArrayList<Message> messages = new ArrayList<Message>();

        Message welcomeMessage = new Message(LayoutTwo,"Hi there! My name is Rosie. I am a "+
                "mental health chatbot. I can help with basic therapist services. Remember, if this "+
                "is an emergency, please contact Emergency Services (dial 911) or the National suicide"+
                "prevention hotline (dial 988). Now, how are you doing today?");
        messages.add(welcomeMessage);
        //Uses recycler to display chatbot messages
        recycler = findViewById(R.id.recyclerViews);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        messageListAdapter = new MessageListAdapter(messages, this);
        recycler.setAdapter(messageListAdapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home = new Intent(ChatbotUI.this, CrisisLines.class);
                startActivity(Home);
                finish();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sends user input to chatbot and gets response
                EditText editText = findViewById(R.id.messageInput);
                String content = editText.getText().toString();
                String queryResult = chatbot.getResponse(content);

                //Creates a message for both the user and chatbot's statements
                Message prompt = new Message(LayoutOne, content);
                Message response = new Message(LayoutTwo, queryResult);

                //Displays both messages on the screen, then clears input field
                messages.add(prompt);
                messages.add(response);
                messageListAdapter.notifyDataSetChanged();
                editText.getText().clear();
            }
        });

    }
}
