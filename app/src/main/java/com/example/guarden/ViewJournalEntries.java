package com.example.guarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class ViewJournalEntries extends AppCompatActivity {
    Button add;
    ImageButton back;
    static JournalAdapter JournalAdapter;

    public ArrayList<String> readFromInternalStorageLineByLine(Context context, String fileName) {
        ArrayList<String> data = new ArrayList<String>();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_journal_entries);
        ArrayList<JournalEntry> entries = new ArrayList<JournalEntry>();

        ArrayList<String> data = readFromInternalStorageLineByLine(getApplicationContext(), "journals.csv");

        int i=0; //For some reason there is a blank line in the CSV file, this is a patch

        for (String line : data) {
            if(i%2 ==0){
                JournalEntry entry = new JournalEntry();
                entry.setFromString(line);
                entries.add(entry);
            }
            i++;
        }

        add = (Button) findViewById(R.id.newEntry);
        back = (ImageButton) findViewById(R.id.journalBack);
        RecyclerView recycler = findViewById(R.id.journalRecycler);
        Intent addNewEntry = new Intent(this, NewJournalEntry.class);
        Intent goBack = new Intent(this, HomeScreen.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        JournalAdapter = new JournalAdapter(this, entries);
        recycler.setAdapter(JournalAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addNewEntry);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goBack);
            }
        });
    }
}