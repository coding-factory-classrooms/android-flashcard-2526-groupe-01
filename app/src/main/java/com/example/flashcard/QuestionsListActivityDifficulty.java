package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuestionsListActivityDifficulty extends AppCompatActivity implements View.OnClickListener {

    private Button tempoQuestionListButton;

    /*private Button easyQuestionListButton;

    private Button normalQuestionListButton;

    private Button hardQuestionListButton;

    private Button hardcoreQuestionListButton;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questions_list_difficulty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tempoQuestionListButton = findViewById(R.id.tempoQuestionListButton);
/*
        easyQuestionListButton = findViewById(R.id.easyQuestionListButton);
        normalQuestionListButton = findViewById(R.id.normalQuestionListButton);
        hardQuestionListButton = findViewById(R.id.hardQuestionListButton);
        hardcoreQuestionListButton = findViewById(R.id.hardcoreQuestionListButton);*/

        tempoQuestionListButton.setOnClickListener(this);
/*
        easyQuestionListButton.setOnClickListener(this);
        normalQuestionListButton.setOnClickListener(this);
        hardQuestionListButton.setOnClickListener(this);
        hardcoreQuestionListButton.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tempoQuestionListButton){
            Intent intent = new Intent(this,QuestionsListActivity.class);
            startActivity(intent);
        }
        /*else if (id == R.id.easyQuestionListButton) {
            Intent intent = new Intent(this, easyQuestionListButton.class);
            startActivity(intent);

        } else if (id == R.id.normalQuestionListButton) {
            Intent intent = new Intent(this, normalQuestionListButton.class);
            startActivity(intent);

        } else if (id == R.id.hardQuestionListButton) {
            Intent intent = new Intent(this, hardQuestionListButton.class);
            startActivity(intent);

        }else if (id == R.id.hardcoreQuestionListButton) {
            Intent intent = new Intent(this, hardcoreQuestionListButton.class);
            startActivity(intent);
        }*/
    }
}