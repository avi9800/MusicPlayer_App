package com.example.music_player.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.music_player.R;

import java.io.IOException;
import java.net.MalformedURLException;

public class MusicPlayerActivity extends AppCompatActivity {

String songname,duration,url;
Bitmap song_image;
Boolean playing=true;
Boolean paused=false;
ImageButton previous,play,pause,next;
ImageView show_song_image;
MediaPlayer player;
SeekBar seekBar;
TextView name;
int length=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplayer);
        Bundle bundle=getIntent().getExtras();
        song_image=bundle.getParcelable("song_image");
        songname=bundle.getString("song_name");
        duration=bundle.getString("song_duration");
        url=bundle.getString("song_url");

        previous=findViewById(R.id.previous_button);
        name=findViewById(R.id.songname_musicplayer);
        play=findViewById(R.id.playing_button_player_layout);
        pause=findViewById(R.id.pause_button_player_layout);
        next=findViewById(R.id.next_button);
        show_song_image=findViewById(R.id.songImagemusic);
        seekBar=findViewById(R.id.song_seekbar);

        seekBar.setMax(Integer.parseInt(duration));
        MusicPlayer(song_image,songname,duration,url);


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicPlayer(song_image,songname,duration,url);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicPlayer(song_image,songname,duration,url);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(playing) {
                        pause.setVisibility(View.INVISIBLE);
                        play.setVisibility(View.VISIBLE);
                        playing = false;
                        player.pause();
                        length = player.getCurrentPosition();
                    }
                }
            });
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!playing)
                    pause.setVisibility(View.VISIBLE);
                    play.setVisibility(View.INVISIBLE);
                    player.seekTo(length);
                    player.start();
                    playing=true;
                }
            });


    }

    private void MusicPlayer(Bitmap song_image, String songname, String duration, String url){
        show_song_image.setImageBitmap(song_image);
        name.setText(songname);
        new PlayMusic().execute(url);
    }


    public class PlayMusic extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try{
                Uri uri = Uri.parse(strings[0]);
                player = new MediaPlayer();
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDataSource(MusicPlayerActivity.this, uri);
                player.prepare();
                player.start();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(player!=null) {
            player.release();
            finish();
        }
    }
}