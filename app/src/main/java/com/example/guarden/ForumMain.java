package com.example.guarden;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.guarden.R;

import java.util.Collections;
import java.util.Comparator;

public class ForumMain extends AppCompatActivity {

    // Variables
    ImageButton newPost;
    ImageView back;
    TextView tag_title, vent, question, positive, all, old, newest;
    RelativeLayout filter, sort;
    PostAdapter adapter;
    Intent myIntent;
    ImageView forum_view;
    String tagText = " ";
    Boolean filterActive = false;
    Boolean oldActive = false;
    Boolean sortActive = false;
    Intent restart;
    private DatabaseReference forumRef, posForumRef, ventForumRef, questionForumRef;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        // Initialize variables
        back = findViewById(R.id.backButton);
        newPost = findViewById(R.id.new_message);
        filter = findViewById(R.id.filter);
        sort = findViewById(R.id.sort);

        forum_view = findViewById(R.id.filter_view);
        tag_title = findViewById(R.id.tag_title);
        vent = findViewById(R.id.forum_tag_three);
        question = findViewById(R.id.forum_tag_two);
        positive = findViewById(R.id.forum_tag);
        all = findViewById(R.id.forum_tag_four);
        old = findViewById(R.id.oldest_tag);
        newest = findViewById(R.id.newest_tag);

        forum_view.setVisibility(View.GONE);
        tag_title.setVisibility(View.GONE);
        vent.setVisibility(View.GONE);
        question.setVisibility(View.GONE);
        positive.setVisibility(View.GONE);
        all.setVisibility(View.GONE);
        old.setVisibility(View.GONE);
        newest.setVisibility(View.GONE);

        old.setBackground(getDrawable(R.drawable.grey_tag_background));

        myIntent = new Intent(ForumMain.this, HomeScreen.class);
        Intent postPage = new Intent(ForumMain.this, Forums.class);
        restart = new Intent(ForumMain.this, ForumMain.class);

        forumRef = FirebaseDatabase.getInstance().getReference().child("forum");
        posForumRef = FirebaseDatabase.getInstance().getReference().child("positiveForum");
        ventForumRef = FirebaseDatabase.getInstance().getReference().child("ventForum");
        questionForumRef = FirebaseDatabase.getInstance().getReference().child("questionForum");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Post> startOptions = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(forumRef, Post.class)
                .build();
        adapter = new PostAdapter(startOptions);
        recyclerView.setAdapter(adapter);

        // When back button is pressed, go to previous screen -> home
        back.setOnClickListener(v -> {
            startActivity(myIntent);
            finish();
        });

        // When new post is clicked
        newPost.setOnClickListener(v -> {
            startActivity(postPage);
        });

        // When filter is clicked
        filter.setOnClickListener(v -> {
            tag_title.setText("Filter:");
            if (filterActive == true) { //Hides all appropriate buttons after filter button is clicked
                forum_view.setVisibility(View.GONE);
                tag_title.setVisibility(View.GONE);
                vent.setVisibility(View.GONE);
                question.setVisibility(View.GONE);
                positive.setVisibility(View.GONE);
                all.setVisibility(View.GONE);
                newest.setVisibility(View.GONE);
                old.setVisibility(View.GONE);
                filterActive = false;
            } else { //Shows all appropriate buttons after filter button is clicked
                newest.setVisibility(View.GONE);
                old.setVisibility(View.GONE);
                forum_view.setVisibility(View.VISIBLE);
                tag_title.setVisibility(View.VISIBLE);
                vent.setVisibility(View.VISIBLE);
                question.setVisibility(View.VISIBLE);
                positive.setVisibility(View.VISIBLE);
                all.setVisibility(View.VISIBLE);
                filterActive = true;
                sortActive = false;
            }
        });
        //When sort is clicked
        sort.setOnClickListener(v -> {
            tag_title.setText("Sort:");
            if (sortActive == true) { //Hides all appropriate buttons after sort button is clicked
                forum_view.setVisibility(View.GONE);
                tag_title.setVisibility(View.GONE);
                vent.setVisibility(View.GONE);
                question.setVisibility(View.GONE);
                positive.setVisibility(View.GONE);
                all.setVisibility(View.GONE);
                newest.setVisibility(View.GONE);
                old.setVisibility(View.GONE);
                sortActive = false;
            } else { //Shows all appropriate buttons after sort button is clicked
                forum_view.setVisibility(View.VISIBLE);
                tag_title.setVisibility(View.VISIBLE);
                newest.setVisibility(View.VISIBLE);
                old.setVisibility(View.VISIBLE);
                vent.setVisibility(View.GONE);
                question.setVisibility(View.GONE);
                positive.setVisibility(View.GONE);
                all.setVisibility(View.GONE);
                sortActive = true;
                filterActive = false;
            }
        });
        //Sets layout to sort-by-newest
        newest.setOnClickListener(v -> {
            newest.setBackground(getDrawable(R.drawable.picked_image_background));
            old.setBackground(getDrawable(R.drawable.grey_tag_background));
            oldActive = false;
            LinearLayoutManager lm = new LinearLayoutManager(ForumMain.this);
            lm.setReverseLayout(false);
            lm.setStackFromEnd(false);

            recyclerView.setLayoutManager(lm);
        });
        //Sets layout to sort-by-oldest
        old.setOnClickListener(v -> {
            newest.setBackground(getDrawable(R.drawable.grey_tag_background));
            old.setBackground(getDrawable(R.drawable.picked_image_background));
            oldActive = true;
            LinearLayoutManager lm = new LinearLayoutManager(ForumMain.this);
            lm.setReverseLayout(true);
            lm.setStackFromEnd(true);

            recyclerView.setLayoutManager(lm);
        });

        //Filters post by tag:vent
        vent.setOnClickListener(v -> {
            tagText = "vent";
            vent.setBackground(getDrawable(R.drawable.vent_forum_tag));
            question.setBackground(getDrawable(R.drawable.grey_tag_background));
            positive.setBackground(getDrawable(R.drawable.grey_tag_background));
            all.setBackground(getDrawable(R.drawable.grey_tag_background));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(ventForumRef, Post.class)
                    .build();
            adapter = new PostAdapter(options);

            recyclerView.setAdapter(adapter);
            adapter.stopListening();
            adapter.startListening();

            LinearLayoutManager lm = new LinearLayoutManager(ForumMain.this);
            if (oldActive) {
                lm.setReverseLayout(true);
                lm.setStackFromEnd(true);
            } else {
                lm.setReverseLayout(false);
                lm.setStackFromEnd(false);
            }
            recyclerView.setLayoutManager(lm);
        });

        //Filters posts by tag:question
        question.setOnClickListener(v -> {
            tagText = "question";
            vent.setBackground(getDrawable(R.drawable.grey_tag_background));
            question.setBackground(getDrawable(R.drawable.question_forum_tag));
            positive.setBackground(getDrawable(R.drawable.grey_tag_background));
            all.setBackground(getDrawable(R.drawable.grey_tag_background));
            FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(questionForumRef, Post.class)
                    .build();
            adapter = new PostAdapter(options);

            recyclerView.setAdapter(adapter);
            adapter.stopListening();
            adapter.startListening();

            LinearLayoutManager lm = new LinearLayoutManager(ForumMain.this);
            if (oldActive) {
                lm.setReverseLayout(true);
                lm.setStackFromEnd(true);
            } else {
                lm.setReverseLayout(false);
                lm.setStackFromEnd(false);
            }
            recyclerView.setLayoutManager(lm);
        });

        //Filters posts by tag:positivity
        positive.setOnClickListener(v -> {
            tagText = "positivity";
            vent.setBackground(getDrawable(R.drawable.grey_tag_background));
            question.setBackground(getDrawable(R.drawable.grey_tag_background));
            positive.setBackground(getDrawable(R.drawable.positive_forum_tag));
            all.setBackground(getDrawable(R.drawable.grey_tag_background));
            FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(posForumRef.orderByValue(), Post.class)
                    .build();
            adapter = new PostAdapter(options);

            recyclerView.setAdapter(adapter);
            adapter.stopListening();
            adapter.startListening();

            LinearLayoutManager lm = new LinearLayoutManager(ForumMain.this);
            if (oldActive) {
                lm.setReverseLayout(true);
                lm.setStackFromEnd(true);
            } else {
                lm.setReverseLayout(false);
                lm.setStackFromEnd(false);
            }
            recyclerView.setLayoutManager(lm);
        });
        //Removes filters
        all.setOnClickListener(v -> {

            vent.setBackground(getDrawable(R.drawable.grey_tag_background));
            question.setBackground(getDrawable(R.drawable.grey_tag_background));
            positive.setBackground(getDrawable(R.drawable.grey_tag_background));
            all.setBackground(getDrawable(R.drawable.picked_image_background));

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(forumRef, Post.class)
                    .build();
            adapter = new PostAdapter(options);

            recyclerView.setAdapter(adapter);
            adapter.stopListening();
            adapter.startListening();

            LinearLayoutManager lm = new LinearLayoutManager(ForumMain.this);
            if (oldActive) {
                lm.setReverseLayout(true);
                lm.setStackFromEnd(true);
            } else {
                lm.setReverseLayout(false);
                lm.setStackFromEnd(false);
            }
            recyclerView.setLayoutManager(lm);
        });

    }

    @Override
    public  void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adapter.stopListening();
        startActivity(myIntent);
    }

    private static class Chat {
        public String name, tag, message;

        public Chat() {}

        public Chat(String name, String tag, String message) {
            this.name = name;
            this.tag = tag;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}