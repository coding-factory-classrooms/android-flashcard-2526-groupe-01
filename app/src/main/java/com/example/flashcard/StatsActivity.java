package com.example.flashcard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

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

        Button trainingButton = findViewById(R.id.trainingButton);
        Button shareButton = findViewById(R.id.shareButton);
        Button homeButton = findViewById(R.id.homeButton);

        // Get objects
        Intent srcIntent = getIntent();

        int scoreText = getIntent().getIntExtra("scoretext", 0);
        int selectedDif = getIntent().getIntExtra("selecteddif", 0);
        ArrayList<Questions> wrongAnswersList = getIntent().getParcelableArrayListExtra("wrongAnswersList");

        // Calculate and set % progress bar
        float perProgress = (float) scoreText /4 * 100f;
        progressBar.setProgress((int) perProgress);
        String percentageProgress = progressBar.getProgress() + " %";
        percentage.setText(percentageProgress);

        // Set Difficulty Text
        String[] labels = {"Facile", "Moyen", "Difficile", "Hardcore"};
        if (selectedDif >= 0 && selectedDif < labels.length) {
            difficulty.setText("Niveau : " + labels[selectedDif]);
        }

        score.setText(scoreText + " / 4");

        // If user got wrong answers button redirect to a short quizz
        if (wrongAnswersList != null && !wrongAnswersList.isEmpty()) {
            trainingButton.setVisibility(View.VISIBLE);

            trainingButton.setOnClickListener(view -> {
                Intent intentTraining = new Intent(this, QuestionsTrainingActivity.class);
                intentTraining.putParcelableArrayListExtra("wrongAnswersList", wrongAnswersList);
                intentTraining.putExtra("questionindex", 0);
                intentTraining.putExtra("scoretext", 0);
                startActivity(intentTraining);
                finish();
            });

        }
        else {
            trainingButton.setVisibility(View.INVISIBLE);
        }

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