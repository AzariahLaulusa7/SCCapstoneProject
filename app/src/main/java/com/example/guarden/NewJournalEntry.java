package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewJournalEntry extends AppCompatActivity {

    public Button back, done;
    private EditText journalName, journalContent;

    public void appendToInternalStorage(Context context, String fileName, String data) {
        try {
            // Check if the file exists, if not, create it
            File file = new File(context.getFilesDir(), fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(data.getBytes());
            fos.write("\n".getBytes()); // Add a newline character after each entry if desired
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeToInternalStorage(Context context, String fileName, String data) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean doesFileExist(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        return file.exists();
    }
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

            //Write the data to internal storage
            if((doesFileExist(getApplicationContext(), "journals.csv") == true)) {
                appendToInternalStorage(getApplicationContext(), "journals.csv", entry.getString());
            }
            else{
                writeToInternalStorage(getApplicationContext(), "journals.csv", entry.getString());
            }

            Intent home = new Intent(this, HomeScreen.class);
            startActivity(home);
        });
    }
}