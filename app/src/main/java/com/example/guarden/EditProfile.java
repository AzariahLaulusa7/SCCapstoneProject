package com.example.guarden;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.C;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class EditProfile extends AppCompatActivity {

    // Variables

    // Icons from:
    // pen - "https://www.freepik.com/icon/pen_11257674#fromView=search&term=edit&track=ais&page=1&position=9&uuid=80ab2d69-3755-4c46-b9ec-831d59861ed7"
    // id - "https://www.flaticon.com/free-icon/id-card_9512601?term=id&page=2&position=9&origin=search&related_id=9512601"
    // phone - "https://www.flaticon.com/free-icon/telephone_724664?term=phone&page=1&position=16&origin=search&related_id=724664"
    // mail - "https://www.flaticon.com/free-icon/mail_9068642?term=email&page=1&position=15&origin=search&related_id=9068642"
    // lock - "https://www.flaticon.com/free-icon/padlock_10542551?term=password&page=1&position=26&origin=search&related_id=10542551"

    private ImageView profileImage, databaseImage;
    private EditText usernameEditText, firstNameEditText, lastNameEditText, passwordEditText;
    private DatabaseReference userRef;
    private String imageUrl;
    private StorageReference storageRef;
    private StorageReference imageRef;
    private Uri imageUri;
    static String key;
    static ArrayList<Pose> getCustomPoses;
    Intent myIntent;

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
        myIntent = new Intent(this, HomeScreen.class);
        Intent restartIntent = new Intent(this, Login.class);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("users");

        // Set an image if profileImage is not null
        //if(CreateAccount.image != null)
        //    profileImage.setImageDrawable(CreateAccount.image.getDrawable());

        // Retrieving image from gallery
        ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK) {

                    Intent data = result.getData();

                try {

                    imageUri = data != null ? data.getData() : null;
                    assert imageUri != null;
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap newImage = BitmapFactory.decodeStream(imageStream);

                    Bitmap resizedImageBitmap = Bitmap.createScaledBitmap(newImage, 150, 150, true);
                    Drawable resizedImage = new BitmapDrawable(getResources(), resizedImageBitmap);

                    profileImage.setImageDrawable(resizedImage);

                } catch (FileNotFoundException e) {// handle exception
                    e.printStackTrace();
                    Toast.makeText(EditProfile.this, "Something went wrong: **"+e+"**", Toast.LENGTH_LONG).show();
                }
            } else {// didn't pick an image
                Toast.makeText(EditProfile.this, "Failed to replace image. Please pick an image", Toast.LENGTH_LONG).show();
            }
        });

        // Go back to home
        back.setOnClickListener(v ->{
            startActivity(myIntent);
            finish();
        });

        // Send user to gallery -> activity launcher
        editImage.setOnClickListener(v -> {

            Intent gallery = new Intent(Intent.ACTION_PICK);
            gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityLauncher.launch(gallery);

        });

        if(SaveUser.getUserName(EditProfile.this).length() != 0)
            key = SaveUser.getUserName(EditProfile.this);
        if(key == null) {
            key = " ";
        } else {
            userRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        firstNameEditText.setText(user.firstName);
                        lastNameEditText.setText(user.lastName);
                        usernameEditText.setText(user.email);
                        passwordEditText.setText(user.password);
                        getCustomPoses = user.customPoses;
                    } else {
                        Toast.makeText(EditProfile.this, "LOADING CREDENTIALS FAILED", Toast.LENGTH_SHORT).show();
                    }

                    storageRef = storage.getReference();
                    imageRef = storageRef.child("/users/" + key);

                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        if (uri != null) {
                            String imageUrl = uri.toString();
                            Picasso.get().load(imageUrl).resize(150, 150).into(profileImage);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled
                }
            });
        }

        //TODO: Save user's custom poses when the profile is updated

        // Update data if requirements are met
        editButton.setOnClickListener(v -> {
            final String email = usernameEditText.getText().toString().trim();
            final String password = passwordEditText.getText().toString().trim();
            final String firstName = firstNameEditText.getText().toString().trim();
            final String lastName = lastNameEditText.getText().toString().trim();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(password)) {
                // Password and Email check from Joe in Create Account class
                // Validate email
                if (!isValidEmail(email)) {
                    Toast.makeText(EditProfile.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate password
                if (!isValidPassword(password)) {
                    Toast.makeText(EditProfile.this, "Password must contain a capital letter, number and be at least 7 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                User updatedUser = new User(email, password, firstName, lastName, getCustomPoses);
                if (key == " ") {
                    key = email.replace(".",",");
                    userRef.child(email.replace(".",",")).setValue(updatedUser)
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(EditProfile.this, "Account Created", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e ->
                                    Toast.makeText(EditProfile.this, "Failed To Create Account", Toast.LENGTH_SHORT).show());
                    if (imageUri != null) {
                        storageRef.child("users").child(email.replace(".",",")).putFile(imageUri)
                                .addOnFailureListener(e ->
                                        Toast.makeText(EditProfile.this, "Failed To Save Image", Toast.LENGTH_SHORT).show());
                    }
                    SaveUser.setUserName(EditProfile.this, key);
                    addDefaultPosesToList(key);
                    finish();
                    startActivity(restartIntent);
                } else if (!key.equals(email.replace(".",","))) {
                    if (imageUri != null) {
                        key = email.replace(".",",");
                        storageRef.child("users").child(email.replace(".",",")).putFile(imageUri)
                                .addOnFailureListener(e ->
                                        Toast.makeText(EditProfile.this,  "Failed To Save Image", Toast.LENGTH_LONG).show());
                        storageRef.child("/users/"+SaveUser.getUserName(EditProfile.this)).delete()
                                .addOnFailureListener(e ->
                                        Toast.makeText(EditProfile.this, "Failed To Delete Old Image", Toast.LENGTH_SHORT).show());
                        userRef.child(SaveUser.getUserName(EditProfile.this)).removeValue();
                        userRef.child(email.replace(".",",")).setValue(updatedUser)
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e ->
                                        Toast.makeText(EditProfile.this, "Failed To Update", Toast.LENGTH_SHORT).show());
                        SaveUser.setUserName(EditProfile.this, key);
                        addDefaultPosesToList(key);
                        finish();
                        startActivity(restartIntent);
                    } else {
                        profileImage.setImageDrawable(getDrawable(R.drawable.user_blue_circle_128));
                        Toast.makeText(EditProfile.this, "Please Choose A New Image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //userRef.child("users").child(Login.emailKey).removeValue();

                    userRef.child(SaveUser.getUserName(EditProfile.this)).setValue(updatedUser)
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e ->
                                    Toast.makeText(EditProfile.this, "Failed To Update", Toast.LENGTH_SHORT).show());
                    if(imageUri != null) {
                        //storageRef.child("/users/"+Login.emailKey).delete();
                        storageRef.child("users").child(SaveUser.getUserName(EditProfile.this)).putFile(imageUri)
                                .addOnFailureListener(e ->
                                        Toast.makeText(EditProfile.this, "Failed To Save Image", Toast.LENGTH_SHORT).show());
                    }
                    addDefaultPosesToList(SaveUser.getUserName(EditProfile.this));
                    startActivity(myIntent);
                    finish();
                }
            } else {
                Toast.makeText(EditProfile.this, "Please FILL all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(myIntent);
    }

    // Password and Email check from Joe in Create Account class
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 7) {
            return false;
        }
        boolean foundNumber = false;
        boolean foundCapital = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                foundNumber = true;
            }
            if (Character.isUpperCase(c)) {
                foundCapital = true;
            }
            if (foundNumber && foundCapital) {
                return true;  // Return early if both conditions are met
            }
        }
        return false;  // Return false if either digit or uppercase letter is not found
    }

    // Poses from Adrian
    public void addDefaultPosesToList(String email){
        Pose lunge = new Pose("yoga","Lunge",R.drawable.pose1,"", 0);
        userRef.child(email).child("customPoses").child("Lunge").setValue(lunge);
        Pose triangle = new Pose("yoga","Triangle",R.drawable.pose2,"", 0);
        userRef.child(email).child("customPoses").child("Triangle").setValue(triangle);
        Pose forwardFold = new Pose("yoga","Forward Fold",R.drawable.pose3,"", 0);
        userRef.child(email).child("customPoses").child("Forward Fold").setValue(forwardFold);
        Pose pushUp = new Pose("exercise","Push Up",R.drawable.exercise1,"", 0);
        userRef.child(email).child("customPoses").child("Push Up").setValue(pushUp);
        Pose sitUp = new Pose("exercise","Sit Up",R.drawable.exercise2,"", 0);
        userRef.child(email).child("customPoses").child("Sit Up").setValue(sitUp);
        Pose squat = new Pose("exercise","Squat",R.drawable.exercise3,"", 0);
        userRef.child(email).child("customPoses").child("Squat").setValue(squat);
    }

    private static class User {
        public String email, password, firstName, lastName, image;
        private ArrayList<Pose> customPoses;

        public User() {}

        public User(String email, String password, String firstName, String lastName) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public User(String email, String password, String firstName, String lastName, String image) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.image = image;
        }

        public User(String email, String password, String firstName, String lastName, ArrayList<Pose> customPoses) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.customPoses = customPoses;
        }
    }
}