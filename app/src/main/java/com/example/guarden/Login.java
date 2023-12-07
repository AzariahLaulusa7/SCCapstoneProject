package com.example.guarden;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginbtn = findViewById(R.id.loginbtn);
        TextView tvCreateAccount = findViewById(R.id.tvCreateAccount);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = emailEditText.getText().toString().trim();
                String enteredPassword = passwordEditText.getText().toString().trim();
                String emailKey = enteredEmail.replace(".", ",");

                databaseReference.child(emailKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null && user.password.equals(enteredPassword)) {
                            Toast.makeText(Login.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MoveMain.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Login.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
            }
        });
    }

    private static class User {
        public String email;
        public String password;

        public User() {}

        public User(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}
