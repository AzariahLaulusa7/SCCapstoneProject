package com.example.guarden;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

//
public class TherapistsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TherapistsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapists);
        ImageView backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.therapistRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Loads therapists from JSON file
        List<Therapist> therapists = JSONHelper.loadTherapistsFromJson(this, "therapists.json");
        adapter = new TherapistsAdapter(therapists, this);
        recyclerView.setAdapter(adapter);
    }
}
