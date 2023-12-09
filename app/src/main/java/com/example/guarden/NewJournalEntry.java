package com.example.guarden;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewJournalEntry extends AppCompatActivity {

    public Button back, done;
    private EditText journalName, journalContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_new_journal_entry);

        back = findViewById(R.id.BackToEntries);
        done = findViewById(R.id.nextPage);

        journalName = findViewById(R.id.journalEntryName);
        journalContent = findViewById(R.id.journalEntryContent);

        back.setOnClickListener(v -> finish());
        done.setOnClickListener(v -> {
            JournalEntry entry = new JournalEntry();
            final String name = journalName.getText().toString().trim();
            final String content = journalContent.getText().toString().trim();

            if(name.length() != 0){
                entry.setEntryName(name);
            }

            if(content.length() != 0){
                entry.setEntryContent(content);
            }

            //Store Entry Somewhere

            finish();
        });
    }
}