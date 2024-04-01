package com.example.guarden;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Random;

public class NotificationService extends IntentService {

    private static final int SCHEDULE_REQUEST_CODE = 123;
    private static final String CHANNEL_ID = "NotificationChannel";
    private static String recentView = ""; // Default to an empty string
    private static final String[] titles1 = {
            "Meditation Reminder",
            "Mindful Moment",
            "Daily Affirmation",
            "Deep Breathing Exercise",
            "Self-Compassion Reminder",
            "Gratitude Reflection",
            "Mindfulness Bell",
            "Body Scan Meditation",
            "Stress Relief Exercise",
            "Serenity Now"
    };

    private static final String[] contents1 = {
            "Take a few moments to pause, breathe, and find stillness within. Your mind and body will thank you.",
            "Engage in a moment of mindfulness. Bring your attention to the present moment and let go of distractions.",
            "You are calm, you are centered, you are at peace. Embrace the present moment with gratitude and mindfulness.",
            "Practice deep breathing exercises to relax your mind and body. Inhale deeply, exhale slowly, and let go of tension.",
            "Be kind to yourself. Allow yourself to rest, recharge, and replenish your energy. You deserve self-compassion.",
            "Take a moment to reflect on things you are grateful for. Cultivate a sense of gratitude and abundance in your life.",
            "Pause for a moment and listen to the sound of the mindfulness bell. Let it remind you to be present and aware.",
            "Practice a body scan meditation to relax your body and release tension. Scan each part of your body with awareness.",
            "Release stress and tension with a guided relaxation exercise. Let go of worries and embrace calmness.",
            "Find serenity in the present moment. Let go of the past, release anxiety about the future, and find peace within."
    };

    private static final String[] titles2 = {
            "Challenge yourself with a memory game",
            "Test your skills with the balloon pop game",
            "Sharpen your reflexes in the reaction game",
            "Join the fun in our mini-games",
            "Play and relax with BreatheEasy games",
            "Enjoy some mental exercise with our games",
            "Beat the high scores in our mini-games",
            "Boost your mood with a quick gaming break",
            "Take a break and play one of our games",
            "Become a master of mindfulness through gaming"
    };

    private static final String[] contents2 = {
            "Exercise your memory and focus with our memory game!",
            "Pop balloons and relieve stress in our balloon pop game!",
            "Test your reaction time and reflexes with our reaction game!",
            "Challenge yourself with our collection of mini-games!",
            "Discover the joy of gaming while staying mindful with BreatheEasy!",
            "Engage your mind and have fun with our selection of games!",
            "Compete with friends and see who can achieve the highest scores!",
            "Elevate your mood and relax with a quick gaming session!",
            "Escape the stresses of the day with a few rounds of gaming!",
            "Combine mindfulness and gaming to enhance your well-being!"
    };

    private static final String[] titles3 = {
            "Take a moment to reflect",
            "Time for your daily journal entry",
            "Capture your thoughts and feelings",
            "Express yourself with words",
            "Journaling time with BreatheEasy",
            "Write down your thoughts for the day",
            "Record your emotions in your journal",
            "Reflect on your day with BreatheEasy",
            "Unleash your creativity in your journal",
            "Document your journey with BreatheEasy"
    };

    private static final String[] contents3 = {
            "Take some time to write about your day and how you're feeling.",
            "Set aside a few moments to journal and reflect on your experiences.",
            "Use your journal to express yourself and process your emotions.",
            "Take a deep breath and let your thoughts flow onto the page.",
            "Journaling can help you gain clarity and insight into your life.",
            "Write down your thoughts, ideas, and aspirations for today.",
            "Capture the moments that matter most in your journal.",
            "Reflect on the challenges and triumphs of the day in your journal.",
            "Let your pen guide you as you explore your thoughts and feelings.",
            "Journaling is a powerful tool for self-discovery and growth."
    };

    private static final String[] titles4 = {
            "Talk to your virtual therapist",
            "Connect with your chatbot counselor",
            "Chat with your BreatheEasy therapist",
            "Schedule a session with your chatbot therapist",
            "Engage in self-reflection with your virtual counselor",
            "Take a moment to chat with your AI therapist",
            "Get support from your BreatheEasy chatbot",
            "Vent your feelings to your virtual therapist",
            "Find comfort and guidance with your chatbot counselor",
            "Speak your mind to your BreatheEasy chatbot therapist"
    };

    private static final String[] contents4 = {
            "Reach out to your virtual therapist for support and guidance.",
            "Schedule a session with your chatbot counselor to discuss your concerns.",
            "Connect with your BreatheEasy therapist to explore your emotions.",
            "Engage in self-reflection and share your thoughts with your virtual counselor.",
            "Take advantage of your chatbot therapist's listening ear and unbiased advice.",
            "Speak freely to your AI therapist about what's on your mind.",
            "Find solace in the supportive conversations with your BreatheEasy chatbot.",
            "Express your feelings and experiences to your virtual therapist.",
            "Receive comfort and guidance from your chatbot counselor.",
            "Share your thoughts and emotions with your BreatheEasy chatbot therapist."
    };

    private static final String[] titles5 = {
            "Calm your mind with BreatheEasy",
            "Take a deep breath and relax",
            "Center yourself with mindful breathing",
            "Find peace with your breath",
            "Restore balance through breathing",
            "Connect with your breath for serenity",
            "Relax and unwind with BreatheEasy",
            "Let your breath guide you to tranquility",
            "Nourish your soul with mindful breaths",
            "Embrace stillness through mindful breathing"
    };

    private static final String[] contents5 = {
            "Take a moment to focus on your breath and quiet your mind.",
            "Inhale deeply and exhale slowly to release tension and stress.",
            "Use mindful breathing to anchor yourself in the present moment.",
            "Let each breath soothe your body and calm your thoughts.",
            "Restore harmony within yourself by tuning into your breath.",
            "Feel the calming rhythm of your breath as you relax your body.",
            "Unwind and let go of worries with the help of mindful breathing.",
            "Allow the gentle rhythm of your breath to bring you peace.",
            "Take a break and nourish your soul with mindful breaths.",
            "Find inner peace and clarity through mindful breathing exercises."
    };
    private static final String[] titles6 = {
            "Get moving with BreatheEasy",
            "Time to stretch and breathe",
            "Enjoy a mindful yoga session",
            "Boost your mood with exercise",
            "Take a break and move your body",
            "Find your flow with BreatheEasy",
            "Release tension with yoga",
            "Energize your day with exercise",
            "Connect with your body through movement",
            "Prioritize your well-being with BreatheEasy"
    };

    private static final String[] contents6 = {
            "Start your day with some gentle stretches and deep breathing.",
            "Join a guided yoga session to relax your mind and body.",
            "Engage in mindful movement to improve your flexibility and strength.",
            "Go for a brisk walk or do a quick workout to elevate your mood.",
            "Take a moment to move and break up your sedentary routine.",
            "Flow through a series of yoga poses to increase your vitality.",
            "Practice yoga to release physical and mental tension.",
            "Exercise is a great way to boost your energy levels and improve your mood.",
            "Listen to your body's needs and engage in movement that feels good.",
            "Make time for self-care and movement to enhance your overall well-being."
    };

    private static final String[] titles7 = {
            "Connect with the BreatheEasy community",
            "Join the conversation on our forums",
            "Engage with like-minded individuals",
            "Share your thoughts and experiences",
            "Discover support and encouragement",
            "Connect with others on their mindfulness journey",
            "Find inspiration and motivation",
            "Explore topics related to mindfulness and well-being",
            "Create meaningful connections with others",
            "Participate in discussions on mental health"
    };

    private static final String[] contents7 = {
            "Join our forums to connect with others who share your mindfulness goals.",
            "Engage in discussions on topics ranging from meditation to self-care.",
            "Share your insights and learn from the experiences of others.",
            "Discover a supportive community where you can express yourself freely.",
            "Find encouragement and support as you navigate your mindfulness journey.",
            "Connect with individuals who are committed to personal growth and well-being.",
            "Be inspired by stories of resilience and transformation shared by our members.",
            "Explore diverse perspectives on mindfulness and holistic living.",
            "Forge genuine connections with fellow members who value mental wellness.",
            "Contribute to a supportive environment where everyone's voice is valued."
    };
    public static String rV;
    public static boolean isAppClosed;
    public static long triggerTimeMillis;
    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isNotifEnabled = prefs.getBoolean("notif_enabled", false);
        boolean switchIsNotifEnabled = prefs.getBoolean("notif", false);
        if (isNotifEnabled && switchIsNotifEnabled) {
            if ("SCHEDULE_NOTIFICATIONS".equals(intent.getAction())) {
                scheduleNotifications();
            } else if ("SHOW_NOTIFICATIONS".equals(intent.getAction())) {
                String title = intent.getStringExtra("title");
                String content = intent.getStringExtra("content");
                int notifid = intent.getIntExtra("notifid", 1001);
                createNotification(notifid, title, content);
            }
        }
    }

    public void scheduleNotifications() {
            long currentTimeMillis = 0;
            long twoHoursInMillis = 0;
            long futureNotif = 0;
            currentTimeMillis = System.currentTimeMillis();
            twoHoursInMillis = 20*1000;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 10);
            calendar.set(Calendar.MILLISECOND, 0);
            futureNotif = calendar.getTimeInMillis();

            // Get the time in milliseconds
            long latest = calendar.getTimeInMillis();

            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            // Get the time in milliseconds
            long earliest = calendar.getTimeInMillis();

            /*
            if (futureNotif > latest) {
                long diff = futureNotif - latest;
                futureNotif = diff % 3600000 + earliest;
            }
            if (futureNotif < earliest) {
                long diff = earliest - futureNotif;
                futureNotif = diff % 3600000 + earliest;
            }

             */

            Random random = new Random();
            int i = random.nextInt(10);

            String title = null;
            String content = null;
            int notifId = 1001;
            //1001 = BASIC
            //1002 = GAMES
            //1003 = JOURNALS
            //1004 = CHATBOT
            //1005 = BREATHING
            //1006 = MOVE
            //1007 = FORUMS
            String recentView = getRecentView();
            Log.d("Test", recentView);
            switch (recentView) {
                case "game_home":
                    notifId = 1002;
                    title = titles2[i];
                    content = contents2[i];
                    break;
                case "content_new_journal_entry":
                    notifId = 1003;
                    title = titles3[i];
                    content = contents3[i];
                    break;
                case "breathe_main":
                    notifId = 1005;
                    title = titles5[i];
                    content = contents5[i];
                    break;
                case "move_main":
                    notifId = 1006;
                    title = titles6[i];
                    content = contents6[i];
                    break;
                case "forum_main":
                    notifId = 1007;
                    title = titles7[i];
                    content = contents7[i];
                    break;
                default:
                    title = titles1[i];
                    content = contents1[i];
                    break;
            }
            /*
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 3);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);\

             */
            long basicNotif = 0;
            basicNotif = calendar.getTimeInMillis();
            /*
            if (futureNotif > (basicNotif - 1 * 60 * 60 * 1000) && futureNotif < basicNotif) {
                futureNotif = basicNotif - 1 * 60 * 60 * 1000;
            }
            if (futureNotif < (basicNotif + 1 * 60 * 60 * 1000) && futureNotif > basicNotif) {
                futureNotif = basicNotif + 1 * 60 * 60 * 1000;
            }
            */
            scheduleNotification(futureNotif, notifId, title, content);
            title = titles1[i];
            content = contents1[i];
            notifId = 1001;
            scheduleNotification(basicNotif, notifId, title, content);
        }
    private void scheduleNotification(long triggerTimeMillis, int notifid, String title, String content) {
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.setAction("SHOW_NOTIFICATIONS");
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("notifid", notifid);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            if (alarmManager.canScheduleExactAlarms()) {
                if(notifid!=1001){
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTimeMillis, pendingIntent);
                } else{
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTimeMillis, pendingIntent);
                }
            } else {
                Intent settings = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                settings.setData(android.net.Uri.parse("package:" + this.getPackageName()));
                settings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(settings);
            }
    }


    public void createNotification(int notifId, String title, String message) {
        Intent notifIntent = new Intent(this, HomeScreen.class);
        notifIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifIntent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, NotificationService.class);
            intent.setAction("SCHEDULE_NOTIFICATIONS");
            startService(intent);
        }
        if (Apptivity.isAppClosed) {
            notificationManager.notify(notifId, builder.build());
        }
    }

    public static void setRecentView(String view) {
        recentView = view;
    }

    private String getRecentView() {
        return recentView;
    }
}
