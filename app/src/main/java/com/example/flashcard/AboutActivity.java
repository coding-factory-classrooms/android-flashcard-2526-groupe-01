package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Getting informations coming from MainActivity
        Intent srcIntent = getIntent();
        int appTitle = srcIntent.getIntExtra("apptitle", R.drawable.apptitle);
        String appTitleText = srcIntent.getStringExtra("apptitletext");
        String[] creators = srcIntent.getStringArrayExtra("creators");
        String group = srcIntent.getStringExtra("group");
        String version = srcIntent.getStringExtra("version");

        // Setting important informations for the UI
        ImageView titleImageView = findViewById(R.id.titleImageView);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView creatorsTextView = findViewById(R.id.creatorsTextView);
        TextView groupTextView = findViewById(R.id.groupTextView);
        TextView versionTextView = findViewById(R.id.versionTextView);
        titleImageView.setImageResource(appTitle);
        titleTextView.setText(appTitleText);
        creatorsTextView.setText("Créateurs : " +
                creators[0] + ", " +
                creators[1] + ", " +
                creators[2] + ", " +
                creators[3]);
        groupTextView.setText("Groupe : " + group);
        versionTextView.setText("Version : " + version);
    }
}