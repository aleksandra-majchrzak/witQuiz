package com.example.witquiz.databasemanager;

import java.util.ArrayList;
import java.util.List;

import com.example.witquiz.entities.Category;

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
				
				categories.add(new Category(cursor.getInt(cursor.getColumnIndex("Id")), 
											cursor.getString(cursor.getColumnIndex("Name"))));
			}
			
		}finally{
			if(cursor!= null)
				cursor.close();
		}
		
		return categories.toArray(new Category[categories.size()]);
	}

}
