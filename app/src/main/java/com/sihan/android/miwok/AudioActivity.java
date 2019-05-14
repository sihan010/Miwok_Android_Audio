package com.sihan.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class AudioActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.number_eight);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(AudioActivity.this,"I'm done", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void button_stop(View view) {
        Button button_play = findViewById(R.id.button_pl);
        mediaPlayer.stop();
        button_play.setText("Play");
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.number_eight);
    }

    public void button_play(View view) {
        Button button_play = findViewById(R.id.button_pl);

        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            button_play.setText("Play");
        }
        else {
            mediaPlayer.start();
            button_play.setText("Pause");
        }

    }

}
