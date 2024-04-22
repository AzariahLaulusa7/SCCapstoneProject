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

    private DatabaseReference databaseReference;

    static String key;

    static ArrayList<JournalEntry> entries = new ArrayList<JournalEntry>();

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


        add = (Button) findViewById(R.id.newEntry);
        back = (ImageButton) findViewById(R.id.journalBack);
        RecyclerView recycler = findViewById(R.id.journalRecycler);
        Intent addNewEntry = new Intent(this, NewJournalEntry.class);
        Intent goBack = new Intent(this, HomeScreen.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        JournalAdapter = new JournalAdapter(this, entries);
        recycler.setAdapter(JournalAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        if(SaveUser.getUserName(ViewJournalEntries.this).length() != 0)
            key = SaveUser.getUserName(ViewJournalEntries.this);
        if(key == null) {
            key = " ";
        } else {
            databaseReference.child("users").child(key).child("journalEntries").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    entries.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String tempName = snapshot.child("name").getValue(String.class);
                        String tempContent = snapshot.child("content").getValue(String.class);

                        JournalEntry entry = new JournalEntry(tempName, tempContent);
                        entries.add(entry);
                    }
                    if (ViewJournalEntries.JournalAdapter != null) {
                        ViewJournalEntries.JournalAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("DatabaseError", error.getMessage());
                }
            });
        }


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