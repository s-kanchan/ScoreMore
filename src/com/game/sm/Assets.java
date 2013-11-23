package com.game.sm;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Assets {
	
	public Bitmap background;
	public Bitmap start;
	public Bitmap cell_normal;
	public Bitmap cell_normal_dark;
	public Bitmap cell_pressed_dark;
	public Bitmap cell_blue;
	public Bitmap cell_red;
	
	public Assets(Game game, Context context) {
		
		Bitmap start_raw = BitmapFactory.decodeResource(context.getResources(), R.drawable.start);
		start = Bitmap.createScaledBitmap(start_raw, game.CELL_WIDTH, game.CELL_HEIGHT, true);
		
		Bitmap cell_normal_raw = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_normal);
		cell_normal = Bitmap.createScaledBitmap(cell_normal_raw, game.CELL_WIDTH, game.CELL_HEIGHT, true);
		
		Bitmap cell_normal_dark_raw = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_normal_darker);
		cell_normal_dark = Bitmap.createScaledBitmap(cell_normal_dark_raw, game.CELL_WIDTH, game.CELL_HEIGHT, true);
		
		//Bitmap cell_pressed_dark_raw = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_pressed_dark);
		//cell_pressed_dark = Bitmap.createScaledBitmap(cell_pressed_dark_raw, game.CELL_WIDTH, game.CELL_HEIGHT, true);
		
		Bitmap cell_blue_raw = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_dark2);
		cell_blue = Bitmap.createScaledBitmap(cell_blue_raw, game.CELL_WIDTH, game.CELL_HEIGHT, true);
		
		Bitmap cell_red_raw = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_dark2);
		cell_red = Bitmap.createScaledBitmap(cell_red_raw, game.CELL_WIDTH, game.CELL_HEIGHT, true);
		
		Bitmap background_raw = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
		background = Bitmap.createScaledBitmap(background_raw, game.WIDTH, game.HEIGHT, true);
		
	}
	

}
