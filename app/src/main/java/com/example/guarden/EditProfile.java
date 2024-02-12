package com.example.guarden;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfile extends AppCompatActivity {

    // Variables

    // Icons from:
    // pen - "https://www.freepik.com/icon/pen_11257674#fromView=search&term=edit&track=ais&page=1&position=9&uuid=80ab2d69-3755-4c46-b9ec-831d59861ed7"
    // id - "https://www.flaticon.com/free-icon/id-card_9512601?term=id&page=2&position=9&origin=search&related_id=9512601"
    // phone - "https://www.flaticon.com/free-icon/telephone_724664?term=phone&page=1&position=16&origin=search&related_id=724664"
    // mail - "https://www.flaticon.com/free-icon/mail_9068642?term=email&page=1&position=15&origin=search&related_id=9068642"
    // lock - "https://www.flaticon.com/free-icon/padlock_10542551?term=password&page=1&position=26&origin=search&related_id=10542551"

    private ImageView profileImage;
    private EditText usernameEditText, firstNameEditText, lastNameEditText, passwordEditText;
    private DatabaseReference userRef;

    ImageButton back, editImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        // Assign values
        profileImage = findViewById(R.id.profile_image);
        usernameEditText = findViewById(R.id.email);
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        passwordEditText = findViewById(R.id.password);
        Button editButton = findViewById(R.id.editbtn);
        editImage = findViewById(R.id.edit_image);
        back = findViewById(R.id.backIcon);
        Intent myIntent = new Intent(this, HomeScreen.class);

        // Retrieving image from gallery
        ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK) {

                    Intent data = result.getData();

                try {

                    final Uri imageUri = data != null ? data.getData() : null;
                    assert imageUri != null;
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap newImage = BitmapFactory.decodeStream(imageStream);

                    Bitmap resizedImage = Bitmap.createScaledBitmap(newImage, 150, 150, true);
                    RoundedBitmapDrawable roundedImage = RoundedBitmapDrawableFactory.create(getResources(), resizedImage);
                    roundedImage.setCircular(true);

                    profileImage.setImageDrawable(roundedImage);

                } catch (FileNotFoundException e) {// handle exception
                    e.printStackTrace();
                    Toast.makeText(EditProfile.this, "Something went wrong: **"+e+"**", Toast.LENGTH_LONG).show();
                }
            } else {// didn't pick an image
                Toast.makeText(EditProfile.this, "Failed to replace image. Please pick an image", Toast.LENGTH_LONG).show();
            }
        });

        // Go back to home
        back.setOnClickListener(v -> startActivity(myIntent));

        // Send user to gallery -> activity launcher
        editImage.setOnClickListener(v -> {

            Intent gallery = new Intent(Intent.ACTION_PICK);
            gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityLauncher.launch(gallery);

        });

        // Get profile data from firebase
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            usernameEditText.setText(user.getEmail());
                            firstNameEditText.setText(user.getFirstName());
                            lastNameEditText.setText(user.getLastName());
                            passwordEditText.setText(user.getPassword());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled
                }
            });
        }

        // Update data if requirements are met
        editButton.setOnClickListener(v -> {
            final String email = usernameEditText.getText().toString().trim();
            final String password = passwordEditText.getText().toString().trim();
            final String firstName = firstNameEditText.getText().toString().trim();
            final String lastName = lastNameEditText.getText().toString().trim();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(password)) {
                User updatedUser = new User(email, password, firstName, lastName);
                userRef.setValue(updatedUser)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(EditProfile.this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                finish();
            } else {
                Toast.makeText(EditProfile.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // User class
    //TODO: add image to firebase
    private static class User extends CreateAccount{
        public String email, password, firstName, lastName;

        public User(String email, String password, String firstName, String lastName) {
            this.password = password;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPassword() {
            return password;
        }
    }
}