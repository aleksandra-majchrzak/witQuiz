package com.example.witquiz.databasemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper{
	
	private static String DB_PATH = Environment.getExternalStorageDirectory().toString()+"/";
	private static String DB_NAME = "witData.db";
	public static final int DBVERSION = 1;

	public DatabaseOpenHelper(Context context) {
		
		super(context, DB_PATH + DB_NAME, null, DBVERSION);
		
		String dir1 = Environment.getDataDirectory().getAbsolutePath();
		String dir2 = Environment.getExternalStorageDirectory().getAbsolutePath();
		String dir3 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
		String dir4 = Environment.getDownloadCacheDirectory().getAbsolutePath();
		
		Log.d("dir1", dir1);
		Log.d("dir2", dir2);
		Log.d("dir3", dir3);
		Log.d("dir4", dir4);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE Categories( " +
				"Id INT NOT NULL, " +
				"Name NVARCHAR(100) NOT NULL, " +
				"PRIMARY KEY (Id)" +
				");");
		
		db.execSQL("CREATE TABLE Questions( " +
				"Id INT NOT NULL, " +
				"CategoryId INT NOT NULL, " +
				"Question NVARCHAR(100) NOT NULL, " +
				"AnswerId INT NOT NULL, " +
				"PRIMARY KEY(Id) " +
				");");
		
		db.execSQL("CREATE TABLE Answers( " +
				"Id INT NOT NULL, " +
				"QuestionId INT NOT NULL, " +
				"Answer NVARCHAR(100) NOT NULL, " +
				"PRIMARY KEY (Id) " +
				");");
		
		db.execSQL("CREATE TABLE Users( " +
				"Id INT NOT NULL, " +
				"Name NVARCHAR(100) NOT NULL, " +
				"PRIMARY KEY (Id) " +
				");");
		
		db.execSQL("CREATE TABLE HighScores( " +
				"UserId INT NOT NULL, " +
				"HighScore INT NOT NULL, " +
				"PRIMARY KEY (UserId) " +
				");");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
