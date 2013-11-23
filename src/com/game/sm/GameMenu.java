package com.game.sm;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Toast;


public class GameMenu extends Activity {
	
	GameMenuView menu_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		menu_view = new GameMenuView(this);
		setContentView(menu_view);
	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		menu_view.onPauseGameMenuView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		menu_view.onResumeGameMenuView();
	}

}


class GameMenuView extends SurfaceView implements OnTouchListener, SurfaceHolder.Callback{
    
    SurfaceHolder surfaceHolder;
    Bitmap menu;
    Bitmap menu_scaled;
    GraphicsHandler handler;
    Canvas canvas;
    int WIDTH;
    int HEIGHT;
    Sound sound;
    volatile boolean running = false;
     
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
 
  public GameMenuView(Context context) {
   super(context);
   // TODO Auto-generated constructor stub
   sound = new Sound(context);
   surfaceHolder = getHolder();
   surfaceHolder.addCallback(this);
   this.setOnTouchListener(this);
   menu = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
  }
   
  public void onResumeGameMenuView(){
	  
	  //Canvas canvas = surfaceHolder.lockCanvas();
   
  }
   
  public void onPauseGameMenuView(){
   
  }

@Override
protected void onDraw(Canvas canvas) {
	// TODO Auto-generated method stub
	super.onDraw(canvas);
	
	canvas.drawColor(Color.WHITE);
    canvas.drawBitmap(menu_scaled, 0, 0, paint);
	
}

@Override
public void surfaceChanged(SurfaceHolder holder, int format, int width,
		int height) {
	// TODO Auto-generated method stub
	
}

@Override
public void surfaceCreated(SurfaceHolder holder) {
	// TODO Auto-generated method stub
	Canvas canvas = null;
    try {
        canvas = holder.lockCanvas(null);
        this.HEIGHT = canvas.getHeight();
        this.WIDTH = canvas.getWidth();
        //this.handler = new GraphicsHandler(canvas, paint, getContext());
        menu_scaled = Bitmap.createScaledBitmap(menu, WIDTH, HEIGHT, true);
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
public void surfaceDestroyed(SurfaceHolder holder) {
	// TODO Auto-generated method stub
	
}

@Override
public boolean onTouch(View v, MotionEvent event) {
	
	// TODO ssk : remove this
	//getContext().startActivity(new Intent(getContext(),GamePlay.class));
	
	float X = event.getRawX();
	float Y = event.getRawY();
	
	//Toast.makeText(getContext(),"X : "+String.valueOf(X)+"Y : "+String.valueOf(Y), Toast.LENGTH_LONG).show();
	/*getContext().startActivity(new Intent(getContext(),
            GamePlay.class));*/
	//TODO ssk - put this in a new class TouchHandler
	
	
	if( X > (150*WIDTH)/480 && X< (340*WIDTH)/480 && Y>(250*HEIGHT)/800 && Y<(320*HEIGHT)/800 ){
		//Toast.makeText(getContext(),"play pressed", Toast.LENGTH_SHORT).show();
		//getContext().startActivity(new Intent(getContext(),GamePlay.class));
		sound.play_button_click();
		getContext().startActivity(new Intent(getContext(),GameConfig.class));
		
	
	}
	
	if( X > (150*WIDTH)/480 && X< (340*WIDTH)/480 && Y>(390*HEIGHT)/800 && Y<(450*HEIGHT)/800 ){
		
		
		//Toast.makeText(getContext(),"Scores pressed", Toast.LENGTH_SHORT).show();
		sound.play_button_click();
		getContext().startActivity(new Intent(getContext(),
	            Scores.class));
		
		
	}
	
	if( X > (150*WIDTH)/480 && X< (340*WIDTH)/480 && Y>(500*HEIGHT)/800 && Y<(565*HEIGHT)/800 ){
		//Toast.makeText(getContext(),"Options pressed", Toast.LENGTH_SHORT).show();
		sound.play_button_click();
		getContext().startActivity(new Intent(getContext(),
	            Options.class));
	}
	
	if( X > (150*WIDTH)/480 && X< (340*WIDTH)/480 && Y>(650*HEIGHT)/800 && Y<(700*HEIGHT)/800 ){
		//Toast.makeText(getContext(),"Help pressed", Toast.LENGTH_SHORT).show();
		sound.play_button_click();
		getContext().startActivity(new Intent(getContext(),
	            Instruction.class));
	}
	
	
	
	return false;
}

}
