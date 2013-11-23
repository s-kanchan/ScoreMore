package com.game.sm;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {
	
	private SoundPool soundPool;
    private int button_click;
    private AudioManager audioManager;
    float volume;
    
    public Sound( Context context)
    {
    	soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    	button_click = soundPool.load(context, R.raw.button_click, 1);
    	audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    	
    	float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    
        volume = actualVolume / maxVolume;
    }
    
    public void play_button_click()
    {
    	soundPool.play(button_click, volume, volume, 1, 0, 1f);
    }

}
