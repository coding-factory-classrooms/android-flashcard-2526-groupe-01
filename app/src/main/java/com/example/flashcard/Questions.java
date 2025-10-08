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
