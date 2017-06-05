package com.example.linbing.blogfinal;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    SoundPool soundPool;
    HashMap<Integer, Integer> soundPoolMap;
    int soundID = 1;
    Vibrator v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.me);
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);//音效实例化
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(soundID, soundPool.load(this, R.raw.fallbacking, 1));
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);//震动实例化
        Button buttonPlayMediaPlayer = (Button) findViewById(R.id.playmediaplayer);
        Button buttonPauseMediaPlayer = (Button) findViewById(R.id.pausemediaplayer);
        Button buttonPlaySoundPool = (Button) findViewById(R.id.playsoundpool);
        Button buttonPauseSoundPool = (Button) findViewById(R.id.pausesoundpool);
        Button buttonGLT = (Button)findViewById(R.id.btn_GLT);
        Button buttonSOS = (Button)findViewById(R.id.btn_SOS);
        Button buttoncancel = (Button)findViewById(R.id.btn_cancel);


        buttonPlayMediaPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this, "播放背景音乐", Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonPauseMediaPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    Toast.makeText(MainActivity.this, "暂停背景音乐", Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonPlaySoundPool.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AudioManager audioManager =
                        (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                float curVolume =
                        audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume =
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float leftVolume = curVolume / maxVolume;
                float rightVolume = curVolume / maxVolume;
                int priority = 1;
                int no_loop = 0;
                float normal_playback_rate = 1f;
                soundPool.play(soundID, leftVolume, rightVolume, priority, no_loop, normal_playback_rate);
                Toast.makeText(MainActivity.this, "音效", Toast.LENGTH_LONG).show();
            }
        });
        buttonPauseSoundPool.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                soundPool.pause(soundID);
                Toast.makeText(MainActivity.this, "暂停音效", Toast.LENGTH_LONG).show();
            }
        });
        //震动300毫秒
        buttonGLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.vibrate(300);
                Toast.makeText(MainActivity.this, "300毫秒", Toast.LENGTH_LONG).show();
            }
        });
        //固定模式震动 SOS为例 莫斯code
        buttonSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dot = 200; // 莫斯密码点的震动时长
                int dash = 500; // 莫斯密码横的震动时长
                int short_gap = 200; // 点与横之间的时长间隔
                int medium_gap = 500; // 字母之间的时间间隔
                int long_gap = 1000; // 单词之间的时间间隔
                long[] pattern = {
                        0, // 立即开始
                        dot, short_gap, dot, short_gap, dot, // s
                        medium_gap,
                        dash, short_gap, dash, short_gap, dash, // o
                        medium_gap,
                        dot, short_gap, dot, short_gap, dot, // s
                        long_gap
                };
                v.vibrate(pattern, -1);//-1表示不重复
                Toast.makeText(MainActivity.this, "SOS", Toast.LENGTH_LONG).show();
            }
        });
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.cancel();//停止
                Toast.makeText(MainActivity.this, "停止震动", Toast.LENGTH_LONG).show();
            }
        });
    }
    Button.OnClickListener buttonPauseMediaPlayerOnClickListener
            = new Button.OnClickListener() {
        public void onClick(View v) {

            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                Toast.makeText(MainActivity.this,
                        "soundPool.pause()",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    Button.OnClickListener buttonPlaySoundPoolOnClickListener
            = new Button.OnClickListener(){
        public void onClick(View v) {
            AudioManager audioManager =
                    (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            float curVolume =
                    audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume =
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float leftVolume = curVolume/maxVolume;
            float rightVolume = curVolume/maxVolume;
            int priority = 1;
            int no_loop = 0; // 若想要重复播放，设置1
            float normal_playback_rate = 1f;
            soundPool.play(soundID, leftVolume, rightVolume, priority, no_loop,
                    normal_playback_rate);
            //Toast.makeText(AndroidAudioPlayer.this, “soundPool.play()”,
              //      Toast.LENGTH_LONG).show();
        }
    };
    Button.OnClickListener buttonPauseSoundPoolOnClickListener
            = new Button.OnClickListener(){
        public void onClick(View v) {
            soundPool.pause(soundID);
            //Toast.makeText(AndroidAudioPlayer.this, "soundPool.pause()",
         //           Toast.LENGTH_LONG).show();
        }
    };

}
