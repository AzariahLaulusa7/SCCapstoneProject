package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editJournalEntry extends AppCompatActivity {

    public Button delete, done;
    private EditText journalName, journalContent;

    private DatabaseReference databaseReference;

    static String key, currentTitle, currentContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_journal_entry);
        delete = findViewById(R.id.delete);
        done = findViewById(R.id.doneEdit);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        currentTitle = getIntent().getStringExtra("TITLE");
        currentContent = getIntent().getStringExtra("CONTENT");

        journalName = findViewById(R.id.editJournalName);
        journalContent = findViewById(R.id.editJournalContent);

        journalName.setText(currentTitle);
        journalContent.setText(currentContent);

        Intent back = new Intent(editJournalEntry.this, ViewJournalEntries.class);

        if(SaveUser.getUserName(editJournalEntry.this).length() != 0)
            key = SaveUser.getUserName(editJournalEntry.this);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String entryKeyToRemove = currentTitle;

                // Get a reference to the entry you want to remove
                DatabaseReference entryRefToRemove = databaseReference.child("users").child(key).child("journalEntries").child(entryKeyToRemove);

                // Remove the entry from the database
                entryRefToRemove.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Entry successfully removed
                        // You can also update your local list and RecyclerView here if needed
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors that occurred while removing the entry
                        Log.e("RemoveEntry", "Error removing entry: " + e.getMessage());
                    }
                });
                //back to entry list
                startActivity(back);
                finish();
            }
        });
        done.setOnClickListener(v -> {
            String name = journalName.getText().toString().trim();
            String content = journalContent.getText().toString().trim();
            JournalEntry entry = new JournalEntry(name, content);

            ;
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(editJournalEntry.this, "Please add a title", Toast.LENGTH_SHORT).show();
                return;
            }

            if (key != null) {
                databaseReference.child("users").child(key).child("journalEntries").child(currentTitle).setValue(entry)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(editJournalEntry.this, "Journal Created", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(editJournalEntry.this, "Failed to create journal" + e.getMessage(), Toast.LENGTH_SHORT).show());

            } else {
                Toast.makeText(editJournalEntry.this, "Failed to create journal", Toast.LENGTH_SHORT).show();
            }

            startActivity(back);
        });
    }
}
