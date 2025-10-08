package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.atomic.AtomicInteger;

public class QuestionsActivity extends AppCompatActivity {

    // Variable  du nombre de click pour le button Valider
    int numberClickButton = 0;

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
        difficultyImageView.setImageResource(d_logo);

        Button oneRadioButton = findViewById(R.id.oneRadioButton);
        oneRadioButton.setText("France");
        Button twoRadioButton = findViewById(R.id.twoRadioButton);
        twoRadioButton.setText("Inde");
        Button threeRadioButton = findViewById(R.id.threeRadioButton);
        threeRadioButton.setText("Chine");
        Button fourRadioButton = findViewById(R.id.fourRadioButton);
        fourRadioButton.setText("Pays-bas");

        // On assigne un tag à chaque bouton
        oneRadioButton.setTag(1);
        twoRadioButton.setTag(2);
        threeRadioButton.setTag(3);
        fourRadioButton.setTag(4);


        // Recupere la bonne reponse
        int response = 2;


        Button submitChoiceButtton = findViewById(R.id.submitChoiceButtton);
        submitChoiceButtton.setOnClickListener(view -> {

           numberClickButton++;

            // Recuperer le choix de l'utilisateur
            RadioGroup radioGroup = findViewById(R.id.radio_group);
            int checkedId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectionButton = findViewById(checkedId);
            int responseUser = (Integer)selectionButton.getTag();

            // si le choix de l'utilisateur et de la bonne reponse sont le meme
            // l'utilisateur a trouver passer a la 2eme question
            if (response == responseUser ){
                Toast.makeText(this, "Bravo bonne reponse", Toast.LENGTH_SHORT).show();

            }
            // sinon mauvaise reponse / afficher faux et passer a la suite
            else {
                Toast.makeText(this, "Oh non c'est pas bon, la bonne reponse etait : " + response, Toast.LENGTH_SHORT).show();
            }
            // Changement du text du button valider en "prochaine question"
            submitChoiceButtton.setText("Prochaine question !");


            // si il appuie 2 fois sur le button il est rediriger vers la 2 eme question
            if (numberClickButton > 1){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }






        });
    }
}