package com.example.guarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.widget.ImageButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ViewJournalEntries extends AppCompatActivity implements RecyclerViewInterface{

    ImageButton add, back;
    static JournalAdapter JournalAdapter;

    private DatabaseReference databaseReference;

    static String key;

    static ArrayList<JournalEntry> entries = new ArrayList<JournalEntry>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //reference XML
        setContentView(R.layout.content_view_journal_entries);

        //Init buttons, intents, recycler, adapter, layout manager, and reference database
        add = (ImageButton) findViewById(R.id.addEntry);
        back = (ImageButton) findViewById(R.id.journalBack);

        RecyclerView recycler = findViewById(R.id.journalRecycler);

        Intent addNewEntry = new Intent(this, NewJournalEntry.class);
        Intent goBack = new Intent(this, HomeScreen.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        JournalAdapter = new JournalAdapter(this, entries, this);
        recycler.setAdapter(JournalAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Checking if the user is logged in
        if(SaveUser.getUserName(ViewJournalEntries.this).length() != 0)
            key = SaveUser.getUserName(ViewJournalEntries.this);
        if(key == null) {
            key = " ";
        } else {
            //Referencing the database
            databaseReference.child("users").child(key).child("journalEntries").addListenerForSingleValueEvent(new ValueEventListener() {

                //Updating the recycler view when new journals are added.
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    entries.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String tempName = snapshot.child("name").getValue(String.class);
                        String tempContent = snapshot.child("content").getValue(String.class);

                        JournalEntry entry = new JournalEntry(tempName, tempContent);
                        entries.add(entry);
                    }

                    // Reverse the entries list

                    if (ViewJournalEntries.JournalAdapter != null) {
                        ViewJournalEntries.JournalAdapter.notifyDataSetChanged();
                    }
                }

                //Error message
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("DatabaseError", error.getMessage());
                }
            });
        }


        //add and back buttons
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

    @Override
    public void onItemClick(int position) {
        //this code is activated when a journal entry is selected from the list
        Intent edit = new Intent(this, editJournalEntry.class);

        edit.putExtra("TITLE", entries.get(position).getName());
        edit.putExtra("CONTENT", entries.get(position).getContent());

        startActivity(edit);

    }
}