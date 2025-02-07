package com.example.guarden;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LeaderBoard extends AppCompatActivity {
    private TextView memoryGameScore, balloonGameScore, reactionGameScore;
    private TextView globalMemoryGameScore, globalBalloonGameScore, globalReactionGameScore;
    private DatabaseReference usersRef;
    private int globalMemoryBest = 0, globalBalloonBest = 0;
    private long globalReactionBest = Long.MAX_VALUE;  // Assuming lower scores are better for reaction times

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        usersRef = FirebaseDatabase.getInstance().getReference("users");
        //Initializes text view for each score displayed
        memoryGameScore = findViewById(R.id.memoryGameScore);
        balloonGameScore = findViewById(R.id.balloonGameScore);
        reactionGameScore = findViewById(R.id.reactionGameScore);
        globalMemoryGameScore = findViewById(R.id.globalMemoryGameScore);
        globalBalloonGameScore = findViewById(R.id.globalBalloonGameScore);
        globalReactionGameScore = findViewById(R.id.globalReactionGameScore);

        findViewById(R.id.backIcon).setOnClickListener(v -> finish());
        //Pulls scores from the database to display to the user
        loadUserScores();
        loadGlobalScores();
    }

    private void loadUserScores() {
        String username = SaveUser.getUserName(this);
        if (username != null && !username.isEmpty()) { //Checks if user is logged in
            DatabaseReference userScoresRef = usersRef.child(username).child("userScores");
            userScoresRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Pulls the user's current high score for each game from the database
                    Integer memoryScore = dataSnapshot.child("memoryGame").getValue(Integer.class);
                    Integer balloonScore = dataSnapshot.child("balloonGame").getValue(Integer.class);
                    Long reactionScore = dataSnapshot.child("reactionGame").getValue(Long.class);
                    //Calls method to set the textview to the current high scores
                    updateScoreViews(memoryScore, balloonScore, reactionScore);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("LeaderBoard", "Failed to read user scores", databaseError.toException());
                }
            });
        } else {
            Log.e("LeaderBoard", "Invalid username. User might not be logged in or username not set.");
        }
    }
    //Sets each text view to display the current user high score
    private void updateScoreViews(Integer memoryScore, Integer balloonScore, Long reactionScore) {
        memoryGameScore.setText("Memory Game Best Score: " + (memoryScore != null ? memoryScore : "N/A"));
        balloonGameScore.setText("Balloon Game Best Score: " + (balloonScore != null ? balloonScore : "N/A"));
        reactionGameScore.setText("Reaction Game Best Time: " + (reactionScore != null ? reactionScore + "ms" : "N/A"));
    }

    //Pulls the values for the global best score for each game
    private void loadGlobalScores() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataSnapshot scoresSnapshot = snapshot.child("userScores");
                    if (scoresSnapshot.exists()) {
                        //Pulls each global best score from the database
                        Integer memoryGame = scoresSnapshot.child("memoryGame").getValue(Integer.class);
                        Integer balloonGame = scoresSnapshot.child("balloonGame").getValue(Integer.class);
                        Long reactionGame = scoresSnapshot.child("reactionGame").getValue(Long.class);
                        //Checks to make sure the user's score isn't higher than the global best score
                        //If it is, sets the global best core to the user's best score
                        if (memoryGame != null && memoryGame > globalMemoryBest) {
                            globalMemoryBest = memoryGame;
                        }
                        if (balloonGame != null && balloonGame > globalBalloonBest) {
                            globalBalloonBest = balloonGame;
                        }
                        if (reactionGame != null && reactionGame < globalReactionBest) {
                            globalReactionBest = reactionGame;
                        }
                    }
                }
                //Sets each text view to display the global best scores
                globalMemoryGameScore.setText("Global Memory Game High Score: " + globalMemoryBest);
                globalBalloonGameScore.setText("Global Balloon Game High Score: " + globalBalloonBest);
                globalReactionGameScore.setText("Global Reaction Game Best Time: " + globalReactionBest + "ms");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LeaderBoard", "Failed to read global scores", databaseError.toException());
            }
        });
    }
}
