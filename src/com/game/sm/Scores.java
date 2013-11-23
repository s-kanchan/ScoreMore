package com.game.sm;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Scores extends Activity {
	
	ScoreHandler score_handler;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.scores);
		score_handler = new ScoreHandler(this);
		setScores();
		super.onCreate(savedInstanceState);
	}

	private void setScores() {
		cursor = score_handler.getScores();
		int offset =1;
		if(cursor != null && cursor.moveToFirst())
		{
			do
			{
				String name = cursor.getString(1);
				int score = cursor.getInt(2);
				String name_tag = "score_name_"+String.valueOf(offset);
				String score_tag = "score_"+String.valueOf(offset);
				int resID = getResources().getIdentifier(name_tag, "id", getPackageName());
				TextView tv = (TextView)findViewById(resID);
				tv.setText(name);
				
				resID = getResources().getIdentifier(score_tag, "id", getPackageName());
				tv = (TextView)findViewById(resID);
				tv.setText(String.valueOf(score));
				
				offset++;
				
			}while(cursor.moveToNext());
		}
		
	}
	
	

}
