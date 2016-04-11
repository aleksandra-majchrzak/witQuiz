package com.example.witquiz.activities;

import com.example.witquiz.R;
import com.example.witquiz.databasemanager.DatabaseManager;
import com.example.witquiz.entities.Answer;
import com.example.witquiz.entities.Category;
import com.example.witquiz.entities.Question;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class EditQuestionActivity extends Activity implements OnClickListener{
	
	Question question;
	private EditText questionTextView;
	private RadioButton[] answerButtons;
	private EditText[] answerEditTexts;
	private Category category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_question);
		
		if(savedInstanceState != null){
			question = (Question) savedInstanceState.getParcelable("question");
			category = (Category) savedInstanceState.getParcelable("category");
		}
		else{
			question = getIntent().getExtras().getParcelable("question");
			category = getIntent().getExtras().getParcelable("category");
		}
		
		loadControls();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_question, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.save_question) {
			boolean newQuestion = question == null;
			
			if(newQuestion){
				question =  new Question();
				
				for(int i = 0; i< 4; ++i){			
					Answer answer = new Answer(0, 0, answerEditTexts[i].getText().toString());
					
					question.getAnswers()[i] = answer;
							
					if(answerButtons[i].isChecked())
						question.setAnswerId(i);
				}
			}
			else{
				
				for(int i = 0; i< 4; ++i){			
					
					question.getAnswers()[i].setAnswer(answerEditTexts[i].getText().toString());
							
					if(answerButtons[i].isChecked())
						question.setAnswerId(question.getAnswers()[i].getId());
				}
			}
			
			question.setCategoryId(category.getId());
			question.setQuestion(questionTextView.getText().toString());
			
			if(newQuestion)
				DatabaseManager.insertQuestion(question);
			else
				DatabaseManager.updateQuestion(question);
			
			this.finish();
			
			return true;
		}
		else if(id == R.id.delete_question){
			
			AlertDialog.Builder builder = new Builder(this);
			 builder
			 .setMessage(R.string.do_you_want_to_delete_question)
			 .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					if(question != null)
						DatabaseManager.deleteQuestion(question);
					
					EditQuestionActivity.this.finish();					
				}
			})
			.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {}
			})
			.show();

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		outState.putParcelable("question", question);
		outState.putParcelable("category", category);
		
		super.onSaveInstanceState(outState);
	}
	
	private void loadControls(){
		
		((TextView) findViewById(R.id.category_name_TextView)).setText(category.getName()) ;
		
		questionTextView = (EditText) findViewById(R.id.question_editText);
		answerButtons = new RadioButton[4];
		answerEditTexts = new EditText[4];
		
		answerButtons[0] = (RadioButton) findViewById(R.id.answer_radio0);
		answerButtons[1] = (RadioButton) findViewById(R.id.answer_radio1);
		answerButtons[2] = (RadioButton) findViewById(R.id.answer_radio2);
		answerButtons[3] = (RadioButton) findViewById(R.id.answer_radio3);
		
		for(int i =0; i< 4; ++i)
			answerButtons[i].setOnClickListener(this);
		
		answerEditTexts[0] = (EditText) findViewById(R.id.answer_editText0);
		answerEditTexts[1] = (EditText) findViewById(R.id.answer_editText1);
		answerEditTexts[2] = (EditText) findViewById(R.id.answer_editText2);
		answerEditTexts[3] = (EditText) findViewById(R.id.answer_editText3);
		
		if(question != null){
			questionTextView.setText(question.getQuestion());
			
			answerEditTexts[0].setText(question.getAnswers()[0].getAnswer());
			answerEditTexts[1].setText(question.getAnswers()[1].getAnswer());
			answerEditTexts[2].setText(question.getAnswers()[2].getAnswer());
			answerEditTexts[3].setText(question.getAnswers()[3].getAnswer());
			
			for(int i = 0; i< 4; ++i){
				
				if(question.getAnswers()[i].getId() == question.getAnswerId())
					answerButtons[i].setChecked(true);
				else
					answerButtons[i].setChecked(false);
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(v instanceof RadioButton){
			
			for(int i =0; i< 4; ++i)
				answerButtons[i].setChecked(false);
			
			((RadioButton) v).setChecked(true);
		}
		
	}
}
