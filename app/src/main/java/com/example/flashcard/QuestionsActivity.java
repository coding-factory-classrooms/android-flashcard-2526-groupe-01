package com.example.flashcard;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionsActivity extends AppCompatActivity {

    // Variable  du nombre de click pour le button Valider
    int numberClickButton = 0;
    private MediaPlayer blopyBlopaEasterEggs;
    private MediaPlayer CorrectMediaPlayer;
    private MediaPlayer WrongMediaPlayer;
    ArrayList<Questions> questionList;

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
        // Ajouter le son Bonne reponse
        this.CorrectMediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.duolingo_correct);
        // Ajouter le son mauvaise reponse
        this.WrongMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wrong_buzzer);



        Intent srcIntent = getIntent();
        int selectedDif = srcIntent.getIntExtra("selecteddif", 0);
        int d_logo = srcIntent.getIntExtra("d_logo", R.drawable.d_easy);
        int d_raw = srcIntent.getIntExtra("d_raw", R.raw.e_ee_bb);
        int questionIndex = srcIntent.getIntExtra("questionindex", 0);
        questionList = srcIntent.getParcelableArrayListExtra("questions");

        TextView questionNumberTextView = findViewById(R.id.questionNumberTextView);
        ImageButton difficultyImageButton = findViewById(R.id.difficultyImageButton);
        difficultyImageButton.setImageResource(d_logo);
        this.blopyBlopaEasterEggs = MediaPlayer.create(getApplicationContext(),d_raw);
        difficultyImageButton.setOnClickListener(view -> blopyBlopaEasterEggs.start());


        Questions Q = questionList.get(questionIndex);

        String correctAnswer = Q.answers.get(Q.correctAnswerPosition - 1);

        Collections.shuffle(Q.answers);

        // À mettre dans la boucle for en dessous (je crois :D)
//        String answerI = Q.answers.get(0);
//        String answerII = Q.answers.get(1);
//        String answerIII = Q.answers.get(2);
//        String answerIV = Q.answers.get(3);

        Q.correctAnswerPosition = Q.answers.indexOf(correctAnswer) + 1;

        RadioGroup group = findViewById(R.id.radio_group);

    // création d'un button pour chaque reponse
        for (int i = 0; i < Q.answers.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(Q.answers.get(i));
            radioButton.setTag(i + 1);
            radioButton.setPadding(8, 8, 8, 8);
            group.addView(radioButton);
        }
//        for (int i = 0; i < Q.answers.size(); i++) {
//            // Instancier un RadioButton
//
//            int id = View.generateViewId();
//
//            // ajouter ce rb a ton radiogroup
//        }

        // À mettre dans la boucle for aussi (je crois :D)
//        Button oneRadioButton = findViewById(R.id.oneRadioButton);
//        oneRadioButton.setText(answerI);
//        Button twoRadioButton = findViewById(R.id.twoRadioButton);
//        twoRadioButton.setText(answerII);
//        Button threeRadioButton = findViewById(R.id.threeRadioButton);
//        threeRadioButton.setText(answerIII);
//        Button fourRadioButton = findViewById(R.id.fourRadioButton);
//        fourRadioButton.setText(answerIV);
//
//        // On assigne un tag à chaque bouton
//        oneRadioButton.setTag(1);
//        twoRadioButton.setTag(2);
//        threeRadioButton.setTag(3);
//        fourRadioButton.setTag(4);



        // Recupere la bonne reponse
        int response = Q.correctAnswerPosition;


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
            if (response == responseUser){
                Toast.makeText(this, "Bravo bonne reponse", Toast.LENGTH_SHORT).show();
                CorrectMediaPlayer.start();
            }
            // sinon mauvaise reponse / afficher faux et passer a la suite
            else {
                Toast.makeText(this, "Oh non c'est pas bon, la bonne reponse etait : " + response, Toast.LENGTH_SHORT).show();
                WrongMediaPlayer.start();
            }
            // Changement du text du button valider en "prochaine question"
            Q.answered = true;
            submitChoiceButtton.setText("Prochaine question !");


            // si il appuie 2 fois sur le button il est rediriger vers la 2 eme question
            if (numberClickButton > 1) {
                if (questionIndex + 1 < questionList.size()) {
                    // Encore des questions -> aller à la suivante
                    Intent intent = new Intent(this, QuestionsActivity.class);
                    intent.putParcelableArrayListExtra("questions", questionList);
                    intent.putExtra("questionindex", questionIndex + 1);
                    startActivity(intent);
                    finish();
                } else {
                    // Plus de questions -> fin du quiz
                    Toast.makeText(this, " Bravo ! Vous avez terminé le quiz !", Toast.LENGTH_LONG).show();

                    // rediriger vers les stat de la partie ici
                     startActivity(new Intent(this, MainActivity.class));
                     finish();
                }
            }
        });
    }
}