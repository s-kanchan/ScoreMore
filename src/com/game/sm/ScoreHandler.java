package com.game.sm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ScoreHandler {
	
	private SQLiteDatabase scoremore_database;
	private Cursor cursor;
	private String table_name;
	private String db_name;
	
	
	public ScoreHandler(Context context) {
		
		Log.i("SCOREHANDLER", "inside constructor");
		db_name = "ScoreMoreData.db";
		table_name = "scores";
		scoremore_database = context.openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
		create_table();
		
	}

	private void create_table() {
		String query = "CREATE TABLE IF NOT EXISTS "+table_name+" (_id integer primary key autoincrement ,name text not null, score integer not null)";
		scoremore_database.execSQL(query);
		
	}
	
	public Cursor getScores()
	{
		String query = "SELECT * FROM "+ table_name + " ORDER BY score DESC";
		cursor = scoremore_database.rawQuery(query, null);
		return cursor;
	}
	
	public int getTopScore()
	{
		cursor = getScores();
		if(cursor != null && cursor.moveToFirst())
		{
			//cursor.moveToFirst();
			return(cursor.getInt(2));
		}
		else
			return (int) Integer.MIN_VALUE;
	}

	public void update(int score, String name) {
		
		ContentValues values =new ContentValues();
		values.put("name", name);
		values.put("score", score);
		
		cursor = getScores();
		if(cursor.getCount() < 5)
		{
			scoremore_database.insert(table_name, null, values);
		}
		else
		{
			cursor.moveToLast();
			int _id = cursor.getInt(0);
			scoremore_database.update(table_name, values, "_id="+String.valueOf(_id), null);
		}
		
	}

}
