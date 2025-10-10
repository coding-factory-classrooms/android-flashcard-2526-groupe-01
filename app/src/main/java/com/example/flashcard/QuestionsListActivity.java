package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// Activity that show the list of the questions
public class QuestionsListActivity extends AppCompatActivity implements QuestionsListAdapter.OnQuestionClickListener {

    private static final String TAG = "QuestionsListActivity";

    private static final String JSON_URL = "https://raw.githubusercontent.com/coding-factory-classrooms/android-flashcard-2526-groupe-01/refs/heads/feature/api/app/src/main/assets/question.json";

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
        adapter = new QuestionsListAdapter(allQuestions, this);
        recyclerView.setAdapter(adapter);

        loadQuestionsFromApi();

    }

    private void loadQuestionsFromApi(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(JSON_URL)
                .build();

        Log.i(TAG, "Envoie de la requete HTTP" + JSON_URL);

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: ", e);

                runOnUiThread(()->{
                    Log.w(TAG, "Using offline questions");
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()){
                    Log.e(TAG, "Response not successful: " + response.code());
                    return;
                }

                String jsonString = response.body().string();
                Log.i(TAG, "Received JSON response, length : " + jsonString.length());

                List<Questions> loadedQuestions = parseQuestionsFromJson(jsonString);

                runOnUiThread(() -> {
                    if (loadedQuestions != null && !loadedQuestions.isEmpty()){
                        allQuestions.clear();
                        allQuestions.addAll(loadedQuestions);
                        adapter.notifyDataSetChanged();
                        Log.i(TAG, "Successfully loaded " + loadedQuestions.size() + "questions from API");
                    } else {
                        Log.w(TAG, "Failed to parse questions, keeping fallback questions ");
                    }
                });
            }
        });
    }

    private List<Questions> parseQuestionsFromJson(String jsonString) {
        List<Questions> questionsList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray questionsArray = jsonObject.getJSONArray("questions");

            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject quesionObj = questionsArray.getJSONObject(i);

                String question = quesionObj.getString("question");
                int correctAnswerPosition = quesionObj.getInt("correctAnswerPosition");
                String difficulty = quesionObj.getString("difficulty");

                JSONArray answersArray = quesionObj.getJSONArray("answers");
                List<String> answers = new ArrayList<>();
                for (int j = 0; j < answersArray.length(); j++) {
                    answers.add(answersArray.getString(j));
                }

                String flagString = quesionObj.getString("flag");
                String rawString = quesionObj.getString("raw");

                int flagId = getResourceIdFromString(flagString);
                int rawId = getResourceIdFromString(rawString);

                Questions q = new Questions(
                        question,
                        answers,
                        correctAnswerPosition,
                        flagId,
                        rawId,
                        difficulty
                );
                questionsList.add(q);
            }
            Log.i(TAG, "Successfully parsed " + questionsList.size() + "questions");
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON", e);
            return null;
        }
        return questionsList;
    }

    private int getResourceIdFromString(String resourceString){
        String resourceName = resourceString
                .replace("R.drawable.", "")
                .replace("R.raw.","");

        String resourceType = resourceString.contains("drawable") ? "drawable" : "raw";

        int resourceId = getResources().getIdentifier(
                resourceName,
                resourceType,
                getPackageName()
        );

        if (resourceId == 0) {
            Log.w(TAG, "Resource not found : " + resourceString);
        }

        return  resourceId;
    }

    /*private void loadQuestionsFromApi() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://raw.githubusercontent.com/coding-factory-classrooms/android-flashcard-2526-groupe-01/dev/app/src/main/assets/question.json")
                .build();

        client.newCall(request).enqueue(new Callback() {

            public static final String TAG = "QuestionsListActivity";

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String body = response.body().string();
                Log.i(TAG, "onResponse: body=" + body);

                try {
                    JSONObject jsonObject = new JSONObject(body);
                    JSONObject questionJsonObject = JSONObject.getJSONObject;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                    Log.e(TAG, "onResponse: ", e);
                }
            }
        });
        Log.i("QuestionListActivity", "Started HTTP Request");
    }*/

    // Get the complete list of all questions and difficulties
    private  List<Questions> getAllQuestions(){
        Log.i(TAG, "Loading fallback questions");

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

        Questions Q1 = new Questions("Quel est le pays de ce drapeau ?", answers1, 1, R.drawable.flag_e_usa, R.raw.duolingo_correct, "easy");
        Questions Q2 = new Questions("Quel est le pays de ce drapeau ?", answers2, 1, R.drawable.flag_e_nigeria, R.raw.duolingo_correct, "easy");
        Questions Q3 = new Questions("Quel est le pays de ce drapeau ?", answers3, 1, R.drawable.flag_e_spain, R.raw.duolingo_correct, "easy");
        Questions Q4 = new Questions("Quel est le pays de ce drapeau ?", answers4, 1, R.drawable.flag_e_italy, R.raw.duolingo_correct, "easy");

        questions.add(Q1);
        questions.add(Q2);
        questions.add(Q3);
        questions.add(Q4);
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

        Questions Q1 = new Questions("Quel est le pays de ce drapeau ?", answers1, 1, R.drawable.flag_n_ethiopia, R.raw.duolingo_correct, "medium");
        Questions Q2 = new Questions("Quel est le pays de ce drapeau ?", answers2, 1, R.drawable.flag_n_india, R.raw.duolingo_correct, "medium");
        Questions Q3 = new Questions("Quel est le pays de ce drapeau ?", answers3, 1, R.drawable.flag_n_portugal, R.raw.duolingo_correct, "medium");
        Questions Q4 = new Questions("Quel est le pays de ce drapeau ?", answers4, 1, R.drawable.flag_n_kenya, R.raw.duolingo_correct, "medium");

        questions.add(Q1);
        questions.add(Q2);
        questions.add(Q3);
        questions.add(Q4);
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

        Questions Q1 = new Questions("Quel est le pays de ce drapeau ?", answers1, 1, R.drawable.flag_h_eritrea, R.raw.duolingo_correct, "hard");
        Questions Q2 = new Questions("Quel est le pays de ce drapeau ?", answers2, 1, R.drawable.flag_h_malawi, R.raw.duolingo_correct, "hard");
        Questions Q3 = new Questions("Quel est le pays de ce drapeau ?", answers3, 1, R.drawable.flag_h_oman, R.raw.duolingo_correct, "hard");
        Questions Q4 = new Questions("Quel est le pays de ce drapeau ?", answers4, 1, R.drawable.flag_h_mozambique, R.raw.duolingo_correct, "hard");

        questions.add(Q1);
        questions.add(Q2);
        questions.add(Q3);
        questions.add(Q4);
        return questions;
    }

    private List<Questions> getHardcoreQuestions() {
        List<Questions> questions = new ArrayList<>();

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

        Questions Q1 = new Questions("Quel est le drapeau de cette état américain ?", answers1, 1, R.drawable.flag_vh_hawaii, R.raw.duolingo_correct, "hardcore");
        Questions Q2 = new Questions("Quel est ce pays non reconnue par l'ONU ?", answers2, 1, R.drawable.flag_vh_somaliland, R.raw.duolingo_correct, "hardcore");
        Questions Q3 = new Questions("Quel est ce pays aujourd'hui disparue ?", answers3, 1, R.drawable.flag_vh_german_empire, R.raw.duolingo_correct, "hardcore");
        Questions Q4 = new Questions("Quel est le territoire français de ce drapeau ?", answers4, 1, R.drawable.flag_vh_saint_pierre_and_miquelon, R.raw.duolingo_correct, "hardcore");

        questions.add(Q1);
        questions.add(Q2);
        questions.add(Q3);
        questions.add(Q4);
        return questions;
    }

    @Override
    public void onQuestionClick(Questions questions, int position) {
        Intent intent = new Intent(this, QuestionsActivity.class);
        ArrayList<Questions> list = new ArrayList<>();
        list.add(questions);
        intent.putParcelableArrayListExtra("questions", list);
        startActivity(intent);
    }
}