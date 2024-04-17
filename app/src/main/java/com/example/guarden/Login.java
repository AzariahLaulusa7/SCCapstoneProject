package com.example.guarden;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private DatabaseReference databaseReference;
    public static String UserID;
    static String emailKey;
    static Boolean skipFlag = false;
    Intent stayIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button loginbtn = findViewById(R.id.loginbtn);
        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        TextView tvCreateAccount = findViewById(R.id.tvCreateAccount);
        TextView tvSkip = findViewById(R.id.tvSkip);

        stayIntent = new Intent(Login.this, Login.class);

        //setListeners();

        tvSkip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                skipFlag = true;
                Intent intent = new Intent(Login.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = emailEditText.getText().toString().trim();
                String enteredPassword = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(enteredEmail) || TextUtils.isEmpty(enteredPassword)) {
                    Toast.makeText(Login.this, "Email and password must not be empty", Toast.LENGTH_SHORT).show();
                    return; // Exit the OnClickListener if fields are empty
                }

                emailKey = enteredEmail.replace(".", ",");

                databaseReference.child(emailKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null && user.password.equals(enteredPassword)) {
                            Toast.makeText(Login.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, HomeScreen.class);
                            UserID=user.email;
                            SaveUser.setUserName(Login.this, emailKey);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(stayIntent);
    }

    private static class User {
        public ArrayList<Pose> poseList;
        public String email, password, firstName, lastName, image;

        public User() {}

        public User(String email, String password, String firstName, String lastName) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public User(String email, String password, String firstName, String lastName, String image, ArrayList<Pose> poseList) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.image = image;
            this.poseList = poseList;
        }
    }
}
