package com.example.flashcard;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

        Button oneRadioButton = findViewById(R.id.oneRadioButton);
        Button twoRadioButton = findViewById(R.id.twoRadioButton);
        Button threeRadioButton = findViewById(R.id.threeRadioButton);
        Button fourRadioButton = findViewById(R.id.fourRadioButton);

        // On assigne un tag à chaque bouton
        oneRadioButton.setTag(1);
        twoRadioButton.setTag(2);
        threeRadioButton.setTag(3);
        fourRadioButton.setTag(4);


        // Recupere la bonne reponse
        int response = 2;

        Button submitChoiceButtton = findViewById(R.id.submitChoiceButtton);
        submitChoiceButtton.setOnClickListener(view -> {
            // Recuperer le choix de l'utilisateur
            RadioGroup radioGroup = findViewById(R.id.radio_group);
            int checkedId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectionButton = findViewById(checkedId);
            int responseUser = (Integer)selectionButton.getTag();

            // si le choix de l'utilisateur et de la bonne reponse sont le meme
            // l'utilisateur a trouver passer a la 2eme question
            if (response == responseUser ){
                Log.d("response", "Bien Jouer c'est la bonne reponse");
            }
            // sinon mauvaise reponse / afficher faux et passer a la suite
            else {
                Log.d("nonono", "oh non c'est pas ca hahaahha ");
            }
        });
    }
}