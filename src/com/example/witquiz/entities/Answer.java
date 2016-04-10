package com.example.witquiz.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable{

	private int id;
	private int questionId;
	private String answer;
	
	public Answer(int id, int questionId, String answer) {
		this.id = id;
		this.questionId = questionId;
		this.answer = answer;
	}

	public Answer(Parcel in) {
		
		this.id = in.readInt();
		this.questionId = in.readInt();
		this.answer = in.readString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeInt(this.id);
		dest.writeInt(this.questionId);
		dest.writeString(this.answer);
		
	}
	
	public static final Parcelable.Creator<Answer> CREATOR =
            new Parcelable.Creator<Answer>() {

        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
    
}
