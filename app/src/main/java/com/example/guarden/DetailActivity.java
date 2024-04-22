package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {

    ImageButton back;
    TextView name, message, delete, tagTitle;
    EditText comment, editPost;
    ImageView image, editButton, filterView;
    String tagText = "";
    int tagTextBackground = -1;
    Intent intent, home;
    CommentAdapter adapterComment;
    Button commentbutton, sendButton, cancelButton;
    TextView vent, positive, question;
    RelativeLayout tagBox;
    private DatabaseReference commentRef, commentPostRef, userRef;
    private DatabaseReference forumRef, posRef, ventRef, qRef;

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
        delete = findViewById(R.id.delete_icon);
        editPost = findViewById(R.id.comment_text_edit);
        editButton = findViewById(R.id.edit);
        sendButton = findViewById(R.id.send);
        vent = findViewById(R.id.forum_tag_three);
        question = findViewById(R.id.forum_tag_two);
        positive = findViewById(R.id.forum_tag);
        tagBox = findViewById(R.id.tag_box);
        cancelButton = findViewById(R.id.cancel);
        filterView = findViewById(R.id.filter_view);
        tagTitle = findViewById(R.id.tag_title);
        intent = new Intent(this, ForumMain.class);
        home = new Intent(this, HomeScreen.class);

        userRef = FirebaseDatabase.getInstance().getReference("users");
        commentRef = FirebaseDatabase.getInstance().getReference("comment");
        commentPostRef = FirebaseDatabase.getInstance().getReference("comment");

        posRef = FirebaseDatabase.getInstance().getReference("positiveForum");
        qRef = FirebaseDatabase.getInstance().getReference("questionForum");
        ventRef = FirebaseDatabase.getInstance().getReference("ventForum");
        forumRef = FirebaseDatabase.getInstance().getReference("forum");

        vent.setBackground(getDrawable(R.drawable.grey_tag_background));
        question.setBackground(getDrawable(R.drawable.grey_tag_background));
        positive.setBackground(getDrawable(R.drawable.grey_tag_background));

        editButton.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
        sendButton.setVisibility(View.GONE);
        editPost.setVisibility(View.GONE);
        tagBox.setVisibility(View.GONE);
        filterView.setVisibility(View.GONE);
        tagTitle.setVisibility(View.GONE);


        back.setOnClickListener(v -> {
            startActivity(intent);
            finish();
        });

        String chosenName;
        String chosenMessage;
        String postKey;
        String postOwner = null;
        String tag;
        int tagBackground;
        int chosenImage;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                chosenName = null;
                chosenMessage = null;
                postKey = null;
                postOwner = null;
                tag = null;
                tagBackground = -1;
                chosenImage = -1;
            } else {
                chosenName = extras.getString("name");
                chosenMessage = extras.getString("message");
                chosenImage = extras.getInt("pic");
                postKey = extras.getString("postKey");
                postOwner = extras.getString("user");
                tag = extras.getString("tag");
                tagBackground = extras.getInt("tag_background");
            }
        } else {
            chosenName = null;
            chosenImage = -1;
            chosenMessage = null;
            tag = null;
            tagBackground = -1;
            postKey = null;
        }

        if (tag.equals("positivity")) {
            vent.setBackground(getDrawable(R.drawable.grey_tag_background));
            question.setBackground(getDrawable(R.drawable.grey_tag_background));
            positive.setBackground(getDrawable(R.drawable.positive_forum_tag));
        } else if (tag.equals("vent")) {
            vent.setBackground(getDrawable(R.drawable.vent_forum_tag));
            question.setBackground(getDrawable(R.drawable.grey_tag_background));
            positive.setBackground(getDrawable(R.drawable.grey_tag_background));
        } else {
            vent.setBackground(getDrawable(R.drawable.grey_tag_background));
            question.setBackground(getDrawable(R.drawable.question_forum_tag));
            positive.setBackground(getDrawable(R.drawable.grey_tag_background));
        }

        if (postOwner != null) {
            if (SaveUser.getUserName(DetailActivity.this).equals(postOwner)) {
                editButton.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            }
        }

        if (postKey != null) {
            commentRef = commentRef.child(postKey);
            commentPostRef = commentPostRef.child(postKey);
        }

        if (chosenName != null && chosenMessage != null && chosenImage >= 0) {
            name.setText(chosenName);
            message.setText(chosenMessage);
            editPost.setText(chosenMessage);
            image.setImageResource(chosenImage);
        }

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

        editButton.setOnClickListener(v -> {
            editPost.setVisibility(View.VISIBLE);
            sendButton.setVisibility(View.VISIBLE);
            message.setVisibility(View.GONE);
            editButton.setVisibility(View.GONE);
            tagBox.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
            filterView.setVisibility(View.VISIBLE);
            tagTitle.setVisibility(View.VISIBLE);
        });

        cancelButton.setOnClickListener(v -> {
            editPost.setVisibility(View.GONE);
            sendButton.setVisibility(View.GONE);
            message.setVisibility(View.VISIBLE);
            tagBox.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.GONE);
            filterView.setVisibility(View.GONE);
            tagTitle.setVisibility(View.GONE);
            editPost.setText(chosenMessage);
            if (tag.equals("positivity")) {
                vent.setBackground(getDrawable(R.drawable.grey_tag_background));
                question.setBackground(getDrawable(R.drawable.grey_tag_background));
                positive.setBackground(getDrawable(R.drawable.positive_forum_tag));
            } else if (tag.equals("vent")) {
                vent.setBackground(getDrawable(R.drawable.vent_forum_tag));
                question.setBackground(getDrawable(R.drawable.grey_tag_background));
                positive.setBackground(getDrawable(R.drawable.grey_tag_background));
            } else {
                vent.setBackground(getDrawable(R.drawable.grey_tag_background));
                question.setBackground(getDrawable(R.drawable.question_forum_tag));
                positive.setBackground(getDrawable(R.drawable.grey_tag_background));
            }
        });

        vent.setOnClickListener(v -> {
            tagText = "vent";
            tagTextBackground = R.drawable.vent_forum_tag;
            vent.setBackground(getDrawable(R.drawable.vent_forum_tag));
            question.setBackground(getDrawable(R.drawable.grey_tag_background));
            positive.setBackground(getDrawable(R.drawable.grey_tag_background));
        });

        question.setOnClickListener(v -> {
            tagText = "question";
            tagTextBackground = R.drawable.question_forum_tag;
            vent.setBackground(getDrawable(R.drawable.grey_tag_background));
            question.setBackground(getDrawable(R.drawable.question_forum_tag));
            positive.setBackground(getDrawable(R.drawable.grey_tag_background));
        });

        positive.setOnClickListener(v -> {
            tagText = "positivity";
            tagTextBackground = R.drawable.positive_forum_tag;
            vent.setBackground(getDrawable(R.drawable.grey_tag_background));
            question.setBackground(getDrawable(R.drawable.grey_tag_background));
            positive.setBackground(getDrawable(R.drawable.positive_forum_tag));
        });

        sendButton.setOnClickListener(v -> {
            final String newMessage = editPost.getText().toString().trim();
            if (!TextUtils.isEmpty(newMessage) && !tagText.isEmpty() && tagTextBackground != -1) {
                adapterComment.stopListening();
                Chat newChat = new Chat(chosenName, tagText, newMessage, chosenImage, tagTextBackground, postKey, SaveUser.getUserName(DetailActivity.this));
                forumRef.child(postKey).setValue(newChat)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(DetailActivity.this, "Post Updated", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(DetailActivity.this, "Failed To Update Post", Toast.LENGTH_SHORT).show());
                if (tagText.equals("positivity")) {
                    posRef.child(postKey).setValue(newChat);
                } else if (tagText.equals("vent")) {
                    ventRef.child(postKey).setValue(newChat);
                } else {
                    qRef.child(postKey).setValue(newChat);
                }
                finish();
                startActivity(home);
            } else if (!TextUtils.isEmpty(newMessage)) {
                adapterComment.stopListening();
                Chat newChat = new Chat(chosenName, tag, newMessage, chosenImage, tagBackground, postKey, SaveUser.getUserName(DetailActivity.this));
                forumRef.child(postKey).setValue(newChat)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(DetailActivity.this, "Post Updated", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(DetailActivity.this, "Failed To Update Post", Toast.LENGTH_SHORT).show());
                if (tag.equals("positivity")) {
                    posRef.child(postKey).setValue(newChat);
                } else if (tag.equals("vent")) {
                    ventRef.child(postKey).setValue(newChat);
                } else {
                    qRef.child(postKey).setValue(newChat);
                }
                finish();
                startActivity(home);
            } else {
                Toast.makeText(DetailActivity.this, "Please enter a post", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(v -> {
            adapterComment.stopListening();
            forumRef.child(postKey).removeValue();
            if (tag.equals("positivity")) {
                posRef.child(postKey).removeValue();
            } else if (tag.equals("vent")) {
                ventRef.child(postKey).removeValue();
            } else {
                qRef.child(postKey).removeValue();
            }
            finish();
            startActivity(intent);
        });

        commentbutton.setOnClickListener(v -> {
            final String message = comment.getText().toString().trim();

            if (!TextUtils.isEmpty(message)) {
                adapterComment.stopListening();
                String uniqueKey = userRef.push().getKey();
                Comment newChat = new Comment(message, SaveUser.getName(DetailActivity.this)+": ");
                if (uniqueKey != null && commentPostRef != null) {
                    commentPostRef.child(uniqueKey).setValue(newChat)
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

    private static class Chat {
        public String name, tag, message, key, user;
        public int image;
        public int tagBackground, orderNumber;

        public Chat() {}

        public Chat(String name, String tag, String message, int image, int tagBackground, String key, String user) {
            this.name = name;
            this.tag = tag;
            this.message = message;
            this.image = image;
            this.tagBackground = tagBackground;
            this.key = key;
            this.user = user;
        }
    }

}
