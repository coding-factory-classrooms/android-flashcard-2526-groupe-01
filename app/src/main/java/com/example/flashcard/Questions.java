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
    public boolean answered;

    public Questions(String question, List<String> answers, int correctAnswerPosition, int flag, boolean answered) {
        this.question = question;
        this.answers = answers;
        this.correctAnswerPosition = correctAnswerPosition;
        this.flag = flag;
        this.answered = answered;
    }

    protected Questions(Parcel in) {
        question = in.readString();
        answers = in.createStringArrayList();
        correctAnswerPosition = in.readInt();
        flag = in.readInt();
        answered = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeStringList(answers);
        dest.writeInt(correctAnswerPosition);
        dest.writeInt(flag);
        dest.writeByte((byte) (answered ? 1 : 0));
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

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
