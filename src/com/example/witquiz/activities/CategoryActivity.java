package com.example.witquiz.activities;

import com.example.witquiz.R;
import com.example.witquiz.databasemanager.CursorHelper;
import com.example.witquiz.databasemanager.DatabaseManager;
import com.example.witquiz.entities.Category;
import com.example.witquiz.entities.Question;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CategoryActivity extends Activity {
	
	ListView questionsListView;
	Category category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		category = (Category) this.getIntent().getExtras().getParcelable("category");
		
		loadControls();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.add_new_question) {
			
			Intent intent = new Intent(CategoryActivity.this, EditQuestionActivity.class);
			intent.putExtra("category", category);
			startActivity(intent);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		((CursorAdapter)questionsListView.getAdapter())
							.changeCursor(DatabaseManager.getQuestionsForCategory(category.getId()));
	}
	
	private void loadControls(){
		
		TextView categoryTextView = (TextView) findViewById(R.id.category_name_textView);
		categoryTextView.setText(category.getName());
		
		questionsListView = (ListView) findViewById(R.id.questions_listView);
		
		CursorAdapter adapter = new CursorAdapter(this, DatabaseManager.getQuestionsForCategory(category.getId())) {
			
			@Override
			public View newView(Context context, Cursor cursor, ViewGroup parent) {
				
				return LayoutInflater.from(context).inflate(R.layout.question_row, parent, false);
			}
			
			@Override
			public void bindView(View view, Context context, Cursor cursor) {
				TextView questionTextView = (TextView) view.findViewById(R.id.question_summary_textView);
				questionTextView.setText(CursorHelper.getString(cursor, "Question"));
				
				TextView answersTextView = (TextView) view.findViewById(R.id.answers_summary_textView);
				answersTextView.setText(CursorHelper.getString(cursor, "Answers"));
				
				view.setTag(CursorHelper.getInt(cursor, "_id"));
			}
		};
		
		questionsListView.setAdapter(adapter);
		
		questionsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Question selectedQuestion = (Question) DatabaseManager.getQuestionById((int) id);
				
				Intent intent = new Intent(CategoryActivity.this, EditQuestionActivity.class);
				intent.putExtra("question", selectedQuestion);
				intent.putExtra("category", category);
				startActivity(intent);
			}

		});
	}
}
