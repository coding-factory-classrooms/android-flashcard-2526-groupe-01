package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuestionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         Intent srcIntent = getIntent();
         int selectedDif = srcIntent.getIntExtra("selecteddif", 0);
         int d_logo = srcIntent.getIntExtra("d_logo", R.drawable.d_easy);

        TextView questionNumberTextView = findViewById(R.id.questionNumberTextView);
        ImageView difficultyImageView = findViewById(R.id.difficultyImageView);
        questionNumberTextView.setText(selectedDif + "");
        difficultyImageView.setImageResource(d_logo);
    }
}