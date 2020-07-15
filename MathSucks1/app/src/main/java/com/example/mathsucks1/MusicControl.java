package com.example.mathsucks1;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicControl {
    private static MusicControl sInstance;
    private Context mContext;
    private MediaPlayer mediaPlayer;
    public MusicControl(Context context){
        mContext = context;
    }
    public static MusicControl getInstance(Context context){
        if(sInstance == null){
            sInstance = new MusicControl(context);
        }
        return sInstance;
    }
    public void playMusic(){
        mediaPlayer = MediaPlayer.create(mContext, R.raw.guitar);
        mediaPlayer.start();
    }
    public void stopMusic(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.seekTo(0);
        }
    }
}
