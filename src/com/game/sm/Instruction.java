package com.game.sm;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Instruction extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.instruction);
		
		TextView tv = (TextView) findViewById(R.id.instruction_view);
		
		Paint paint = tv.getPaint();
		int scaled_font_size = this.getResources().getDimensionPixelSize(R.dimen.font_size);
		float[] direction = new float[] {0.0f, -1.0f, 0.5f};
		MaskFilter filter = new EmbossMaskFilter(direction, 0.8f, 15f, 1f);
		paint.setTextSize(scaled_font_size);
		paint.setMaskFilter(filter);
		paint.setColor(Color.rgb(98, 33, 10));
		
		tv.getPaint().set(paint);
		setContentView(R.layout.instruction);
		
		super.onCreate(savedInstanceState);
	}
	

}
