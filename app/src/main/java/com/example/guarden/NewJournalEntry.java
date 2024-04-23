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
        //Referencing XML
        setContentView(R.layout.content_new_journal_entry);
        //Identifying XML elements
        back = findViewById(R.id.BackToEntries);
        done = findViewById(R.id.nextPage);
        journalName = findViewById(R.id.journalEntryName);
        journalContent = findViewById(R.id.journalEntryContent);

        //Referencing database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Checking if the user is logged in
        if(SaveUser.getUserName(NewJournalEntry.this).length() != 0)
            key = SaveUser.getUserName(NewJournalEntry.this);

        //back to ViewJournalEntries
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home = new Intent(NewJournalEntry.this, ViewJournalEntries.class);
                startActivity(Home);
                finish();
            }
        });

        //Adds new journal to database
        done.setOnClickListener(v -> {
            String name = journalName.getText().toString().trim();
            String content = journalContent.getText().toString().trim();
            JournalEntry entry = new JournalEntry(name, content);

            //User cannot upload journal with no title but they CAN upload a journal with no content
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(NewJournalEntry.this, "Please add a title", Toast.LENGTH_SHORT).show();
                return;
            }

            //Name of the journal is used as the entry key
            if (key != null) {
                databaseReference.child("users").child(key).child("journalEntries").child(name).setValue(entry)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(NewJournalEntry.this, "Journal Created", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(NewJournalEntry.this, "Failed to create journal" + e.getMessage(), Toast.LENGTH_SHORT).show());

            } else {
                Toast.makeText(NewJournalEntry.this, "Failed to create journal", Toast.LENGTH_SHORT).show();
            }
            //Returns to view page on completion of journal
            Intent back = new Intent(this, ViewJournalEntries.class);
            startActivity(back);
        });
    }
}