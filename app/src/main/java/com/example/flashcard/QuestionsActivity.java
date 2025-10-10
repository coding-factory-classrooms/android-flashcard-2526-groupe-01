package com.example.flashcard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class QuestionsActivity extends AppCompatActivity {

    // Variable number of click for next steps
    int numberClickButton = 0;
    // Music Variable
    private MediaPlayer blopyBlopaEasterEggs;
    private MediaPlayer CorrectMediaPlayer;
    private MediaPlayer WrongMediaPlayer;
    private MediaPlayer BresilPlayer;
    private MediaPlayer ItaliePlayer;
    private MediaPlayer EspagnePlayer;
    private MediaPlayer Inde;

    // Variable for EasterEGG
    private int clickCount = 0;

    // ArrayList
    ArrayList<Questions> questionList;
    ArrayList<Questions> wrongAnswersList;

    // Score Text
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


        // Button for surbmit choice On quiz
        Button submitChoiceButtton = findViewById(R.id.submitChoiceButtton);
        // Set visibility hidden
        submitChoiceButtton.setVisibility(View.GONE);

        // Initialise SOUND
        this.CorrectMediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.duolingo_correct);
        this.WrongMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wrong_buzzer);
        this.BresilPlayer = MediaPlayer.create(getApplicationContext(), R.raw.flag_e_brazil);
        this.EspagnePlayer = MediaPlayer.create(getApplicationContext(), R.raw.flag_e_spain);
        this.ItaliePlayer = MediaPlayer.create(getApplicationContext(), R.raw.italie);
        this.Inde = MediaPlayer.create(getApplicationContext(), R.raw.inde);


        // Source for take Questions
        Intent srcIntent = getIntent();
        int selectedDif = srcIntent.getIntExtra("selecteddif", 0);
        int d_logo = srcIntent.getIntExtra("d_logo", R.drawable.d_easy);
        int d_raw = srcIntent.getIntExtra("d_raw", R.raw.e_ee_bb);
        int questionIndex = srcIntent.getIntExtra("questionindex", 0);
        scoreText = getIntent().getIntExtra("scoretext", 0);
        questionList = srcIntent.getParcelableArrayListExtra("questions");
        wrongAnswersList = getIntent().getParcelableArrayListExtra("wrongAnswersList");

        // idk
        if (wrongAnswersList == null) {
            wrongAnswersList = new ArrayList<>();
        }

        // GET index question on questionList
        Questions Q = questionList.get(questionIndex);
        // GET Number text View
        TextView questionNumberTextView = findViewById(R.id.questionNumberTextView);
        // Get question text view
        TextView questionTextView = findViewById(R.id.questionTextView);
        // set text question to text View
        questionTextView.setText(Q.question);
        // idk
        TextView feedbackTextView = findViewById(R.id.feedbackTextView);


        // set difficultuImageView for button img
        ImageButton difficultyImageView = findViewById(R.id.button_image);
        // set img for the button image
        difficultyImageView.setImageResource(d_logo);
        // set text for de question on quiz
        questionNumberTextView.setText("Question " + (questionIndex +1) + "/4");
        // GET difficultyImage Button
        ImageButton difficultyImageButton = findViewById(R.id.difficultyImageButton);
        difficultyImageButton.setImageResource(d_logo);
        // initialise Media Player for play sound on click
        this.blopyBlopaEasterEggs = MediaPlayer.create(getApplicationContext(),d_raw);
        difficultyImageButton.setOnClickListener(view -> blopyBlopaEasterEggs.start());

        // Get CorrectAnswer width the position
        String correctAnswer = Q.answers.get(Q.correctAnswerPosition - 1);
        // shuffle que answers
        Collections.shuffle(Q.answers);

        // set the CorrectAnswerPosition to de aswers indexOF
        Q.correctAnswerPosition = Q.answers.indexOf(correctAnswer) + 1;

        // GET RAdio Group
        RadioGroup group = findViewById(R.id.radio_group);
        // set Padding to the radio Group
        group.setPadding(10,10,10,10);



        // Boucle FOR create RadioButton
        for (int i = 0; i < Q.answers.size(); i++) {

            // 1️ Create radioButton
            RadioButton radioButton = new RadioButton(this);
            // 2 give drawable to the radioButton for get new properties
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(30);
            drawable.setColor(Color.parseColor("#7D4FFE"));
            radioButton.setBackground(drawable);
            radioButton.setPadding(30, 20, 30, 20);

            // 3. SET Layout : maw width and marges
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 10, 0, 10);
            // PUT params to radio button
            radioButton.setLayoutParams(params);

            // SET TEXT to radio button (answers)
            radioButton.setText(Q.answers.get(i));
            // SET TAG for find the position of the radio button
            radioButton.setTag(i + 1);
            radioButton.setTextColor(Color.WHITE);
            radioButton.setTextSize(20);
            // add radioButton to the radioGroup
            group.addView(radioButton);

            // Listener onClick for the radioButton
            radioButton.setOnClickListener(view -> {

                // boucle for take all child on the radio Group
                for (int j = 0; j < group.getChildCount(); j++) {
                    // Chaque child is a View
                    View c = group.getChildAt(j);
                    // caste button for edit
                    RadioButton r = (RadioButton) c;
                        // Chaque bouton a son drawable unique
                        GradientDrawable bg = new GradientDrawable();
                        bg.setCornerRadius(30);
                        // if radio button is checked
                        if (r.isChecked()) {
                            // change bg color and text color
                            bg.setColor(Color.parseColor("#C49FFF"));
                            r.setTextColor(Color.WHITE);
                        } else {
                            // else set this color and Put button VISIBLE
                            bg.setColor(Color.parseColor("#7D4FFE"));
                            r.setTextColor(Color.WHITE);
                            submitChoiceButtton.setVisibility(View.VISIBLE);
                        }
                        r.setBackground(bg);
                }
            });
        }

        // Get the Good answersPosition
        int response = Q.correctAnswerPosition;
        // Lister on button Valider
        submitChoiceButtton.setOnClickListener(view -> {

        numberClickButton++;
        // get group
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        // get id on radioButton
        int checkedId = radioGroup.getCheckedRadioButtonId();
        // recupere get the checked Button width id ^
        RadioButton selectionButton = findViewById(checkedId);
        // get tag of this button for if after
        int responseUser = (Integer)selectionButton.getTag();


          // gone the radioGroup
            radioGroup.setVisibility(View.GONE);
            // if Good answerPosition = tag Checked ( 1, 1)
            if (response == responseUser) {
                feedbackTextView.setText("Bravo ! Bonne réponse !");
                // play sound Correct
                if (numberClickButton <= 1) {
                    CorrectMediaPlayer.start();
                }
            }
            // else say its not good
            else {
                feedbackTextView.setText("Oh non, c'est pas bon ! La bonne réponse était : " + correctAnswer);
                if (numberClickButton <= 1) {
                    WrongMediaPlayer.start();
                }
            }

            if (questionIndex + 1 == questionList.size()) {
                // Changement du texte du button valider en "terminer le quizz"
                submitChoiceButtton.setText("Terminer le quizz !");
            } else {
                // Changement du texte du button valider en "prochaine question"
                submitChoiceButtton.setText("Prochaine question !");
            }


            if (numberClickButton > 1) {
                if (response == responseUser) {
                    scoreText++;
                } else {
                    wrongAnswersList.add(Q);
                }
                // si il appuie 2 fois sur le button il est rediriger vers la 2 eme question
                if (numberClickButton > 1) {
                if (questionIndex + 1 < questionList.size()) {
                    // Encore des questions -> aller à la suivante
                    Intent intent = new Intent(this, QuestionsActivity.class);
                    intent.putParcelableArrayListExtra("questions", questionList);
                    intent.putParcelableArrayListExtra("wrongAnswersList", wrongAnswersList);
                    intent.putExtra("questionindex", questionIndex + 1);
                    intent.putExtra("scoretext", scoreText);
                    intent.putExtra("selecteddif", selectedDif);
                    intent.putExtra("d_logo", d_logo);
                    intent.putExtra("d_raw", d_raw);
                    startActivity(intent);
                    finish();
                } else {
                    // Plus de questions -> fin du quiz
                    Toast.makeText(this, " Bravo ! Vous avez terminé le quiz !", Toast.LENGTH_LONG).show();

                    // rediriger vers les stats de la partie ici
                    Intent intentStats = new Intent(this, StatsActivity.class);
                    intentStats.putParcelableArrayListExtra("wrongAnswersList", wrongAnswersList);
                    intentStats.putExtra("questionindex", questionIndex);
                    intentStats.putExtra("scoretext", scoreText);
                    intentStats.putExtra("selecteddif", selectedDif);
                    startActivity(intentStats);
                    finish();
                }
                }
            }
        });

        ImageButton button_image = findViewById(R.id.button_image);
        button_image.setImageResource(Q.flag);
        button_image.setOnClickListener( view -> {

            // get FLAG and current flag question
            if (Q.flag == R.drawable.flag_e_brazil) {
                // if == play sound
                BresilPlayer.start();
            } else if (Q.flag == R.drawable.flag_e_spain) {
                EspagnePlayer.start();
            } else if (Q.flag == R.drawable.flag_e_italy) {
                ItaliePlayer.start();
            } else if (Q.flag == R.drawable.flag_e_nigeria) {
                Inde.start();
            }
            // if click 5 times on button_image play sound
                clickCount++;
                if (clickCount == 5) {
                    clickCount = 0;
                    Inde.start();
                }
        });
    }
}