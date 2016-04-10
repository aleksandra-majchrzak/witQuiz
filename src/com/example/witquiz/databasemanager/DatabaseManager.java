package com.example.witquiz.databasemanager;

import java.util.ArrayList;
import java.util.List;

import com.example.witquiz.entities.Answer;
import com.example.witquiz.entities.Category;
import com.example.witquiz.entities.Question;

import android.database.Cursor;

public class DatabaseManager {
	
	public static Category[] getAllCategories(){
		Cursor cursor = null;
		List<Category> categories;
		
		try{
			cursor = DatabaseHelper.getDatabaseInstance().rawQuery("SELECT * FROM Categories", null);
			
			if(cursor.getCount()==0)
				return new Category[0];
			
			categories = new ArrayList<Category>(cursor.getCount());
			
			while(cursor.moveToNext()){
				
				categories.add(new Category(CursorHelper.getInt(cursor, "Id"), 
											CursorHelper.getString(cursor, "Name")));
			}
			
		}finally{
			if(cursor!= null)
				cursor.close();
		}
		
		return categories.toArray(new Category[categories.size()]);
	}
	
	public static Question[] getQuestionsForCategory(int categoryId){
		
		List<Question> questions = new ArrayList<Question>();
		Cursor cursor = null;
		
		try{
			cursor = DatabaseHelper.getDatabaseInstance().rawQuery(
					"SELECT q.Id, q.Question, q.AnswerId FROM Questions q WHERE q.CategoryId = ? ORDER BY RANDOM() LIMIT 10"
						, new String[]{ String.valueOf(categoryId) } );
			
			if(cursor.getCount()==0)
				return questions.toArray(new Question[0]);
			
			while(cursor.moveToNext()){
				
				Question question = new Question(CursorHelper.getInt(cursor, "Id"), 
						categoryId, 
						CursorHelper.getString(cursor, "Question"), 
						null, 
						CursorHelper.getInt(cursor, "AnswerId"));
				
				Cursor answerCursor = null;
				
				try{
					
					answerCursor = DatabaseHelper.getDatabaseInstance().rawQuery(
							"SELECT a.Id, a.Answer FROM Answers a WHERE a.QuestionId = ? ORDER BY RANDOM()"
								, new String[]{ String.valueOf(question.getId()) } );

					Answer[] answers = new Answer[4];
					int i = 0;
					
					while(answerCursor.moveToNext()){
						answers[i] = new Answer(CursorHelper.getInt(answerCursor, "Id"), 
								question.getId(), 
								CursorHelper.getString(answerCursor, "Answer"));
						
						++i;
					}
					
					question.setAnswers(answers);
					
				} finally{
					
					if(answerCursor!= null)
						answerCursor.close();
				}
				
				questions.add(question);
			}
			
		} finally{
			
			if(cursor!= null)
				cursor.close();
		}
		
		return questions.toArray(new Question[questions.size()]);
	}

}
