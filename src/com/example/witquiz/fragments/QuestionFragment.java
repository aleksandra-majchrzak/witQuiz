package com.example.witquiz.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.witquiz.R;
import com.example.witquiz.entities.Answer;
import com.example.witquiz.entities.Question;

public class QuestionFragment extends Fragment {

	private TextView questionTextView;
	private ToggleButton[] answerButtons;
	private Question[] questions;
	int currentQuestionIndex = 0;
	private int checkedIndex = -1;
	Button confirmButton;
	
	public QuestionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_question,
				container, false);
		
		if(savedInstanceState!= null){
			questions = (Question[]) savedInstanceState.getParcelableArray("questions");
			currentQuestionIndex = savedInstanceState.getInt("questionIndex");
			checkedIndex = savedInstanceState.getInt("checked");
		}
		else{
			Parcelable[] parcelables =  this.getArguments().getParcelableArray("questions");
			questions = new Question[parcelables.length];
			
			System.arraycopy(parcelables, 0, questions, 0, parcelables.length);
			
			checkedIndex = -1;
		}
		
		loadControls(rootView);
		
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		final Answer[] answers = questions[currentQuestionIndex].getAnswers();
		
		for(int i = 0; i<4 ;++i){
			
			ToggleButton button = answerButtons[i];
			
			if(button.isChecked() && checkedIndex >= 0)
				markAnswers(answers[i].getId(), i);
			
			if(checkedIndex >= 0)
				button.setEnabled(false);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		
		outState.putParcelableArray("questions", questions);
		outState.putInt("questionIndex", currentQuestionIndex);
		outState.putInt("checked", checkedIndex);
		
		super.onSaveInstanceState(outState);
	}
	
	private void loadControls(View view){		
		
		questionTextView = (TextView) view.findViewById(R.id.question_textView);		
		
		answerButtons = new ToggleButton[4];
		
		answerButtons[0] = (ToggleButton) view.findViewById(R.id.a_answer_button);
		answerButtons[1] = (ToggleButton) view.findViewById(R.id.b_answer_button);
		answerButtons[2] = (ToggleButton) view.findViewById(R.id.c_answer_button);
		answerButtons[3] = (ToggleButton) view.findViewById(R.id.d_answer_button);
		
		loadControlsText();
		
		for(int i = 0; i<4 ;++i){
			
			ToggleButton button = answerButtons[i];
			
			button.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					for(ToggleButton button : answerButtons){
						if(button != v)
							button.setChecked(false);
					}
					
				}
				
			});
		}		
		
		
		confirmButton = (Button) view.findViewById(R.id.confirm_button);
		
		if(checkedIndex >= 0)
			confirmButton.setText(R.string.next);
		else
			confirmButton.setText(R.string.confirm);
		
		confirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Question question = questions[currentQuestionIndex];
				Answer[] answers = question.getAnswers();
				
				if(checkedIndex < 0){
	
					int checkedAnswerId = 0;
					int answerIndex = 0;
					
					while(answerIndex < 4){
						
						ToggleButton button = answerButtons[answerIndex];
						
						if(button.isChecked()){
							checkedAnswerId = answers[answerIndex].getId();
							break;
						}
						
						++answerIndex;
					}
					
					if(checkedAnswerId > 0){
						
						checkedIndex = answerIndex;
						
						for(ToggleButton button : answerButtons)
							button.setEnabled(false);
						
						if(! markAnswers(checkedAnswerId, answerIndex))
							return;
						
						((Button)v).setText(R.string.next);
						
					}
					else{
						Toast.makeText(QuestionFragment.this.getActivity(), "¯adna odpowiedŸ nie zosta³a zaznaczona.", Toast.LENGTH_SHORT).show();
					}
					
				}
				
				else{
					++currentQuestionIndex;
					
					if(currentQuestionIndex >= questions.length){						
						
						--currentQuestionIndex;
						
						endOfGame(answers[checkedIndex].getId() == question.getAnswerId());
						return;
					}
					
					
					checkedIndex = -1;
					((Button)v).setText(R.string.confirm);
					
					for(ToggleButton button : answerButtons)
						button.setEnabled(true);
					
					for(ToggleButton button : answerButtons){
						button.setChecked(false);
						button.setBackground(getResources().getDrawable(R.drawable.question_button_selector));
					}
					
					loadControlsText();
				}
				
			}
		});
		
	}
	
	private void loadControlsText(){
		
		Question question = questions[currentQuestionIndex];
		Answer[] answers = question.getAnswers();
		
		questionTextView.setText(question.getQuestion());
		
		String[] answersPrefs = this.getResources().getStringArray(R.array.answers_array);
		
		for(int i = 0; i<4 ;++i){
			
			ToggleButton button = answerButtons[i];
			
			button.setTextOn(String.format(answersPrefs[i], answers[i].getAnswer()));
			button.setTextOff(String.format(answersPrefs[i], answers[i].getAnswer()));
			button.setText(String.format(answersPrefs[i], answers[i].getAnswer()));
		}
	}
	
	private boolean markAnswers(int checkedAnswerId, int answerIndex){
		Question question = questions[currentQuestionIndex];
		Answer[] answers = question.getAnswers();
		
		if(checkedAnswerId == question.getAnswerId()){
			answerButtons[answerIndex]
					.setBackground(getResources().getDrawable(R.drawable.question_button_ok_selector));
			
			return true;
		}
		else{
			
			answerButtons[answerIndex]
					.setBackground(getResources().getDrawable(R.drawable.question_button_wrong_selector));
			
			for(int i = 0; i<4; ++i){
				
				if(answers[i].getId() == question.getAnswerId()){
					
					answerButtons[i]
							.setBackground(getResources().getDrawable(R.drawable.question_button_ok_selector));
					
					break;
				}
			}
						
			return endOfGame(false);
		}
		
	}
	
	private boolean endOfGame(boolean success){
		
		if(success)
			confirmButton.setText(R.string.won);
		else
			confirmButton.setText(R.string.lost);
		
		for(ToggleButton button : answerButtons)
			button.setEnabled(false);
		
		confirmButton.setEnabled(false);
		
		return success;
	}

}