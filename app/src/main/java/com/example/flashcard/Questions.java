package com.example.flashcard;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class Questions implements Parcelable {

    public String question;
    public String answerI;
    public String answerII;
    public String answerIII;
    public String answerIV;
    public int correctAnswerPosition;
    public int flag;
    public boolean answered;

    public Questions(String question, String answerI, String answerII, String answerIII, String answerIV, int correctAnswerPosition, int flag, boolean answered) {
        this.question = question;
        this.answerI = answerI;
        this.answerII = answerII;
        this.answerIII = answerIII;
        this.answerIV = answerIV;
        this.correctAnswerPosition = correctAnswerPosition;
        this.flag = flag;
        this.answered = answered;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
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

    public String getAnswerIV() {
        return answerIV;
    }

    public void setAnswerIV(String answerIV) {
        this.answerIV = answerIV;
    }

    public String getAnswerIII() {
        return answerIII;
    }

    public void setAnswerIII(String answerIII) {
        this.answerIII = answerIII;
    }

    public String getAnswerII() {
        return answerII;
    }

    public void setAnswerII(String answerII) {
        this.answerII = answerII;
    }

    public String getAnswerI() {
        return answerI;
    }

    public void setAnswerI(String answerI) {
        this.answerI = answerI;
    }

    protected Questions(Parcel in) {
        question = in.readString();
        answerI = in.readString();
        answerII = in.readString();
        answerIII = in.readString();
        answerIV = in.readString();
        correctAnswerPosition = in.readInt();
        flag = in.readInt();
        answered = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answerI);
        dest.writeString(answerII);
        dest.writeString(answerIII);
        dest.writeString(answerIV);
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
}
