package com.example.flashcard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

        // Get xml components
        TextView score = findViewById(R.id.scoreTextView);
        ProgressBar progressBar = findViewById(R.id.scoreProgressBar);
        TextView percentage = findViewById(R.id.percentageProgressBar);
        TextView difficulty = findViewById(R.id.difficultyLevelTextView);

        Button shareButton = findViewById(R.id.shareButton);
        Button homeButton = findViewById(R.id.homeButton);

        // Get objects
        Intent srcIntent = getIntent();

        int scoreText = getIntent().getIntExtra("scoretext", 0);
        int diff = getIntent().getIntExtra("diffScore", 0);

        // Calculate and set % progress bar
        float perProgress = (float) scoreText /4 * 100f;
        progressBar.setProgress((int) perProgress);
        String percentageProgress = progressBar.getProgress() + " %";
        percentage.setText(percentageProgress);

        String diffi;

        // Set Difficulty Text
        if (diff == 0) {
            diffi = "Facile";
            difficulty.setText("Niveau " + diffi);
        } else if (diff == 1) {
            diffi = "Moyen";
            difficulty.setText("Niveau " + diffi);
        } else if (diff == 2) {
            diffi = "Difficile";
            difficulty.setText("Niveau " + diffi);
        } else if (diff == 3) {
            diffi = "Hardcore";
            difficulty.setText("Niveau " + diffi);
        }

        score.setText(scoreText + " / 4");

        // Instance created when share button clicked
        shareButton.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "J'ai eu " + scoreText + " / 4 sur Guess the Flag !!");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        // Home redirection when button clicked
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }
}