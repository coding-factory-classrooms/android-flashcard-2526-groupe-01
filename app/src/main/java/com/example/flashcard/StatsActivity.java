package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get page components
        TextView score = findViewById(R.id.scoreTextView);
        ProgressBar progressBar = findViewById(R.id.scoreProgressBar);
        TextView percentage = findViewById(R.id.percentageProgressBar);

        Button shareButton = findViewById(R.id.shareButton);
        Button homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        progressBar.setProgress(25);

        String percentageProgress = progressBar.getProgress() + " %";
        percentage.setText(percentageProgress);

    }

    public void manageStats() {

    }
}