package com.example.guarden;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class MovementNewCustom extends AppCompatActivity {
    Button save;
    Button cancel;
    Spinner categorySelect;
    private EditText editTextExerciseName;
    private EditText editTextExerciseDescription;
    private String category;
    private DatabaseReference databaseReference;
    static String key;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movement_add_custom);
        editTextExerciseName = (EditText) findViewById(R.id.editTextExerciseName);
        editTextExerciseDescription = (EditText) findViewById(R.id.editTextExerciseDescription);
        categorySelect = (Spinner) findViewById(R.id.spinnerExerciseCategory);
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Intent goBack = new Intent(this, MovementViewList.class);

        if(SaveUser.getUserName(MovementNewCustom.this).length() != 0)
            key = SaveUser.getUserName(MovementNewCustom.this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextExerciseName.getText().toString().trim();
                String description = editTextExerciseDescription.getText().toString().trim();
                String category = categorySelect.getSelectedItem().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(MovementNewCustom.this, "Please add a name", Toast.LENGTH_SHORT).show();
                    return;
                }
                Pose pose = new Pose(category,name,0,description,0);
                if (key != null) {
                    databaseReference.child("users").child(key).child("customPoses").child(name).setValue(pose)
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(MovementNewCustom.this, "Pose Created", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e ->
                                    Toast.makeText(MovementNewCustom.this, "Failed to create pose" + e.getMessage(), Toast.LENGTH_SHORT).show());
                    finish();
                } else {
                    Toast.makeText(MovementNewCustom.this, "Failed to create pose", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goBack);
            }
        });
    }
}

