package com.example.flashcard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuestionsActivity extends AppCompatActivity {

    // Variable  du nombre de click pour le button Valider
    int numberClickButton = 0;
    private MediaPlayer blopyBlopaEasterEggs;
    private MediaPlayer CorrectMediaPlayer;
    private MediaPlayer WrongMediaPlayer;
    ArrayList<Questions> questionList;

    int scoreText;


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
        Questions Q = questionList.get(questionIndex);

        TextView questionNumberTextView = findViewById(R.id.questionNumberTextView);
        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(Q.question);
        TextView feedbackTextView = findViewById(R.id.feedbackTextView);

        ImageButton difficultyImageView = findViewById(R.id.button_image);
        difficultyImageView.setImageResource(d_logo);
        questionNumberTextView.setText("Question " + (questionIndex +1) + "/4");
        ImageButton difficultyImageButton = findViewById(R.id.difficultyImageButton);
        difficultyImageButton.setImageResource(d_logo);
        this.blopyBlopaEasterEggs = MediaPlayer.create(getApplicationContext(),d_raw);
        difficultyImageButton.setOnClickListener(view -> blopyBlopaEasterEggs.start());


        String correctAnswer = Q.answers.get(Q.correctAnswerPosition - 1);

        Collections.shuffle(Q.answers);

        Q.correctAnswerPosition = Q.answers.indexOf(correctAnswer) + 1;
        RadioGroup group = findViewById(R.id.radio_group);
        group.setPadding(10,10,10,10);
    // création d'un button pour chaque reponse
        for (int i = 0; i < Q.answers.size(); i++) {

            // 1️⃣ Création du RadioButton
            RadioButton radioButton = new RadioButton(this);


            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(30);
            drawable.setColor(Color.parseColor("#7D4FFE"));
            radioButton.setBackground(drawable);
            radioButton.setPadding(30, 20, 30, 20);

            // Layout : largeur pleine et les marge
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 10, 0, 10);
            radioButton.setLayoutParams(params);

            // Texte
            radioButton.setText(Q.answers.get(i));
            radioButton.setTag(i + 1);
            radioButton.setTextColor(Color.WHITE);
            radioButton.setTextSize(20);
            group.addView(radioButton);

            // Listener pour gérer la sélection
            radioButton.setOnClickListener(view -> {
                for (int j = 0; j < group.getChildCount(); j++) {
                    View c = group.getChildAt(j);
                    if (c instanceof RadioButton) {
                        RadioButton r = (RadioButton) c;

                        // Chaque bouton a son drawable unique
                        //evite que les buttons change tous de couleur en meme temps
                        GradientDrawable bg = new GradientDrawable();
                        bg.setCornerRadius(30);

                        if (r.isChecked()) {
                            bg.setColor(Color.parseColor("#C49FFF"));
                            r.setTextColor(Color.WHITE);
                        } else {
                            bg.setColor(Color.parseColor("#7D4FFE"));
                            r.setTextColor(Color.WHITE);
                        }
                        r.setBackground(bg);
                    }
                }
            });
        }

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
            radioGroup.setVisibility(View.GONE);
            if (response == responseUser){

                scoreText++;
                feedbackTextView.setText("Bravo ! Bonne réponse !");
                if (numberClickButton <= 1) {
                    CorrectMediaPlayer.start();
                }
            }
            // sinon mauvaise reponse / afficher faux et passer a la suite
            else {
                feedbackTextView.setText("Oh non, c'est pas bon ! La bonne réponse était : " + correctAnswer);
                if (numberClickButton <= 1) {
                    WrongMediaPlayer.start();
                }
            }
            // Changement du text du button valider en "prochaine question"
            Q.answered = true;
            submitChoiceButtton.setText("Prochaine question !");

            // si il appuie 2 fois sur le button il est rediriger vers la 2 eme question
            if (numberClickButton > 1) {
                if (questionIndex + 1 <= questionList.size()) {
//                    questionNumberTextView.setText("Question " + indexText++ + "/4");
                    // Encore des questions -> aller à la suivante
                    Intent intent = new Intent(this, QuestionsActivity.class);
                    intent.putParcelableArrayListExtra("questions", questionList);
                    intent.putExtra("questionindex", questionIndex + 1);
                    intent.putExtra("scoree", scoreText);
                    startActivity(intent);
                    finish();
                } else {
                    // Plus de questions -> fin du quiz
                    Toast.makeText(this, " Bravo ! Vous avez terminé le quiz !", Toast.LENGTH_LONG).show();

                    // rediriger vers les stats de la partie ici
                    Intent intent = new Intent(this, StatsActivity.class);
                    int scoresrc = scoreText;
                    intent.putExtra("difficultyText", selectedDif);
                    intent.putExtra("scoree", scoresrc);
                    startActivity(intent);
                    finish();
                }
            }
        });


        ImageButton button_image = findViewById(R.id.button_image);
        button_image.setImageResource(Q.flag);
        button_image.setOnClickListener( view -> {
            WrongMediaPlayer.start();
        });
    }
}