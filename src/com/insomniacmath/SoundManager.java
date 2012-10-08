package com.insomniacmath;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;


public class SoundManager implements Soundable {
    private SoundPool mSoundPool;
    private HashMap mSoundPoolMap;
    private AudioManager mAudioManager;
    private Context mContext;

    public static float effectToMuzVolumeVolume = 0.5f;


    public SoundManager(Context context) {
        mContext = context;
        initSounds();
    }

    public void initSounds() {

        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        mSoundPoolMap = new HashMap<Integer, Integer>();
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        addSound(BUTTON1, R.raw.button_16);
        addSound(BUTTON2, R.raw.button_30);
    }

    public void addSound(int index, int SoundID) {
        mSoundPoolMap.put(index, mSoundPool.load(mContext, SoundID, 1));
    }

    public void playSound(int index) {
        float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * (effectToMuzVolumeVolume);
        streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 10;
        if (mSoundPool != null && mSoundPoolMap != null)
            mSoundPool.play((Integer) mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
    }


}
