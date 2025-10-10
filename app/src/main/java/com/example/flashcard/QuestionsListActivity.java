package com.example.flashcard;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Activity that show the list of the questions
public class QuestionsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  QuestionsListAdapter adapter;
    private List<Questions> allQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questions_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initializing the RecyclerView
        // Getting the RecyclerView ID in activity_questions_list_xml
        recyclerView = findViewById(R.id.questionsRecyclerView);
        // Configure the RecyclerView to display items in a vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Getting all questions
        allQuestions = getAllQuestions();

        // Create and configure the Adapter
        adapter = new QuestionsListAdapter(allQuestions);
        recyclerView.setAdapter(adapter);
    }

    // Get the complete list of all questions and difficulties
    private  List<Questions> getAllQuestions(){
        List<Questions> questions = new ArrayList<>();

        // Add easy questions
        questions.addAll(getEasyQuestions());

        // Add normal questions
        questions.addAll(getNormalQuestions());

        // Add hard questions
        questions.addAll(getHardQuestions());

        // Add hardcore questions
        questions.addAll(getHardcoreQuestions());

        return questions;
    }

    // all question
    private List<Questions> getEasyQuestions(){
        List<Questions> questions = new ArrayList<>();

        List<String> answers1 = new ArrayList<>();
        answers1.add("Brasil");
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

        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers1, 1, R.drawable.flag_e_brazil, false));
        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers2, 1, R.drawable.flag_e_nigeria, false));
        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers3, 1, R.drawable.flag_e_spain, false));
        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers4, 1, R.drawable.flag_e_italy, false));

        return questions;
    }
    private List<Questions> getNormalQuestions() {
        List<Questions> questions = new ArrayList<>();

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

        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers1, 1, R.drawable.flag_n_ethiopia, false));
        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers2, 1, R.drawable.flag_n_india, false));
        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers3, 1, R.drawable.flag_n_portugal, false));
        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers4, 1, R.drawable.flag_n_kenya, false));

        return questions;
    }

    private List<Questions> getHardQuestions() {
        List<Questions> questions = new ArrayList<>();

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

        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers1, 1, R.drawable.flag_h_eritrea, false));
        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers2, 1, R.drawable.flag_h_malawi, false));
        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers3, 1, R.drawable.flag_h_oman, false));
        questions.add(new Questions("Quel est le pays de ce drapeau ?", answers4, 1, R.drawable.flag_h_mozambique, false));

        return questions;
    }

    private List<Questions> getHardcoreQuestions() {
        List<Questions> questions = new ArrayList<>();

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

        questions.add(new Questions("", answers1, 1, 0, false));
        questions.add(new Questions("", answers2, 1, 0, false));
        questions.add(new Questions("", answers3, 1, 0, false));
        questions.add(new Questions("", answers4, 1, 0, false));

        return questions;
    }
}