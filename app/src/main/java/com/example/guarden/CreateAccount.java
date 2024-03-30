package com.example.guarden;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.text.TextUtils;
import android.widget.Button;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText, firstNameEditText, lastNameEditText;
    private Button signUpButton;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        usernameEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmpass);
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        signUpButton = findViewById(R.id.signupbtn);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        ImageView backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v -> finish());

        signUpButton.setOnClickListener(v -> {
            final String email = usernameEditText.getText().toString().trim();
            final String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            final String firstName = firstNameEditText.getText().toString().trim();
            final String lastName = lastNameEditText.getText().toString().trim();
            ArrayList<Pose> customPoses = new ArrayList<Pose>();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)
                    || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
                Toast.makeText(CreateAccount.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(CreateAccount.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(email, password, firstName, lastName, customPoses);


            databaseReference.child("users").child(email.replace(".",",")).setValue(newUser)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(CreateAccount.this, "Account Created", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(CreateAccount.this, "Failed to create account: " + e.getMessage(), Toast.LENGTH_SHORT).show());

            finish();
        });
    }

    public class User {
        public String email, password, firstName, lastName;
        public ArrayList<Pose> customPoses;

        public User(String email, String password, String firstName, String lastName, ArrayList<Pose> customPoses) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.customPoses = customPoses;
        }
        public ArrayList<Pose> getCustomPoses(){
            return this.customPoses;
        }

    }
}