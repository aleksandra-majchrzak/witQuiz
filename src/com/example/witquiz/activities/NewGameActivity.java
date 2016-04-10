package com.example.witquiz.activities;

import com.example.witquiz.R;
import com.example.witquiz.databasemanager.DatabaseManager;
import com.example.witquiz.entities.Category;
import com.example.witquiz.entities.Question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class NewGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);
		
		loadControls();
	}
	
	private void loadControls(){
		
		final Spinner categorySpinner = (Spinner) findViewById(R.id.choose_category_spinner);
		
		Category[] categories = DatabaseManager.getAllCategories(true);
		
		categorySpinner.setAdapter(new ArrayAdapter<Category>(this, R.layout.simple_row, categories){
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				if(convertView == null){
					convertView = NewGameActivity.this.getLayoutInflater()
														.inflate(R.layout.simple_row, null);
				}
				
				TextView categoryTextView = (TextView) convertView.findViewById(R.id.simple_row_textView);
				
				categoryTextView.setText(this.getItem(position).getName());
				
				return convertView;
			}
			
			@Override
			public View getDropDownView (int position, View convertView, ViewGroup parent){
				
				if(convertView == null){
					convertView = NewGameActivity.this.getLayoutInflater()
														.inflate(R.layout.simple_row, null);
				}
				
				TextView categoryTextView = (TextView) convertView.findViewById(R.id.simple_row_textView);
				
				categoryTextView.setText(this.getItem(position).getName());
				
				return convertView;
				
			}
			
		});
		
		Button startGameButton = (Button) findViewById(R.id.start_game_button);
		
		startGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Category selectedCategory = (Category) categorySpinner.getSelectedItem();
				
				Question[] questions = DatabaseManager.getQuestionsForCategory(selectedCategory.getId());
				
				Intent intent = new Intent(NewGameActivity.this, QuestionActivity.class);
				intent.putExtra("categoryName", selectedCategory.getName());
				intent.putExtra("questions", questions);
				startActivity(intent);
				
			}
		});
	}

}
