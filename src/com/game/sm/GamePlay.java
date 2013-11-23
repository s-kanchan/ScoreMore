package com.game.sm;

import com.game.sm.Game.TURN;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class GamePlay extends Activity {

	GameView gv;
	boolean isSinglePlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		isSinglePlayer = true;
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		gv = new GameView(this, isSinglePlayer);
		setContentView(gv);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i("GamePlay", "onPause called");
		super.onPause();
		gv.onPauseGameView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i("GamePlay", "onResume Called");
		super.onResume();
		gv.onResumeGameView();
	}

}

class GameView extends SurfaceView implements  SurfaceHolder.Callback
{

	
	int HEIGHT;
	int WIDTH;
	int ROWS =7;
	int COLS = 5;
	static int count =0;
	
	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	SurfaceHolder surfaceHolder;
	Canvas canvas;
	GraphicsHandler gfx;
	Game game;
	Sound sound;
	ScoreHandler score_handler;
	boolean isSinglePlayer;
	
	public GameView(Context context, boolean isSinglePlayer) {
		super(context);
		
		sound = new Sound(context);
		score_handler = new ScoreHandler(context);
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		//TODO Change this
		this.isSinglePlayer = isSinglePlayer;
		//this.setOnTouchListener(this);
	}

	

	public void onResumeGameView() {
		
		Log.i("GamePlay", "OnResumeGameView Called");
		
	}



	public void onPauseGameView() {
		// TODO Auto-generated method stub
		Log.i("GamePlay", "onPauseGameView Called");
		
	}



	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		Log.i("GamePlay", "surfaceCreated Called");
		
	    try {
	    	surfaceHolder = holder;
	        canvas = holder.lockCanvas(null);
	        this.HEIGHT = canvas.getHeight();
	        this.WIDTH = canvas.getWidth();
	        
	        gfx = new GraphicsHandler(canvas, paint, getContext());
	        if(game == null)
	        	game = new Game(canvas, ROWS, COLS, getContext(), isSinglePlayer);
	        
	        synchronized (holder) {
	            onDraw(canvas);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (canvas != null) {
	            holder.unlockCanvasAndPost(canvas);
	        }
	    }
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Log.i("GamePLay", "onDraw called");
		
		gfx.drawBackground(canvas, game);
		//gfx.drawBoard(canvas, game);
		gfx.drawScore(canvas, game);
		
		if(game.isRunning)
		{
			gfx.drawBoard(canvas, game);
		}
		else
		{
			gfx.drawWinner(canvas, game);
			String name;
			if(game.turn == Game.TURN.PLAYER1)
				name = game.player1_name;
			else
				name = game.player2_name;
			//Thread t = new Thread(new Dialog(score_handler, (int)abs(game.player1_score - game.player2_score), name, getContext()));

			
			show_dialog(score_handler,Math.abs(game.player1_score - game.player2_score), name, getContext());
		}
		super.onDraw(canvas);
		/*
		if(game.type == Game.TYPE.AI && game.turn == Game.TURN.PLAYER2)
		{
			game.makeNextMove();
			onDraw(canvas);
		}
		*/
		//super.onDraw(canvas);
	}

	private void show_dialog(final ScoreHandler score_handler, final int score, final String name,Context context) {
		
		Log.i("GAMEPLAY", "show_dialog_called");
		int top_score = score_handler.getTopScore();
		Log.i("GAMEPLAY", "TOP_SCORE : "+String.valueOf(top_score));
		if(score > top_score)
		{
		
			//Thread t = new Thread(new Dialog(score_handler, score, name, context));
			//t.start();
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			 
			// set title
			alertDialogBuilder.setTitle("High Score!");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Do you want to save!")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						//MainActivity.this.finish();
						score_handler.update(score, name);
						
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			
		}
	}



	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		float eventX = event.getRawX();
		float eventY = event.getRawY();
		
		switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
        	
            if(game.isValidTouch(eventX, eventY))
            	{
            	sound.play_button_click();
	            try {
	    	        canvas = surfaceHolder.lockCanvas(null);
	    	        synchronized (surfaceHolder) {
	    	            onDraw(canvas);
	    	            if(game.type == Game.TYPE.AI && game.turn == Game.TURN.PLAYER2)
	    	    		{
	    	    			game.makeNextMove();
	    	    			//Thread.sleep(1000);
	    	    			onDraw(canvas);
	    	    		}
	    	            
	    	        }
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    } finally {
	    	        if (canvas != null) {
	    	            surfaceHolder.unlockCanvasAndPost(canvas);
	    	        }
	    	    }
	            	}
            
            break;
            
        case MotionEvent.ACTION_MOVE:
        	
            break;
            
        case MotionEvent.ACTION_UP:
        	
            break;
		}
		
		
		return true;
	}
}

class Dialog implements Runnable
{
	
	
	private Context context;
	ScoreHandler handler;
	int score;
	String name;

	public Dialog(ScoreHandler handler, int score, String name, Context context) {
		Log.i("DIALOG", "inside constructor");
		this.context = context;
		this.handler = handler;
		this.score = score;
		this.name = name;
	}
	
	@Override
	public void run() {
		
		Log.i("RUN", "running");
		
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
			// set title
			alertDialogBuilder.setTitle("High Score!");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Do you want to save!")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						//MainActivity.this.finish();
						handler.update(score, name);
						
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
		
	}
	
}

