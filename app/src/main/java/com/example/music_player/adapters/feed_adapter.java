package com.example.music_player.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player.R;
import com.example.music_player.activities.MusicPlayerActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class feed_adapter extends RecyclerView.Adapter<feed_adapter.ViewHolder> {


    private Context context;
    ArrayList<String> titles,sources,durations;
    ArrayList<Bitmap> images;
    static Boolean isplaying=false;
    MediaPlayer player;

    public feed_adapter(Context context, ArrayList<String> title, ArrayList<Bitmap> image, ArrayList<String> source, ArrayList<String> duration){
        this.context=context;
        titles=new ArrayList<>();
        images=new ArrayList<>();
        sources=new ArrayList<>();
        durations=new ArrayList<>();
        this.images=image;
        this.titles=title;
        this.sources=source;
        this.durations=duration;

    }

    @NonNull
    @Override
    public feed_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new feed_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull feed_adapter.ViewHolder holder, final int position) {
        holder.feed_item_text.setText(titles.get(position));
        holder.song_image.setImageBitmap(images.get(position));
        holder.feed_item_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Playing "+titles.get(position), Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context, MusicPlayerActivity.class);
                i.putExtra("song_name",titles.get(position));
                i.putExtra("song_url",sources.get(position));
                i.putExtra("song_duration",durations.get(position));
                i.putExtra("song_image",images.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView feed_item_text;
        ImageView song_image,download;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            feed_item_text=itemView.findViewById(R.id.feed_item_text);
            song_image=itemView.findViewById(R.id.online_image);
            download=itemView.findViewById(R.id.download_button);

        }

    }

    public class PlayMusic extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... strings) {
            try{
                Uri uri = Uri.parse(strings[0]);
                player = new MediaPlayer();
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDataSource(context, uri);
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
}
