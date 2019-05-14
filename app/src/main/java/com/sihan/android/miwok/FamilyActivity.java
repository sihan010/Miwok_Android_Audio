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

public class FamilyActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_family);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> listOfNumbers = new ArrayList<>();
        listOfNumbers.add(new Word("Daughter","lutti",R.drawable.family_daughter,R.raw.family_daughter));
        listOfNumbers.add(new Word("Father","otiiko",R.drawable.family_father,R.raw.family_father));
        listOfNumbers.add(new Word("Grandfather","tolookosu",R.drawable.family_grandfather,R.raw.family_grandfather));
        listOfNumbers.add(new Word("Grandmother","oyyisa",R.drawable.family_grandmother,R.raw.family_grandmother));
        listOfNumbers.add(new Word("Mother","massokka",R.drawable.family_mother,R.raw.family_mother));
        listOfNumbers.add(new Word("Older Brother","temmokka",R.drawable.family_older_brother,R.raw.family_older_brother));
        listOfNumbers.add(new Word("Older Sister","kenekaku",R.drawable.family_older_sister,R.raw.family_older_sister));
        listOfNumbers.add(new Word("Son","kawinta",R.drawable.family_son,R.raw.family_son));
        listOfNumbers.add(new Word("Younger Brother","kawinta",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        listOfNumbers.add(new Word("Younger Sister","kawinta",R.drawable.family_younger_sister,R.raw.family_younger_sister));

        NumbersAdapter adapter = new NumbersAdapter(this,R.layout.word_list_item,listOfNumbers, R.color.category_family);

        ListView listView = findViewById(R.id.list_view_family);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = listOfNumbers.get(position);
                releaseMediaPlayerResource();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this,word.getAudio());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Toast.makeText(FamilyActivity.this,"Audio Playing Finished", Toast.LENGTH_SHORT).show();
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
