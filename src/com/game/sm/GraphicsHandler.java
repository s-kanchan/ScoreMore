package com.game.sm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;

public class GraphicsHandler {


	private Paint paint;
	private Paint txtPaint;
	private Paint scorePaint;

	public GraphicsHandler(Canvas canvas, Paint paint, Context context) { 
		// TODO Auto-generated constructor stub
		this.paint = paint; 
		this.txtPaint = getTextPaint(context);
		this.scorePaint = getScorePaint(context);
	}

	private Paint getScorePaint(Context context) {
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		int scaled_font_size = context.getResources().getDimensionPixelSize(R.dimen.score_font_size);
		float[] direction = new float[] {0.0f, -1.0f, 0.5f};
		MaskFilter filter = new EmbossMaskFilter(direction, 0.8f, 15f, 1f);
		paint.setTextSize(scaled_font_size);
		paint.setMaskFilter(filter);
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(2);
		paint.setColor(Color.rgb(98, 33, 10));
		//paint.setColor(Color.rgb(165, 62, 25));
		return paint;
	}

	private Paint getTextPaint(Context context) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		int scaled_font_size = context.getResources().getDimensionPixelSize(R.dimen.font_size);
		float[] direction = new float[] {0.0f, -1.0f, 0.5f};
		MaskFilter filter = new EmbossMaskFilter(direction, 0.8f, 15f, 1f);
		paint.setTextSize(scaled_font_size);
		paint.setMaskFilter(filter);
		paint.setStyle(Style.FILL);
		paint.setTextAlign(Align.CENTER);
		paint.setStrokeWidth(2);
		paint.setColor(Color.rgb(98, 33, 10));
		//paint.setColor(Color.rgb(165, 62, 25));
		return paint;
	}

	public void drawBoard(Canvas canvas, Game game) {
		// TODO Auto-generated method stub
		
		for(int i=0; i<(game.ROWS*game.COLS); i++)
		{
			game.cells[i].drawCell(canvas, game, paint);
			game.cells[i].drawCellNumber(canvas, game, txtPaint);
		}
		
	}

	public void drawBackground(Canvas canvas, Game game) {
		
		canvas.drawBitmap(game.assets.background, 0, 0, paint);
	}

	public void drawScore(Canvas canvas, Game game) {
		
		String score1 = game.player1_name + " : " + String.valueOf(game.player1_score);
		String score2 = game.player2_name + " : " + String.valueOf(game.player2_score);
		
		scorePaint.setTextAlign(Align.LEFT);
		canvas.drawText(score1, game.LEFT, game.LEFT*2, scorePaint);
		
		scorePaint.setTextAlign(Align.RIGHT);
		canvas.drawText(score2, game.RIGHT, game.LEFT*2, scorePaint);
		
		
	}

	public void drawWinner(Canvas canvas, Game game) {
		
		int x = game.WIDTH/2;
		int y = game.HEIGHT/2;
		String winner;
		if(game.player1_score == game.player2_score)
			winner = "GAME DRAW!";
		else if(game.player1_score > game.player2_score)
			winner= game.player1_name + " WINS!";
		else 
			winner= game.player2_name + " WINS!";
		
		canvas.drawText(winner, x, y, txtPaint);
		
	}

}
