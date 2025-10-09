package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

        Intent srcIntent = getIntent();

        int scoreText = getIntent().getIntExtra("scoretext", 0);
        int diff = srcIntent.getIntExtra("diffScore", 0);
        Toast.makeText(this, "Score au démarrage : " + scoreText, Toast.LENGTH_SHORT).show();


        // Get page components
        TextView score = findViewById(R.id.scoreTextView);
        ProgressBar progressBar = findViewById(R.id.scoreProgressBar);
        TextView percentage = findViewById(R.id.percentageProgressBar);
        TextView difficulty = findViewById(R.id.difficultyLevelTextView);

        Button shareButton = findViewById(R.id.shareButton);
        Button homeButton = findViewById(R.id.homeButton);

        int perProgress = diff/4 * 100;
        progressBar.setProgress(perProgress);
        String percentageProgress = progressBar.getProgress() + " %";
        percentage.setText(percentageProgress);

        String diffi;

        if (diff == 0) {
            diffi = "Easy";
            difficulty.setText("Level " + diffi);
        } else if (diff == 1) {
            diffi = "Medium";
            difficulty.setText("Level " + diffi);
        } else if (diff == 2) {
            diffi = "Hard";
            difficulty.setText("Level " + diffi);
        } else if (diff == 3) {
            diffi = "Super Hard";
            difficulty.setText("Level " + diffi);
        }

        score.setText(scoreText + " / 4");

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        shareButton.setOnClickListener(view -> {

        });

    }
}