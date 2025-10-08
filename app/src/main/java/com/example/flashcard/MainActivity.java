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
            Intent intent = new Intent(this, StatActivity.class);
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
}