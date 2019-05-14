package com.sihan.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer = null;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayerResource();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> listOfNumbers = new ArrayList<>();
        listOfNumbers.add(new Word("Black","lutti",R.drawable.color_black,R.raw.color_black));
        listOfNumbers.add(new Word("Brown","otiiko",R.drawable.color_brown,R.raw.color_brown));
        listOfNumbers.add(new Word("Dusty Yellow","tolookosu",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        listOfNumbers.add(new Word("Gray","oyyisa",R.drawable.color_gray,R.raw.color_gray));
        listOfNumbers.add(new Word("Green","massokka",R.drawable.color_green,R.raw.color_green));
        listOfNumbers.add(new Word("Mustard Yellow","temmokka",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        listOfNumbers.add(new Word("Red","kenekaku",R.drawable.color_red,R.raw.color_red));
        listOfNumbers.add(new Word("White","kawinta",R.drawable.color_white,R.raw.color_white));

        NumbersAdapter adapter = new NumbersAdapter(this,R.layout.word_list_item,listOfNumbers,R.color.category_colors);

        ListView listView = findViewById(R.id.list_view_color);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = listOfNumbers.get(position);
                releaseMediaPlayerResource();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this,word.getAudio());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Toast.makeText(ColorsActivity.this,"Audio Playing Finished", Toast.LENGTH_SHORT).show();
                            releaseMediaPlayerResource();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayerResource();
        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }

    private void releaseMediaPlayerResource(){
        if(mMediaPlayer!=null){
            mMediaPlayer.release();
            mMediaPlayer=null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
