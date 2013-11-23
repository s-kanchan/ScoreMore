package com.game.sm;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.Log;

public class Game {
	
	//ENUMS
	public static enum TURN {PLAYER1,PLAYER2};
	public static enum POSITION {START,TOP,LEFT,RIGHT,BOTTOM,MIDDLE}; //USED BY CELLS
	public static enum TYPE {AI,HUMAN};
	public static enum STATE {INVALID, VALID, RED, BLUE};
	public static enum LEVEL {EASY, MEDIUM, HARD};
	
	SharedPreferences sharedPreferences;
	//CONSTANTS
	int HEIGHT;
	int WIDTH;
	int ROWS;
	int COLS;
	
	int TOP;
	int BOTTOM;
	int LEFT;
	int RIGHT;
	int CELL_WIDTH;
	int CELL_HEIGHT;
	
	
	//VARIABLES
	volatile boolean isRunning;
	int player1_score;
	int player2_score;
	int last_cell; 
	
	String player1_name;
	String player2_name;
	
	//ENUM INSTANCES USED BY GAME
	LEVEL level;
	TURN turn;
	Cell cells[];
	TYPE type;
	Assets assets;

	
	public Game(Canvas canvas, int rows, int cols, Context context, boolean isSinglePlayer)
	{
		//Log.i("Game", "Game constructor");
		sharedPreferences = context.getSharedPreferences("scoremore", Context.MODE_PRIVATE);
		HEIGHT = canvas.getHeight();
		WIDTH = canvas.getWidth();
		TOP = (150*HEIGHT)/800;
		BOTTOM = (775*HEIGHT)/800;
		LEFT =  (25*WIDTH)/480;
		RIGHT = (455*WIDTH)/480;
		
		ROWS = rows;
		COLS=cols;
		player1_score = 0;
		player2_score = 0;
	
		CELL_WIDTH = (int) (RIGHT-LEFT)/COLS;
		//CELL_HEIGHT = (int) (BOTTOM-TOP)/ROWS;
		CELL_HEIGHT = CELL_WIDTH;
		int offset = BOTTOM - TOP;
		while(CELL_HEIGHT*ROWS > offset)
			ROWS--;
		
		BOTTOM = TOP + CELL_HEIGHT*rows;
		
		last_cell = 0;
		isRunning = true;
		turn = TURN.PLAYER1;
		
		// TODO Change this
		/*
		player1_name = "surendra";
		player2_name = "tejas";
		
		level = getLevel();
		
		if(isSinglePlayer)
		{
			type = TYPE.AI;
			player2_name = "Computer";
		}
		*/
		level = getLevel();
		configure_game();
		
		assets = new Assets(this, context);
		cells = new Cell[ ROWS*COLS ];
		createCells(cells);
	}


	private void configure_game() {
		
	player1_name = sharedPreferences.getString("player1_name", "Player1");
	player2_name = sharedPreferences.getString("player2_name", "player2");
	Boolean is_AI = sharedPreferences.getBoolean("is_AI", true);
	if(is_AI)
		type = TYPE.AI;
	else
		type = TYPE.HUMAN;
	
		
	}


	private LEVEL getLevel() {
		LEVEL level = Game.LEVEL.EASY;
		
		return level;
	}


	private void createCells(Cell cells[]) {
		
		for(int i=0;i<(ROWS*COLS);i++)
		{
			cells[i] = new Cell(i, this);
		}
		cells[0].POSITION = Game.POSITION.START;
		cells[0].value = 0;
		
		// TODO REMOVE THIS
		cells[1].STATE = Game.STATE.VALID;
		cells[5].STATE = Game.STATE.VALID;
	}


	public boolean isValidTouch(float eventX, float eventY) {
		boolean is_valid = false;
		if(eventX > LEFT && eventX < RIGHT && eventY > TOP && eventY < BOTTOM)
		{
			//cells[10].STATE = Game.STATE.VALID;
			int cur_row = (int) ((eventY-TOP)/CELL_HEIGHT);
			int cur_col = (int) ((eventX-LEFT)/CELL_WIDTH);
			int target_cell = cur_col + (cur_row*COLS);
			//cells[target_cell].STATE = Game.STATE.INVALID;
			//Log.i("GAME", String.valueOf(target_cell));
			//cells[10].STATE = Game.STATE.VALID;
			if(cells[target_cell].STATE == STATE.VALID)
				is_valid = true;
			validate(target_cell);
			//cells[target_cell].STATE = Game.STATE.BLUE;
			
		}
		return is_valid;
	}


	private void validate(int target_cell) {
		
		if(cells[target_cell].STATE != Game.STATE.VALID)
			return;
		
		for(int i = 1; i < (ROWS*COLS); i++)
			if(cells[i].STATE == Game.STATE.VALID)
				cells[i].STATE = Game.STATE.INVALID;
		
		if(turn == Game.TURN.PLAYER1)
		{
			cells[target_cell].STATE = Game.STATE.RED;
			turn = Game.TURN.PLAYER2;
			player1_score += cells[target_cell].value;
		}
		else
		{
			cells[target_cell].STATE = Game.STATE.BLUE;
			turn = Game.TURN.PLAYER1;
			player2_score += cells[target_cell].value;
		}
		
		if(target_cell == (ROWS*COLS)-1)
		{
			isRunning = false;
			return;
		}
		
		//TODO critical part
		//Updating valid buttons for next move
		ArrayList<Integer> ls = new ArrayList<Integer>();
		int row = (int) target_cell/COLS;
		int col = (int) target_cell%COLS;
		
		switch (cells[target_cell].POSITION) {
		case TOP:
			if(target_cell < (COLS-1))			// Move to right or down
				ls.add(target_cell+1);
			ls.add( target_cell + COLS);
				
			break;
			
		case LEFT:							// Move to right or down
			if(row < (ROWS-1))
				ls.add(target_cell + COLS);
			ls.add( target_cell + 1);
			
			break;

		case RIGHT:							// Move to down or left
			if(row < (ROWS-1))
				ls.add(target_cell + COLS);
			ls.add( target_cell - 1);
			 
			break;
		
		case BOTTOM:						// Move to up or right
			if(col < (COLS-1))
				ls.add(target_cell+1);
			ls.add( target_cell - COLS);
			
			break;
		
		default:							// Move to any possible 4 direction
			if(isValidForNextMove(target_cell - 1))
				ls.add( target_cell - 1);
			
			if(isValidForNextMove(target_cell + 1))
				ls.add( target_cell + 1);
			
			if(isValidForNextMove(target_cell - COLS))
				ls.add( target_cell - COLS );
			
			if(isValidForNextMove(target_cell + COLS))
				ls.add( target_cell + COLS );
			
			
			
			break;
		}
		
		 for (Integer index : ls) 
		 {
			if(cells[index].STATE == STATE.INVALID)
				cells[index].STATE = STATE.VALID;
		 }
		
	}


	private boolean isValidForNextMove(int index) {
		
		int i=0;
		boolean isValid = true;
		//return true;
		
		Log.i("GAME", "Checking for : "+String.valueOf(cells[index].value));
		
		for(i = index; (i%COLS != 0); i-- )
			if(cells[i].STATE == STATE.RED || cells[i].STATE == STATE.BLUE )
				break;
		Log.i("GAME", "Halted at : "+String.valueOf(cells[i].value));
		if(cells[i].STATE != STATE.RED && cells[i].STATE != STATE.BLUE)
			return true;
		
		for(i = index; (i%COLS != 0); i++ )
			if(cells[i].STATE == STATE.RED || cells[i].STATE == STATE.BLUE)
				{
				isValid = false;
				break;
				}
		//Log.i("GAME", "Halted at : "+String.valueOf(cells[i].value));
		if(isValid)
		{	//i--;
			//if( cells[i].STATE != STATE.RED || cells[i].STATE != STATE.BLUE)
				return true;
		}
		
		for(i = index; ((int)(i/COLS) != 0); i -= COLS )
			if(cells[i].STATE == STATE.RED || cells[i].STATE == STATE.BLUE)
				break;
		Log.i("GAME", "Halted at : "+String.valueOf(cells[i].value));
		if(  cells[i].STATE != STATE.RED && cells[i].STATE != STATE.BLUE)
			return true;
		
		for(i = index; ((int)(i/COLS) != (ROWS-1)); i += COLS )
			if(cells[i].STATE == STATE.RED || cells[i].STATE == STATE.BLUE)
				break;
		Log.i("GAME", "Halted at : "+String.valueOf(cells[i].value));
		if( cells[i].STATE != STATE.RED && cells[i].STATE != STATE.BLUE)
			return true;
		
		
		return false; 
	}


	public void makeNextMove() {
		
		if(this.level == LEVEL.EASY)
			makeNextEasyMove();
		
	}


	private void makeNextEasyMove() {
		int max = Integer.MIN_VALUE;
		int max_index = -1;
		for (Cell cell : cells) 
		{
			if(cell.STATE == STATE.VALID && cell.value > max)
				{
				max = cell.value;
				max_index = cell.ID;
				}
		}
		//TODO wait for some time and make a move
		
		validate(max_index);
		
		
		
		
		Log.i("GAME", "MAKING MOVE TO : "+String.valueOf(cells[max_index].value));
		//validate(max_index);
	}


	
}
