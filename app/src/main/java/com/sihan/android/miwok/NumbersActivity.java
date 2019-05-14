package com.sihan.android.miwok;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer = null;
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
        setContentView(R.layout.activity_numbers);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> listOfNumbers = new ArrayList<>();
        listOfNumbers.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        listOfNumbers.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        listOfNumbers.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        listOfNumbers.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        listOfNumbers.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        listOfNumbers.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        listOfNumbers.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        listOfNumbers.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        listOfNumbers.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        listOfNumbers.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        NumbersAdapter adapter = new NumbersAdapter(this, R.layout.word_list_item, listOfNumbers, R.color.category_numbers);

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
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this,word.getAudio());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Toast.makeText(NumbersActivity.this,"Audio Playing Finished", Toast.LENGTH_SHORT).show();
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
