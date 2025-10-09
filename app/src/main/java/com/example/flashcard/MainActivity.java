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
    private Button questionsListDifficultyButton;
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
        questionsListDifficultyButton = findViewById(R.id.questionsListDifficultyButton);
        statButton = findViewById(R.id.statButton);
        aboutButton = findViewById(R.id.aboutButton);

        // Heart of the activity
        startButton.setOnClickListener(this);
        questionsListDifficultyButton.setOnClickListener(this);
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


        } else if (id == R.id.questionsListDifficultyButton) {

            // go to questionsListActivity - Here is the logic for the transition to the questions list page
            Intent intent = new Intent(this, QuestionsListActivityDifficulty.class);
            startActivity(intent);

        } else if (id == R.id.statButton) {

            // go to statActivity - Here is the logic for the transition to the stat page
            Intent intent = new Intent(this, StatsActivity.class);
            startActivity(intent);

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
        Intent intentStats = new Intent(this, StatsActivity.class);
        int d_raw = 0;
        if (selectedDif == 0) {
            d_raw = R.raw.e_ee_bb;
            intentStats.putExtra("diffScore", selectedDif);
        } else if (selectedDif == 1) {
            d_raw = R.raw.m_ee_bb;
            intentStats.putExtra("diffScore", selectedDif);
        } else if (selectedDif == 2) {
            d_raw = R.raw.h_ee_bb;
            intentStats.putExtra("diffScore", selectedDif);
        } else if (selectedDif == 3) {
            d_raw = R.raw.hc_ee_bb;
            intentStats.putExtra("diffScore", selectedDif);
        }
        startActivity(intentStats);
        return d_raw;
    }

    public void questionsInitialization(int selectedDif, Intent intent) {
        int questionIndex = 0;
        ArrayList<Questions> questions = new ArrayList<>();
        if (selectedDif == 0) {

            // Questions for the easy difficulty
            List<String> answers1 = new ArrayList<>();
            answers1.add("Brasil");
            answers1.add("Chine");
            answers1.add("France");
            answers1.add("Allemagne");
            List<String> answers2 = new ArrayList<>();
            answers2.add("Italie");
            answers2.add("Japon");
            answers2.add("Nigéria");
            answers2.add("Russie");
            List<String> answers3 = new ArrayList<>();
            answers3.add("Espagne");
            answers3.add("Royaume-Unis");
            answers3.add("Ukraine");
            answers3.add("Etats-Unis");
            List<String> answers4 = new ArrayList<>();
            answers4.add("Chine");
            answers4.add("Italie");
            answers4.add("Ukraine");
            answers4.add("Russie");
            Questions EQ1 = new Questions("Quel est le pays de ce drapeau ?", answers1, 1, R.drawable.flag_e_brazil, false);
            Questions EQ2 = new Questions("Quel est le pays de ce drapeau ?", answers2, 3, R.drawable.flag_e_nigeria, false);
            Questions EQ3 = new Questions("Quel est le pays de ce drapeau ?", answers3, 1, R.drawable.flag_e_spain, false);
            Questions EQ4 = new Questions("Quel est le pays de ce drapeau ?", answers4, 2, R.drawable.flag_e_italy, false);
            questions.add(EQ1);
            questions.add(EQ2);
            questions.add(EQ3);
            questions.add(EQ4);

        } else if (selectedDif == 1) {

            // Questions for the medium difficulty
            List<String> answers1 = new ArrayList<>();
            answers1.add("Colombie");
            answers1.add("Egypte");
            answers1.add("Ethiopie");
            answers1.add("Finlande");
            List<String> answers2 = new ArrayList<>();
            answers2.add("Hongrie");
            answers2.add("Inde");
            answers2.add("Kenya");
            answers2.add("Lithuanie");
            List<String> answers3 = new ArrayList<>();
            answers3.add("Philippines");
            answers3.add("Portugal");
            answers3.add("Suède");
            answers3.add("Thailande");
            List<String> answers4 = new ArrayList<>();
            answers4.add("Portugal");
            answers4.add("Colombie");
            answers4.add("Kenya");
            answers4.add("Suède");
            Questions MQ1 = new Questions("qzfqzfqzf", answers1, 3, R.drawable.flag_n_ethiopia, false);
            Questions MQ2 = new Questions("qzfqzf", answers2, 2, R.drawable.flag_n_india, false);
            Questions MQ3 = new Questions("zfqzfq", answers3, 2, R.drawable.flag_n_portugal, false);
            Questions MQ4 = new Questions("zfqzf", answers4, 3, R.drawable.flag_n_kenya, false);
            questions.add(MQ1);
            questions.add(MQ2);
            questions.add(MQ3);
            questions.add(MQ4);

        } else if (selectedDif == 2) {

            // Questions for the hard difficulty
            List<String> answers1 = new ArrayList<>();
            answers1.add("Bosnie-Herzégovine");
            answers1.add("Cape Vert");
            answers1.add("Érythrée");
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
            Questions HQ1 = new Questions("ezeggez", answers1, 3, R.drawable.flag_h_eritrea, false);
            Questions HQ2 = new Questions("aegfegazeg", answers2, 1, R.drawable.flag_h_malawi, false);
            Questions HQ3 = new Questions("aezgfaeg", answers3, 1, R.drawable.flag_h_oman, false);
            Questions HQ4 = new Questions("aezgaeg", answers4, 1, R.drawable.flag_h_mozambique, false);
            questions.add(HQ1);
            questions.add(HQ2);
            questions.add(HQ3);
            questions.add(HQ4);

        } else if (selectedDif == 3) {

            // Questions for the hardcore difficulty
            List<String> answers1 = new ArrayList<>();
            answers1.add("test");
            answers1.add("test");
            answers1.add("test");
            answers1.add("test");
            List<String> answers2 = new ArrayList<>();
            answers2.add("ffqfq");
            answers2.add("zfq");
            answers2.add("fzqs");
            answers2.add("fzqsf");
            List<String> answers3 = new ArrayList<>();
            answers3.add("fzqzf");
            answers3.add("fzqsf");
            answers3.add("fzqs");
            answers3.add("fzqsfz");
            List<String> answers4 = new ArrayList<>();
            answers4.add("fzqsf");
            answers4.add("fzqsfzq");
            answers4.add("fzqsf");
            answers4.add("fzqsf");
            Questions HCQ1 = new Questions("", answers1, 1, 0, false);
            Questions HCQ2 = new Questions("", answers2, 1, 0, false);
            Questions HCQ3 = new Questions("", answers3, 1, 0, false);
            Questions HCQ4 = new Questions("", answers4, 1, 0, false);
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