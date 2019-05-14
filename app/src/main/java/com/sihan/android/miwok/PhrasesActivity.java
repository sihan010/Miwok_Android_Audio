package com.sihan.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_phrases);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> listOfNumbers = new ArrayList<>();
        listOfNumbers.add(new Word("Are you coming","lutti",R.raw.phrase_are_you_coming));
        listOfNumbers.add(new Word("Come here","otiiko",R.raw.phrase_come_here));
        listOfNumbers.add(new Word("How are you feeling","tolookosu",R.raw.phrase_how_are_you_feeling));
        listOfNumbers.add(new Word("I'm coming","oyyisa",R.raw.phrase_im_coming));
        listOfNumbers.add(new Word("I'm feeling good","massokka",R.raw.phrase_im_feeling_good));
        listOfNumbers.add(new Word("Let's go","temmokka",R.raw.phrase_lets_go));
        listOfNumbers.add(new Word("My name is","kenekaku",R.raw.phrase_my_name_is));
        listOfNumbers.add(new Word("What is your name","kawinta",R.raw.phrase_what_is_your_name));
        listOfNumbers.add(new Word("Where are you going","wo'e",R.raw.phrase_where_are_you_going));
        listOfNumbers.add(new Word("Yes I'm Coming","na'aacha",R.raw.phrase_yes_im_coming));

        NumbersAdapter adapter = new NumbersAdapter(this,R.layout.word_list_item,listOfNumbers,R.color.category_phrases);

        ListView listView = findViewById(R.id.list_view_number);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = listOfNumbers.get(position);
                releaseMediaPlayerResource();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this,word.getAudio());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Toast.makeText(PhrasesActivity.this,"Audio Playing Finished", Toast.LENGTH_SHORT).show();
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