package com.example.flashcard;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.util.List;

public class Questions implements Parcelable {

    public String question;
    public List<String> answers;
    public int correctAnswerPosition;
    public int flag;
    public int raw;

    public String difficulty;

    public Questions(String question, List<String> answers, int correctAnswerPosition, int flag, int raw, String difficulty) {
        this.question = question;
        this.answers = answers;
        this.correctAnswerPosition = correctAnswerPosition;
        this.flag = flag;
        this.raw = raw;
        this.difficulty = difficulty;
    }

    protected Questions(Parcel in) {
        question = in.readString();
        answers = in.createStringArrayList();
        correctAnswerPosition = in.readInt();
        flag = in.readInt();
        raw = in.readInt();
        difficulty = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeStringList(answers);
        dest.writeInt(correctAnswerPosition);
        dest.writeInt(flag);
        dest.writeInt(raw);
        dest.writeString(difficulty);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswerPosition() {
        return correctAnswerPosition;
    }

    public void setCorrectAnswerPosition(int correctAnswerPosition) {
        this.correctAnswerPosition = correctAnswerPosition;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getRaw() {
        return raw;
    }

    public void setRaw(int raw) {
        this.raw = raw;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


}