package com.game.sm;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

public class Cell {
	
	int value;
	int ID;
	Game.POSITION POSITION;
	Game.STATE STATE;
	Point top_left_corner;
	Bitmap cell_image;
	
	Cell(int id, Game game)
	{
		
		this.ID = id;
		value = generate_value(id);
		POSITION = setPosition(game);
		STATE = Game.STATE.INVALID;
		top_left_corner = findTopLeftCorner(game);
	}

	private int generate_value(int id) {
		int value;
		int shuffler;
		Random randomGenerator = new Random();
		shuffler = randomGenerator.nextInt(10)+1;
		
		if(shuffler < 4)
			value = randomGenerator.nextInt(99)+100;
		else if(shuffler < 6)
			value = randomGenerator.nextInt(30)+50;
		else
			value = randomGenerator.nextInt(20)+1;
		
		return value;
	}

	private Point findTopLeftCorner(Game game) {
		
		Point top_left_corner = new Point();
		
		top_left_corner.x = (game.LEFT + (this.ID % game.COLS)*game.CELL_WIDTH);
		top_left_corner.y = (game.TOP + (int)(this.ID/game.COLS)*game.CELL_HEIGHT);
		//Log.i("CELL", "TLC ID : "+String.valueOf(ID)+" X : "+String.valueOf(top_left_corner.x)+" Y : "+String.valueOf(top_left_corner.y));
		return top_left_corner;
	}

	private Game.POSITION setPosition(Game game) {
		
		if((int)(ID/game.COLS) == 0)
			return Game.POSITION.TOP;
		
		else if( (int) (ID % game.COLS) == game.COLS-1 )
			return Game.POSITION.RIGHT;
		
		else if( (int) (ID % game.COLS) == 0)
			return Game.POSITION.LEFT;
		
		else if((int) (ID/game.COLS) == game.ROWS -1 )
			return Game.POSITION.BOTTOM;
		
		
		
		
		
		return Game.POSITION.MIDDLE;
	}

	public void drawCell(Canvas canvas, Game game, Paint paint) {
		paint.setAntiAlias(true);
		//Log.i("CELL", "Drawing CEll : "+String.valueOf(ID)+" X: "+String.valueOf(top_left_corner.x)+" Y : "+String.valueOf(top_left_corner.y));
		if(POSITION == Game.POSITION.START)
			cell_image = game.assets.start;
		else if(STATE == Game.STATE.VALID)
			cell_image = game.assets.cell_normal;
		else if(STATE == Game.STATE.INVALID)
			cell_image = game.assets.cell_normal_dark;
		else if(STATE == Game.STATE.BLUE) 
			cell_image = game.assets.cell_blue;
		else 
			cell_image = game.assets.cell_red;
		
		canvas.drawBitmap(cell_image, top_left_corner.x, top_left_corner.y,	paint);
	}

	public void drawCellNumber(Canvas canvas, Game game, Paint txtPaint) {
		
		if(POSITION != Game.POSITION.START)
		{
			float x = top_left_corner.x + (game.CELL_WIDTH/2);
			float y = top_left_corner.y + (game.CELL_HEIGHT/2) + (txtPaint.getTextSize()/3);
			String txt = String.valueOf(this.value);
			
			canvas.drawText(txt, x, y, txtPaint);
		}
	}
	
}
