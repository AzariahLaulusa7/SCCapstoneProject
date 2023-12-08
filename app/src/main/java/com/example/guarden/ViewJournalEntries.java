package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;



public class ViewJournalEntries extends AppCompatActivity {
    private Button back;
    private Button next;
    Intent newEntry = new Intent(this, NewJournalEntry.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_journal_entries);

        back = (Button) findViewById(R.id.BackToEntries);
        Button next = (Button) findViewById(R.id.nextPage);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        back.setOnClickListener(v -> finish());

       next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(newEntry);
            }
        });



    }
}