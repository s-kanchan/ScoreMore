package com.game.sm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView.BufferType;

public class GameConfig extends Activity {
	
	SharedPreferences sharedPreferences; 
	Editor editor;
	Sound sound;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		sound = new Sound(this);
		sharedPreferences = getSharedPreferences("scoremore", Context.MODE_PRIVATE);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.menu_config);
		
		
	
	}
	
	public void onClickOfSinglePlayer(View v)
	{
		//this.startActivity(new Intent(this,GamePlay.class));
		sound.play_button_click();
		String name = sharedPreferences.getString("single_player_name", "");
		Log.i("GAME CONFIG", name);
		setContentView(R.layout.menu_single_player);
		if(name != "")
		{
			EditText et = (EditText) findViewById(R.id.single_player_name);
			et.setText(name, BufferType.EDITABLE);
		}
		
	}
	
	public void onClickOfTwoPlayer(View v)
	{
		sound.play_button_click();
		setContentView(R.layout.menu_two_player);
		/*
		String player1 = sharedPreferences.getString("player1_name", "");
		if(player1 != "")
		{
			EditText et = (EditText) findViewById(R.id.player1_name);
			et.setText(player1);
		}
		
		String player2 = sharedPreferences.getString("player2_name", "");
		if(player2 != "")
		{
			EditText et = (EditText) findViewById(R.id.player2_name);
			et.setText(player2);
		}
		
		*/
	}
	
	public void single_player_start(View v)
	{
		sound.play_button_click();
		EditText et = (EditText) findViewById(R.id.single_player_name);
		String name = et.getText().toString();
		if(name.equals(""))
			name = "Player1";
			
		editor = sharedPreferences.edit();
		editor.putString("player1_name", name);
		editor.putString("single_player_name", name);
		editor.putString("player2_name", "Computer");
		editor.putBoolean("is_AI", true);
		
		editor.commit();
			
		this.startActivity(new Intent(this,GamePlay.class));
		
		finish();
	}

	public void two_player_start(View v)
	{
		sound.play_button_click();
		EditText et1 = (EditText) findViewById(R.id.player1_name);
		String name1 = et1.getText().toString();
		EditText et2 = (EditText) findViewById(R.id.player2_name);
		String name2 = et2.getText().toString();
		if(name1.equals(""))
			name1 = "Player1";
		if(name2.equals(""))
			name2 = "Player2";
		
		editor = sharedPreferences.edit();
		editor.putString("player1_name", name1);
		//editor.commit();
		
		editor.putString("player2_name", name2);
		editor.putBoolean("is_AI", false);
		editor.commit();
		
		this.startActivity(new Intent(this,GamePlay.class));
		
		finish();
	}
}
