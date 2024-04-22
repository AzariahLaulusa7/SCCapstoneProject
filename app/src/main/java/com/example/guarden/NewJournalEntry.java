package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewJournalEntry extends AppCompatActivity {

    public Button back, done;
    private EditText journalName, journalContent;

    private DatabaseReference databaseReference;

    static String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_new_journal_entry);
        back = findViewById(R.id.BackToEntries);
        done = findViewById(R.id.nextPage);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        journalName = findViewById(R.id.journalEntryName);
        journalContent = findViewById(R.id.journalEntryContent);

        if(SaveUser.getUserName(NewJournalEntry.this).length() != 0)
            key = SaveUser.getUserName(NewJournalEntry.this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home = new Intent(NewJournalEntry.this, HomeScreen.class);
                startActivity(Home);
                finish();
            }
        });
        done.setOnClickListener(v -> {
            String name = journalName.getText().toString().trim();
            String content = journalContent.getText().toString().trim();
            JournalEntry entry = new JournalEntry(name, content);

            ;
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(NewJournalEntry.this, "Please add a title", Toast.LENGTH_SHORT).show();
                return;
            }

            if (key != null) {
                databaseReference.child("users").child(key).child("journalEntries").child(name).setValue(entry)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(NewJournalEntry.this, "Journal Created", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(NewJournalEntry.this, "Failed to create journal" + e.getMessage(), Toast.LENGTH_SHORT).show());

            } else {
                Toast.makeText(NewJournalEntry.this, "Failed to create journal", Toast.LENGTH_SHORT).show();
            }

            Intent back = new Intent(this, ViewJournalEntries.class);
            startActivity(back);
        });
    }
}