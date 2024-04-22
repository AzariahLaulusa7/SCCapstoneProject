package com.example.guarden;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.PowerManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class HomeScreen extends AppCompatActivity {
    private static final String PREF_QUOTE = "quote_pref";
    private static final String KEY_QUOTE = "quote";
    private static final String KEY_LAST_UPDATE_TIME = "last_update_time";
    //List of quotes that can be displayed on the home screen
    private String[] meditationQuotes = {
            "Meditation is the tongue of the soul and the language of our spirit. - Jeremy Taylor",
            "Quiet the mind, and the soul will speak. - Ma Jaya Sati Bhagavati",
            "Meditation brings wisdom; lack of meditation leaves ignorance. Know well what leads you forward and what holds you back. - Buddha",
            "Meditation is not a way of making your mind quiet. It’s a way of entering into the quiet that’s already there. - Deepak Chopra",
            "The thing about meditation is: You become more and more you. - David Lynch",
            "In the midst of movement and chaos, keep stillness inside of you. - Deepak Chopra",
            "Meditation is the ultimate mobile device; you can use it anywhere, anytime, unobtrusively. - Sharon Salzberg",
            "Meditation is a way for nourishing and blossoming the divinity within you. - Amit Ray",
            "Meditation is the secret of all growth in spiritual life and knowledge. - James Allen",
            "Meditation is the discovery that the point of life is always arrived at in the immediate moment. - Alan Watts",
            "Fortune sides with him who dares. ~Virgil",
            "Do one thing every day that scares you. ~Anonymous",
            "You can do anything, but not everything. ~Anonymous",
            "Don't raise your voice, improve your argument. ~Anonymous",
            "Opportunities don't happen, you create them. ~Chris Grosser",
            "If you're going through hell keep going. ~Winston Churchill",
            "No masterpiece was ever created by a lazy artist.~ Anonymous",
            "The starting point of all achievement is desire. ~Napolean Hill",
            "Innovation distinguishes between a leader and a follower. ~Steve Jobs",
            "It's not what you look at that matters, it's what you see. ~Anonymous",
            "All progress takes place outside the comfort zone. ~Michael John Bobak",
            "Failure is the condiment that gives success its flavor. ~Truman Capote",
            "If you want to achieve greatness stop asking for permission. ~Anonymous",
            "Your problem isn't the problem. Your reaction is the problem. ~Anonymous",
            "To live a creative life, we must lose our fear of being wrong. ~Anonymous",
            "No one can make you feel inferior without your consent. ~Eleanor Roosevelt",
            "If you do what you always did, you will get what you always got. ~Anonymous",
            "You may have to fight a battle more than once to win it. ~Margaret Thatcher",
            "The successful warrior is the average man, with laser-like focus. ~Bruce Lee",
            "Don't let what you cannot do interfere with what you can do. ~John R. Wooden",
            "Don't be afraid to give up the good to go for the great. ~John D. Rockefeller",
            "Motivation is what gets you started. Habit is what keeps you going. ~Jim Ryun",
            "I find that the harder I work, the more luck I seem to have. ~Thomas Jefferson",
            "What seems to us as bitter trials are often blessings in disguise.~ Oscar Wilde",
            "All our dreams can come true if we have the courage to pursue them. ~Walt Disney",
            "You must expect great things of yourself before you can do them. ~Michael Jordan",
            "Successful entrepreneurs are givers and not takers of positive energy. ~Anonymous",
            "I have not failed. I've just found 10,000 ways that won't work. ~Thomas A. Edison",
            "Success is the sum of small efforts, repeated day-in and day-out. ~Robert Collier",
            "Courage is resistance to fear, mastery of fear - not absense of fear. ~Mark Twain",
            "People rarely succeed unless they have fun in what they are doing. ~Dale Carnegie",
            "Life is not about finding yourself. Life is about creating yourself. ~Lolly Daskal",
            "It is better to fail in originality than to succeed in imitation. ~Herman Melville",
            "The only place where success comes before work is in the dictionary. ~Vidal Sassoon",
            "Nothing in the world is more common than unsuccessful people with talent. ~Anonymous",
            "A real entrepreneur is somebody who has no safety net underneath them. ~Henry Kravis",
            "Things work out best for those who make the best of how things work out. ~John Wooden",
            "If you can't explain it simply, you don't understand it well enough. ~Albert Einstein",
            "The distance between insanity and genius is measured only by success. ~Bruce Feirstein",
            "The function of leadership is to produce more leaders, not more followers. ~Ralph Nader",
            "Success is liking yourself, liking what you do, and liking how you do it. ~Maya Angelou",
            "The road to success and the road to failure are almost exactly the same. ~Colin R. Davis",
            "Don't let the fear of losing be greater than the excitement of winning. ~Robert Kiyosaki",
            "Success is walking from failure to failure with no loss of enthusiasm. ~Winston Churchill",
            "Blessed are those who can give without remembering and take without forgetting. ~Anonymous",
            "Only put off until tomorrow what you are willing to die having left undone. ~Pablo Picasso",
            "If you are not willing to risk the usual you will have to settle for the ordinary. ~Jim Rohn",
            "Just when the caterpillar thought the world was ending, he turned into a butterfly. ~Proverb",
            "The meaning of life is to find your gift. The purpose of life is to give it away. ~Anonymous",
            "Knowledge is being aware of what you can do. Wisdom is knowing when not to do it. ~Anonymous",
            "As we look ahead into the next century, leaders will be those who empower others. ~Bill Gates",
            "Trust because you are willing to accept the risk, not because it's safe or certain. ~Anonymous",
            "What's the point of being alive if you don't at least try to do something remarkable. ~Anonymous",
            "Be miserable. Or motivate yourself. Whatever has to be done, it's always your choice. ~Wayne Dyer",
            "The ones who are crazy enough to think they can change the world, are the ones that do. ~Anonymous",
            "We become what we think about most of the time, and that's the strangest secret. ~Earl Nightingale",
            "Try not to become a person of success, but rather try to become a person of value. ~Albert Einstein",
            "Little minds are tamed and subdued by misfortune; but great minds rise above it. ~Washington Irving",
            "Whenever you find yourself on the side of the majority, it is time to pause and reflect. ~Mark Twain",
            "When you stop chasing the wrong things you give the right things a chance to catch you. ~Lolly Daskal",
            "If you genuinely want something, don't wait for it -- teach yourself to be impatient. ~Gurbaksh Chahal",
            "Great minds discuss ideas; average minds discuss events; small minds discuss people. ~Eleanor Roosevelt",
            "You may only succeed if you desire succeeding; you may only fail if you do not mind failing. ~Philippos",
            "Good things come to people who wait, but better things come to those who go out and get them. ~Anonymous",
            "Real difficulties can be overcome; it is only the imaginary ones that are unconquerable. ~Theodore N. Vail",
            "The whole secret of a successful life is to find out what is one's destiny to do, and then do it. ~Henry Ford",
            "A successful man is one who can lay a firm foundation with the bricks others have thrown at him. ~David Brinkley",
            "To accomplish great things, we must not only act, but also dream, not only plan, but also believe.~ Anatole France",
            "You've got to get up every morning with determination if you're going to go to bed with satisfaction. ~George Lorimer",
            "Success does not consist in never making mistakes but in never making the same one a second time. ~George Bernard Shaw",
            "Our greatest fear should not be of failure but of succeeding at things in life that don't really matter. ~Francis Chan",
            "Thinking should become your capital asset, no matter whatever ups and downs you come across in your life. ~Dr. APJ Kalam",
            "The number one reason people fail in life is because they listen to their friends, family, and neighbors. ~Napoleon Hill",
            "To be successful you must accept all challenges that come your way. You can't just accept the ones you like. ~Mike Gafka",
            "People often say that motivation doesn't last. Well, neither does bathing - that's why we recommend it daily. ~Zig Ziglar",
            "Develop success from failures. Discouragement and failure are two of the surest stepping stones to success. ~Dale Carnegie",
            "You measure the size of the accomplishment by the obstacles you had to overcome to reach your goals. ~Booker T. Washington",
            "If you don't value your time, neither will others. Stop giving away your time and talents- start charging for it. ~Kim Garst",
            "Whenever you see a successful person you only see the public glories, never the private sacrifices to reach them. ~Vaibhav Shah",
            "Successful people do what unsuccessful people are not willing to do. Don't wish it were easier, wish you were better. ~Jim Rohn",
            "There is no chance, no destiny, no fate, that can hinder or control the firm resolve of a determined soul. ~Ella Wheeler Wilcox",
            "I find that when you have a real interest in life and a curious life, that sleep is not the most important thing. ~Martha Stewart",
            "If you want to achieve excellence, you can get there today. As of this second, quit doing less-than-excellent work. ~Thomas J. Watson",
            "The first step toward success is taken when you refuse to be a captive of the environment in which you first find yourself. ~Mark Caine"
    };
    static String key;
    private static final String PREF_NAME = "PermissionPrefs";
    private static final String PREF_ALARM_PERMISSION_REQUESTED = "alarm_permission_requested";

    private static final int NOTIF_REQUEST_CODE = 123;
    private SharedPreferences prefs;
    //Initializes each button on the home screen
    ImageButton journal;
    ImageButton games;
    ImageButton breath;
    ImageButton forums;

    ImageButton profile;

    ImageButton call;
    ImageButton move;
    ImageButton settings;
    TextView helloText;

    public boolean isAppInForeground;
    ImageButton charts;
    DatabaseReference userRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.background_image);
        BitmapDrawable secondDrawable = (BitmapDrawable) layerDrawable.findDrawableByLayerId(R.id.background_light);
        SharedPreferences sp = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isDarkModeEnabled = sp.getBoolean(Settings.DARK_MODE_KEY, false);//Sets theme to dark if enabled
        if(isDarkModeEnabled) {
            secondDrawable.setAlpha(0);
        } else {
            secondDrawable.setAlpha(255);
        }
        setContentView(R.layout.screen_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        move = (ImageButton) findViewById(R.id.Move);
        journal = (ImageButton) findViewById(R.id.Journal);
        games = (ImageButton) findViewById(R.id.Games);
        breath = (ImageButton) findViewById(R.id.Breath);
        forums = (ImageButton) findViewById(R.id.Forums);
        profile = (ImageButton) findViewById(R.id.Profile);
        call = (ImageButton) findViewById(R.id.Call);
        settings = (ImageButton) findViewById(R.id.Settings);
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        checkNotifPermission();
        charts = (ImageButton) findViewById(R.id.charts);
        userRef = FirebaseDatabase.getInstance().getReference("users");
        updateQuoteIfNeeded();
        displayQuote();
        scheduleAlarm();
        //Checks to see if user is logged in, if so, starts home screen activity
        if(SaveUser.getUserName(HomeScreen.this).length() == 0 && !Login.skipFlag)
        {
            Intent intent = new Intent(HomeScreen.this, Login.class);
            startActivity(intent);
        }
        //If user is not logged in, sets user's name to "Guest" and adds default poses to pose list
        if (Login.skipFlag) {
            if (SaveUser.getUserName(HomeScreen.this).length() == 0) {
                String uniqueKey = userRef.push().getKey();
                String name = "GUEST";
                User newUser = new User("", "", name, "", new ArrayList<>());
                userRef.child(uniqueKey).setValue(newUser)
                        .addOnSuccessListener(aVoid -> {
                            addDefaultPosesToList(uniqueKey);
                        });
                SaveUser.setUserName(HomeScreen.this, uniqueKey);
            }
        }
        //Handles navigation to movement page
        move.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NotificationScheduler.setRecentView("move");
                Intent MoveMain = new Intent(HomeScreen.this, MoveMain.class);
                startActivity(MoveMain);
                finish();
            }
        });

        //Handles navigation to breathe page
        breath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationScheduler.setRecentView("breath");
                Intent BreatheMainActivity = new Intent(HomeScreen.this, BreatheMain.class);
                startActivity(BreatheMainActivity);
                finish();
            }});

        //Handles navigation to tracking page
        charts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, ChartsActivity.class);
                startActivity(intent);
            }
        });

        //Handles navigation to settings page
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent Settings = new Intent(HomeScreen.this, Settings.class);
                startActivity(Settings);
                finish();
            }
        });

        //Handles navigation to profile page
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditProfile = new Intent(HomeScreen.this, EditProfile.class);
                startActivity(EditProfile);
                finish();
            }
        });

        //Handles navigation to journal page
        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationScheduler.setRecentView("journal");
                Intent NewJournalEntry = new Intent(HomeScreen.this, NewJournalEntry.class);
                startActivity(NewJournalEntry);
                finish();
                Intent ViewJournalEntries = new Intent(HomeScreen.this, ViewJournalEntries.class);
                startActivity(ViewJournalEntries);
            }
        });

        //Handles navigation to games page
        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationScheduler.setRecentView("games");
                Intent intent = new Intent(HomeScreen.this, GameHome.class);
                startActivity(intent);
                finish();
            }
        });

        //Handles navigation to forum page
        forums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationScheduler.setRecentView("forums");
                Intent Forums = new Intent(HomeScreen.this, ForumMain.class);
                startActivity(Forums);
                finish();
            }
        });


        //Handles navigation to phone page
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, CrisisLines.class);
                startActivity(intent);
                finish();
            }
        });

        // Edit hello
        helloText = findViewById(R.id.textView2);
        key = SaveUser.getUserName(HomeScreen.this);

        if(key == null)
            key = " ";
        userRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null && user.firstName != null) {
                        helloText.setText("Hello " + user.firstName.toUpperCase() + "!"); //Sets greeting text
                        SaveUser.setName(HomeScreen.this, user.firstName);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }

    //Adds default poses for guest users
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
        public String email, password, firstName, lastName;
        private ArrayList<Pose> customPoses;

        public User() {}

        public User(String email, String password, String firstName, String lastName) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public User(String email, String password, String firstName, String lastName, ArrayList<Pose> customPoses) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.customPoses = customPoses;
        }
    }

    //Requests permission to send the user notifications if it has not been granted
    public void checkNotifPermission() {
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS}, NOTIF_REQUEST_CODE);
            Settings s = new Settings();
            s.notifButton = prefs.getBoolean(Settings.NOTIF_KEY, true);
        } else { //If granted, enable app notifications
            prefs.edit().putBoolean("notif_enabled", true).apply();
            NotificationScheduler ns = new NotificationScheduler(this);
            ns.scheduleRepeatNotif();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIF_REQUEST_CODE) {
            prefs.edit().putBoolean("notif_enabled", grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED).apply();
        }
    }

    //Returns a random quote from the meditationQuotes array to display on the home page
    private String getRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(meditationQuotes.length);
        return meditationQuotes[index];
    }
    void updateQuoteIfNeeded() { //Updates quote every 24 hours
        SharedPreferences prefs = getSharedPreferences(PREF_QUOTE, MODE_PRIVATE);
        long lastUpdateTime = prefs.getLong(KEY_LAST_UPDATE_TIME, 0);
        long currentTime = System.currentTimeMillis();
        long oneDayMillis = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

        if (currentTime - lastUpdateTime >= oneDayMillis) {
            // Update the quote
            String randomQuote = getRandomQuote();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_QUOTE, randomQuote);
            editor.putLong(KEY_LAST_UPDATE_TIME, currentTime);
            editor.apply();
        }
    }

    //Sets textview to the quote text
    void displayQuote() {
        SharedPreferences prefs = getSharedPreferences(PREF_QUOTE, MODE_PRIVATE);
        String randomQuote = prefs.getString(KEY_QUOTE, "");

        TextView textView = findViewById(R.id.textView3);
        textView.setText(randomQuote);
    }

    private void scheduleAlarm() {
        // Create a new PendingIntent for the alarm
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to trigger at midnight every day
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, getMidnight(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    //Identifies when midnight is to reset quotes and daily challenges
    private long getMidnight() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

}
