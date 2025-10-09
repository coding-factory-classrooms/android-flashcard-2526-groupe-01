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
        TextView difficulty = findViewById(R.id.difficultyLevelTextView);

        Button shareButton = findViewById(R.id.shareButton);
        Button homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        Intent srcIntent = getIntent();

        int scr = srcIntent.getIntExtra("scoree", 0);
        int diff = srcIntent.getIntExtra("difficultyText", 0);

        int perProgress = diff/4 * 100;
        progressBar.setProgress(perProgress);
        String percentageProgress = progressBar.getProgress() + " %";
        percentage.setText(percentageProgress);



        if (diff == 0) {
            String diffi = "Easy";
            difficulty.setText("Level " + diffi);
        }
        if (diff == 1) {
            String diffi = "Medium";
            difficulty.setText("Level " + diffi);
        }
        if (diff == 2) {
            String diffi = "Hard";
            difficulty.setText("Level " + diffi);
        }
        if (diff == 3) {
            String diffi = "Super Hard";
            difficulty.setText("Level " + diffi);
        }

        score.setText(scr + " / 4");


    }
}