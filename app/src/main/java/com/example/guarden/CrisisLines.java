package com.example.guarden;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;

public class CrisisLines extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crisis_lines);
        ImageView backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v -> finish());

        Button suicideCrisisButton = findViewById(R.id.button_suicide_crisis);
        Button emergencyServicesButton = findViewById(R.id.button_emergency_services);
        Button crisisTextLineButton = findViewById(R.id.button_crisis_text_line);

        suicideCrisisButton.setOnClickListener(v -> {
            // Intent to dial Suicide & Crisis Lifeline number
            dialPhoneNumber("988");
        });

        emergencyServicesButton.setOnClickListener(v -> {
            // Intent to dial Emergency Services number
            dialPhoneNumber("911");
        });

        crisisTextLineButton.setOnClickListener(v -> {
            // Intent to start a messaging app to text the Crisis Text Line
            sendMessageToNumber("741741");
        });
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private void sendMessageToNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + number));
        startActivity(intent);
    }
}
