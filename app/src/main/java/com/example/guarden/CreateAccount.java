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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText, firstNameEditText, lastNameEditText;
    private Button signUpButton;
    private ImageButton editPen;
    private DatabaseReference databaseReference;
    static ImageView image;
    private RoundedBitmapDrawable roundedImage;
    private String imageUrl;

    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        image = findViewById(R.id.add_profile_image);
        usernameEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmpass);
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        signUpButton = findViewById(R.id.signupbtn);
        editPen = findViewById(R.id.new_edit_image);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        ImageView backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v -> finish());

        StorageReference storageRef = storage.getInstance().getReference();

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

                    image.setImageDrawable(resizedImage);

                } catch (FileNotFoundException e) {// handle exception
                    e.printStackTrace();
                    Toast.makeText(CreateAccount.this, "Something went wrong: **"+e+"**", Toast.LENGTH_LONG).show();
                }
            } else {// didn't pick an image
                Toast.makeText(CreateAccount.this, "Failed to replace image. Please pick an image", Toast.LENGTH_LONG).show();
            }
        });

        // Send user to gallery -> activity launcher
        editPen.setOnClickListener(v -> {

            Intent gallery = new Intent(Intent.ACTION_PICK);
            gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityLauncher.launch(gallery);

        });

        signUpButton.setOnClickListener(v -> {
            final String email = usernameEditText.getText().toString().trim();
            final String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            final String firstName = firstNameEditText.getText().toString().trim();
            final String lastName = lastNameEditText.getText().toString().trim();
            ArrayList<Pose> customPoses = new ArrayList<Pose>();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)
                    || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || image == null) {
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

            if (imageUri != null) {
                storageRef.child("users").child(email.replace(".", ",")).putFile(imageUri)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(CreateAccount.this, "Storage Created", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(CreateAccount.this, "Failed to create storage: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
            addDefaultPosesToList(email);
            finish();
        });
    }

    public void addDefaultPosesToList(String email){
        Pose lunge = new Pose("yoga","Lunge",R.drawable.pose1,"", 0);
        databaseReference.child("users").child(email).child("customPoses").child("Lunge").setValue(lunge);
        Pose triangle = new Pose("yoga","Triangle",R.drawable.pose2,"", 0);
        databaseReference.child("users").child(email).child("customPoses").child("Triangle").setValue(triangle);
        Pose forwardFold = new Pose("yoga","Forward Fold",R.drawable.pose3,"", 0);
        databaseReference.child("users").child(email).child("customPoses").child("Forward Fold").setValue(forwardFold);
        Pose pushUp = new Pose("exercise","Push Up",R.drawable.exercise1,"", 0);
        databaseReference.child("users").child(email).child("customPoses").child("Push Up").setValue(pushUp);
        Pose sitUp = new Pose("exercise","Sit Up",R.drawable.exercise2,"", 0);
        databaseReference.child("users").child(email).child("customPoses").child("Sit Up").setValue(sitUp);
        Pose squat = new Pose("exercise","Squat",R.drawable.exercise3,"", 0);
        databaseReference.child("users").child(email).child("customPoses").child("Squat").setValue(squat);
    }

    public class User {
        private final ArrayList<Pose> customPoses;
        public String email, password, firstName, lastName;


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