package com.example.flashcard;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

            // Here is the logic for the choice of the difficulty by a dialog box
            String[] difficulties = {"Facile", "Moyen", "Difficile", "Hardcore"};

            AlertDialog.Builder difficultyChoiceDialog = new AlertDialog.Builder(this);
            difficultyChoiceDialog
                    .setTitle("Choisis une difficulté")
                    .setSingleChoiceItems(difficulties, 0, (dialog, which) -> {
                        int selectedDif = which; // 0 = Easy, 1 = Medium, 2 = Hard, 3 = hardcore
                        Intent intent = new Intent(this, QuestionsActivity.class);
                        intent.putExtra("selecteddif", selectedDif);
                        intent.putExtra("d_logo", setDifficultyLogo(selectedDif));
                        intent.putExtra("d_raw", setDifficultyRaw(selectedDif));
                        questionsInitialization(selectedDif, intent);
                        startActivity(intent);
                        dialog.dismiss();

                    });
            AlertDialog dialog = difficultyChoiceDialog.create();
            dialog.show();


        } else if (id == R.id.questionsListButton) {

            // go to questionsListActivity - Here is the logic for the transition to the questions list page
            Intent intent = new Intent(this, QuestionsListActivity.class);
            startActivity(intent);

        } else if (id == R.id.statButton) {

            // go to statActivity - Here is the logic for the transition to the stat page
//            Intent intent = new Intent(this, StatsActivity.class);
//            startActivity(intent);

        } else if (id == R.id.aboutButton) {

            // go to aboutActivity
            Intent intent = new Intent(this, AboutActivity.class);
            intent
                    .putExtra("apptitle", appTitle)
                    .putExtra("apptitletext", "Guess the flag")
                    .putExtra("creatorI", "Draxan LT")
                    .putExtra("creatorII", "Matias D")
                    .putExtra("creatorIII", "Strauss A")
                    .putExtra("creatorIV", "Romain P")
                    .putExtra("group", "01")
                    .putExtra("version", getAppVersion());
            startActivity(intent);
        }
    }

    private String getAppVersion(){
        String version = "";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("ERROR", "error while getting the app version");
            version = "Error";
        }
        return version;
    }

    private int setDifficultyLogo(int selectedDif) {
        int d_logo = 0;
        if (selectedDif == 0) {
            d_logo = R.drawable.d_easy;
        } else if (selectedDif == 1) {
            d_logo = R.drawable.d_medium;
        } else if (selectedDif == 2) {
            d_logo = R.drawable.d_hard;
        } else if (selectedDif == 3) {
            d_logo = R.drawable.d_hardcore;
        }
        return d_logo;
    }

    private int setDifficultyRaw(int selectedDif) {
        int d_raw = 0;
        if (selectedDif == 0) {
            d_raw = R.raw.e_ee_bb;
        } else if (selectedDif == 1) {
            d_raw = R.raw.m_ee_bb;
        } else if (selectedDif == 2) {
            d_raw = R.raw.h_ee_bb;
        } else if (selectedDif == 3) {
            d_raw = R.raw.hc_ee_bb;
        }
        return d_raw;
    }

    public void questionsInitialization(int selectedDif, Intent intent) {
        int questionIndex = 0;
        ArrayList<Questions> questions = new ArrayList<>();
        if (selectedDif == 0) {

            // Questions for the easy difficulty
            List<String> answers1 = new ArrayList<>();
            answers1.add("Brésil");
            answers1.add("Chine");
            answers1.add("France");
            answers1.add("Allemagne");
            List<String> answers2 = new ArrayList<>();
            answers2.add("Nigéria");
            answers2.add("Italie");
            answers2.add("Japon");
            answers2.add("Russie");
            List<String> answers3 = new ArrayList<>();
            answers3.add("Espagne");
            answers3.add("Royaume-Unis");
            answers3.add("Ukraine");
            answers3.add("Etats-Unis");
            List<String> answers4 = new ArrayList<>();
            answers4.add("Italie");
            answers4.add("Chine");
            answers4.add("Ukraine");
            answers4.add("Russie");
            Questions EQ1 = new Questions("Quel est le pays de ce drapeau ?", answers1, 1, R.drawable.flag_e_brazil, R.raw.duolingo_correct, "easy");
            Questions EQ2 = new Questions("Quel est le pays de ce drapeau ?", answers2, 1, R.drawable.flag_e_nigeria, R.raw.duolingo_correct, "easy");
            Questions EQ3 = new Questions("Quel est le pays de ce drapeau ?", answers3, 1, R.drawable.flag_e_spain, R.raw.duolingo_correct, "easy");
            Questions EQ4 = new Questions("Quel est le pays de ce drapeau ?", answers4, 1, R.drawable.flag_e_italy, R.raw.duolingo_correct, "easy");
            questions.add(EQ1);
            questions.add(EQ2);
            questions.add(EQ3);
            questions.add(EQ4);

        } else if (selectedDif == 1) {

            // Questions for the medium difficulty
            List<String> answers1 = new ArrayList<>();
            answers1.add("Ethiopie");
            answers1.add("Colombie");
            answers1.add("Egypte");
            answers1.add("Finlande");
            List<String> answers2 = new ArrayList<>();
            answers2.add("Inde");
            answers2.add("Hongrie");
            answers2.add("Kenya");
            answers2.add("Lithuanie");
            List<String> answers3 = new ArrayList<>();
            answers3.add("Portugal");
            answers3.add("Philippines");
            answers3.add("Suède");
            answers3.add("Thailande");
            List<String> answers4 = new ArrayList<>();
            answers4.add("Kenya");
            answers4.add("Portugal");
            answers4.add("Colombie");
            answers4.add("Suède");
            Questions MQ1 = new Questions("Quel est le pays de ce drapeau ?", answers1, 1, R.drawable.flag_n_ethiopia, R.raw.duolingo_correct, "medium");
            Questions MQ2 = new Questions("Quel est le pays de ce drapeau ?", answers2, 1, R.drawable.flag_n_india, R.raw.duolingo_correct, "medium");
            Questions MQ3 = new Questions("Quel est le pays de ce drapeau ?", answers3, 1, R.drawable.flag_n_portugal, R.raw.duolingo_correct, "medium");
            Questions MQ4 = new Questions("Quel est le pays de ce drapeau ?", answers4, 1, R.drawable.flag_n_kenya, R.raw.duolingo_correct, "medium");
            questions.add(MQ1);
            questions.add(MQ2);
            questions.add(MQ3);
            questions.add(MQ4);

        } else if (selectedDif == 2) {

            // Questions for the hard difficulty
            List<String> answers1 = new ArrayList<>();
            answers1.add("Érythrée");
            answers1.add("Bosnie-Herzégovine");
            answers1.add("Cape Vert");
            answers1.add("Libye");
            List<String> answers2 = new ArrayList<>();
            answers2.add("Malawi");
            answers2.add("Mongolie");
            answers2.add("Mozambique");
            answers2.add("Népal");
            List<String> answers3 = new ArrayList<>();
            answers3.add("Oman");
            answers3.add("Seychelles");
            answers3.add("Suriname");
            answers3.add("Ouzbékistan");
            List<String> answers4 = new ArrayList<>();
            answers4.add("Mozambique");
            answers4.add("Ouzbékistan");
            answers4.add("Suriname");
            answers4.add("Libye");
            Questions HQ1 = new Questions("Quel est le pays de ce drapeau ?", answers1, 1, R.drawable.flag_h_eritrea, R.raw.duolingo_correct, "hard");
            Questions HQ2 = new Questions("Quel est le pays de ce drapeau ?", answers2, 1, R.drawable.flag_h_malawi, R.raw.duolingo_correct, "hard");
            Questions HQ3 = new Questions("Quel est le pays de ce drapeau ?", answers3, 1, R.drawable.flag_h_oman, R.raw.duolingo_correct, "hard");
            Questions HQ4 = new Questions("Quel est le pays de ce drapeau ?", answers4, 1, R.drawable.flag_h_mozambique, R.raw.duolingo_correct, "hard");
            questions.add(HQ1);
            questions.add(HQ2);
            questions.add(HQ3);
            questions.add(HQ4);

        } else if (selectedDif == 3) {

            // Questions for the hardcore difficulty
            List<String> answers1 = new ArrayList<>();
            answers1.add("Hawaii");
            answers1.add("Ohio");
            answers1.add("Maine");
            answers1.add("Washington");
            List<String> answers2 = new ArrayList<>();
            answers2.add("Somaliland");
            answers2.add("Abkhazie");
            answers2.add("Kosovo");
            answers2.add("République arabe sahraouie démocratique");
            List<String> answers3 = new ArrayList<>();
            answers3.add("Empire Allemand");
            answers3.add("Empire Autrichien");
            answers3.add("République des deux nations");
            answers3.add("Empire Ottoman");
            List<String> answers4 = new ArrayList<>();
            answers4.add("Saint-Pierre-et-Miquelon");
            answers4.add("Normandie");
            answers4.add("Bretagne");
            answers4.add("Pays basque français");
            Questions HCQ1 = new Questions("Quel est le drapeau de cette état américain ?", answers1, 1, R.drawable.flag_vh_hawaii, R.raw.duolingo_correct, "hardcore");
            Questions HCQ2 = new Questions("Quel est ce pays non reconnue par l'ONU ?", answers2, 1, R.drawable.flag_vh_somaliland, R.raw.duolingo_correct, "hardcore");
            Questions HCQ3 = new Questions("Quel est ce pays aujourd'hui disparue ?", answers3, 1, R.drawable.flag_vh_german_empire, R.raw.duolingo_correct, "hardcore");
            Questions HCQ4 = new Questions("Quel est le territoire français de ce drapeau ?", answers4, 1, R.drawable.flag_vh_saint_pierre_and_miquelon, R.raw.duolingo_correct, "hardcore");
            questions.add(HCQ1);
            questions.add(HCQ2);
            questions.add(HCQ3);
            questions.add(HCQ4);

        }
        Collections.shuffle(questions); // Randomize the order of questions
        intent.putParcelableArrayListExtra("questions", questions);
        intent.putExtra("questionindex", questionIndex);
    }
}