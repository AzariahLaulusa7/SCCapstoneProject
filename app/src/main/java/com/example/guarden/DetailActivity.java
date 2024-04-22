package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//Class to handle commenting under posts in the forum
public class DetailActivity extends AppCompatActivity {

    ImageButton back;
    TextView name, message;
    EditText comment;
    ImageView image;
    Intent intent;
    CommentAdapter adapterComment;
    Button commentbutton;
    private DatabaseReference commentRef, commentPostRef, userRef;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);

        RecyclerView recyclerViewComment = findViewById(R.id.comment_recyclerview);

        back = findViewById(R.id.comment_back_icon);
        name = findViewById(R.id.comment_id);
        message = findViewById(R.id.comment_text);
        image = findViewById(R.id.comment_image);
        commentbutton = findViewById(R.id.comment_button);
        comment = findViewById(R.id.comment_description);
        intent = new Intent(this, ForumMain.class);
        //Finds the database reference for each relevant object
        userRef = FirebaseDatabase.getInstance().getReference("users");
        commentRef = FirebaseDatabase.getInstance().getReference("comment");
        commentPostRef = FirebaseDatabase.getInstance().getReference("comment");

        back.setOnClickListener(v -> {
            startActivity(intent);
            finish();
        });

        String chosenName = null;
        String chosenMessage = null;
        String postKey = null;
        int chosenImage = -1;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) { //Checks to see if post has values for name, message, key, and image
                chosenName = null;
                chosenMessage = null;
                postKey = null;
                chosenImage = -1;
            } else { //Assigns post name, message, image, and key
                chosenName = extras.getString("latitude");
                chosenMessage = extras.getString("longitude");
                chosenImage = extras.getInt("pic");
                postKey = extras.getString("postKey");
            }
        }
        //Finds correct post associated with the comment
        if (postKey != null) {
            commentRef = commentRef.child(postKey);
            commentPostRef = commentPostRef.child(postKey);
        } else {
            Toast.makeText(this, "key: " + postKey, Toast.LENGTH_LONG).show();
        }

        if (chosenName != null && chosenMessage != null && chosenImage >= 0) {
            name.setText(chosenName);
            message.setText(chosenMessage);
            image.setImageResource(chosenImage);
        }
        //Sets up list view
        recyclerViewComment.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Comment> options = new FirebaseRecyclerOptions.Builder<Comment>()
                .setQuery(commentRef, Comment.class)
                .build();
        adapterComment = new CommentAdapter(options);
        recyclerViewComment.setAdapter(adapterComment);
        LinearLayoutManager lm = new LinearLayoutManager(DetailActivity.this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        recyclerViewComment.setLayoutManager(lm);
        //Adds comment when comment button is pressed
        commentbutton.setOnClickListener(v -> {
            //Gets user inputted text
            final String message = comment.getText().toString().trim();
            //Checks to make sure comment exists
            if (!TextUtils.isEmpty(message)) {
                adapterComment.stopListening();
                String uniqueKey = userRef.push().getKey();
                Comment newChat = new Comment(message, SaveUser.getName(DetailActivity.this)+": ");
                if (uniqueKey != null && commentPostRef != null) {
                    commentPostRef.child(uniqueKey).setValue(newChat)//Adds comment to database
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(this, "Comment Created", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Failed To Create Comment", Toast.LENGTH_SHORT).show());
                    finish();
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "Please enter a comment to send", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public  void onStart() {
        super.onStart();
        adapterComment.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterComment.stopListening();
    }
    @Override
    public void onPause() {
        super.onPause();
        adapterComment.stopListening();
    }

    public void onBackPressed() {
        super.onBackPressed();
        adapterComment.stopListening();
        startActivity(intent);
    }

    private static class CommentTemp {

        String message;

        public CommentTemp() {}

        public CommentTemp(String message) {
            this.message = message;
        }
    }

}
