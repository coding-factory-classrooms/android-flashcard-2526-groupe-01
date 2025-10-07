package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    // All buttons in the main activity/launch page
    private Button startButton;
    private Button questionsListButton;
    private Button statButton;
    private Button aboutButton;

    // All variables for Extras
    private int appTitle = R.drawable.apptitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView titleImageView = findViewById(R.id.titleImageView);
        titleImageView.setImageResource(appTitle);

        // Here is where we're linking our variables "Button" to their equivalents in activity_main.xml
        startButton = findViewById(R.id.startButton);
        questionsListButton = findViewById(R.id.questionsListButton);
        statButton = findViewById(R.id.statButton);
        aboutButton = findViewById(R.id.aboutButton);

        // Heart of the activity
        startButton.setOnClickListener(this);
        questionsListButton.setOnClickListener(this);
        statButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.startButton) {
            // choose a difficulty
        } else if (id == R.id.questionsListButton) {
            // go to questionsListActivity
            Intent intent = new Intent(this, QuestionsListActivity.class);
            startActivity(intent);
        } else if (id == R.id.statButton) {
            // go to statActivity
            Intent intent = new Intent(this, StatActivity.class);
            startActivity(intent);
        } else if (id == R.id.aboutButton) {
            // go to aboutActivity
            Intent intent = new Intent(this, AboutActivity.class);
            intent.putExtra("apptitle", appTitle);
            intent.putExtra("apptitletext", "Guess the flag");
            intent.putExtra("creatorI", "Draxan LT");
            intent.putExtra("creatorII", "Matias D");
            intent.putExtra("creatorIII", "Strauss A");
            intent.putExtra("creatorIV", "Romain P");
            intent.putExtra("group", "01");
            intent.putExtra("version", "prototype 1");
            startActivity(intent);
        }
    }
}