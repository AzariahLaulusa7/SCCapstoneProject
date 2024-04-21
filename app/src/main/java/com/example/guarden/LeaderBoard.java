package com.example.guarden;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LeaderBoard extends AppCompatActivity {
    private TextView memoryGameScore, balloonGameScore, reactionGameScore;
    private TextView globalMemoryGameScore, globalBalloonGameScore, globalReactionGameScore;
    private DatabaseReference usersRef, globalScoresRef;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        firebaseAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        globalScoresRef = FirebaseDatabase.getInstance().getReference("globalScores");

        memoryGameScore = findViewById(R.id.memoryGameScore);
        balloonGameScore = findViewById(R.id.balloonGameScore);
        reactionGameScore = findViewById(R.id.reactionGameScore);
        globalMemoryGameScore = findViewById(R.id.globalMemoryGameScore);
        globalBalloonGameScore = findViewById(R.id.globalBalloonGameScore);
        globalReactionGameScore = findViewById(R.id.globalReactionGameScore);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        loadUserScores();
        loadGlobalScores();
    }

    private void loadUserScores() {
        FirebaseUser currentUser = firebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userScoresRef = usersRef.child(userId).child("userScores");
            userScoresRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Integer memoryScore = dataSnapshot.child("memoryGame").getValue(Integer.class);
                    Integer balloonScore = dataSnapshot.child("balloonGame").getValue(Integer.class);
                    Long reactionScore = dataSnapshot.child("reactionGame").getValue(Long.class);
                    updateScoreViews(memoryScore, balloonScore, reactionScore);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("LeaderBoard", "Failed to read user scores", databaseError.toException());
                }
            });
        } else {
            // You might want to handle this condition, e.g., prompt the user to log in
            Log.e("LeaderBoard", "User is not logged in.");
        }
    }

    private void updateScoreViews(Integer memoryScore, Integer balloonScore, Long reactionScore) {
        memoryGameScore.setText("Memory Game Best Score: " + (memoryScore != null ? memoryScore : "N/A"));
        balloonGameScore.setText("Balloon Game Best Score: " + (balloonScore != null ? balloonScore : "N/A"));
        reactionGameScore.setText("Reaction Game Best Time: " + (reactionScore != null ? reactionScore + "s" : "N/A"));
    }

    private void loadGlobalScores() {
        globalScoresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer memoryGlobal = dataSnapshot.child("memoryGame").getValue(Integer.class);
                Integer balloonGlobal = dataSnapshot.child("balloonGame").getValue(Integer.class);
                Long reactionGlobal = dataSnapshot.child("reactionGame").getValue(Long.class);
                globalMemoryGameScore.setText("Global Memory Game High Score: " + (memoryGlobal != null ? memoryGlobal : "N/A"));
                globalBalloonGameScore.setText("Global Balloon Game High Score: " + (balloonGlobal != null ? balloonGlobal : "N/A"));
                globalReactionGameScore.setText("Global Reaction Game Best Time: " + (reactionGlobal != null ? reactionGlobal + "s" : "N/A"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LeaderBoard", "Failed to read global scores", databaseError.toException());
            }
        });
    }
}
