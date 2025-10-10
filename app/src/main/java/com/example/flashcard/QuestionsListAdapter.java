package com.example.flashcard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter for the RecyclerView that show all questions
public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsListAdapter.ViewHolder> {

    // List of questions to show in the RecyclerView
    private List<Questions> questions;

    // Listener to handle clicks on questions
    private OnQuestionClickListener listener;

    // Interface that allow a react when the user clicks on a question
    public interface OnQuestionClickListener{
        void onQuestionClick(Questions questions, int position);
    }

    // Constructor of the Adapter
    public QuestionsListAdapter(List<Questions> questions, OnQuestionClickListener listener) {
        this.questions = questions;
        this.listener = listener;
    }

    //
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_questions, parent, false);
        return new ViewHolder(view);
    }

    // Binds the data of a question to an existing ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the question and configure the ImageButton
        Questions question = questions.get(position);
        holder.questionsFlag.setImageResource(question.getFlag());

        // Get the difficulty icon and configure the ImageView
        int difficultyIcon = getDifficultyIcon(question, holder);
        holder.difficultySymbol.setImageResource(difficultyIcon);

        // Configure the click listener if it's set
        // So that when the user click, it call the listener
        if (listener!= null){
            holder.questionsFlag.setOnClickListener(v ->
                listener.onQuestionClick(question, position)
            );
        }
    }

    // Determines which icon to choose based via the flag name
    private int getDifficultyIcon(Questions questions, ViewHolder holder){
        int flagId = questions.getFlag();
        String flagName = "";

        try {
            flagName = holder.itemView.getContext().getResources().getResourceEntryName(flagId);
        } catch (Exception e) {
            return R.drawable.d_easy; // Default
        }

        if (flagName.startsWith("flag_e_")){
            return R.drawable.d_easy;
        } else if (flagName.startsWith("flag_n_")) {
            return R.drawable.d_medium;
        } else if (flagName.startsWith("flag_h_")){
            return R.drawable.d_hard;
        } else {
            return R.drawable.d_hardcore;
        }
    }

    // Number of questions in the list
    @Override
    public int getItemCount() {
        return questions.size();
    }

    //
    static class ViewHolder extends  RecyclerView.ViewHolder {

        final ImageButton questionsFlag;
        final ImageView difficultySymbol;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Retrieving the graphics component
            // which is located in itemView. itemView = item_questions.xml
            questionsFlag = itemView.findViewById(R.id.questionsImageButton);
            difficultySymbol = itemView.findViewById(R.id.difficultyImageView);

        }
    }
}
