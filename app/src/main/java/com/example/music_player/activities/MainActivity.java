package com.example.music_player.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.music_player.R;
import com.example.music_player.adapters.tab_adapter;
import com.example.music_player.fragments.Downloaded;
import com.example.music_player.fragments.Feed;
import com.example.music_player.fragments.Local;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Feed.OnFragmentInteractionListener, Local.OnFragmentInteractionListener, Downloaded.OnFragmentInteractionListener {

    ProgressBar progressBar;
    ArrayList<String> id,title,album,artist,genre,source,image,trackNumber,totalTrackCount,duration,site;
    ArrayList<Bitmap> bitmaps;
    String data="";
    TabLayout tabLayout;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        title=new ArrayList<>();
        id=new ArrayList<>();
        album=new ArrayList<>();
        artist=new ArrayList<>();
        genre=new ArrayList<>();
        source=new ArrayList<>();
        image=new ArrayList<>();
        trackNumber=new ArrayList<>();
        totalTrackCount=new ArrayList<>();
        duration=new ArrayList<>();
        site=new ArrayList<>();
        bitmaps=new ArrayList<>();


        name=findViewById(R.id.now_playing);
        progressBar=findViewById(R.id.pd);
        tabLayout=findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Feed"));
        tabLayout.addTab(tabLayout.newTab().setText("Downloaded"));
        tabLayout.addTab(tabLayout.newTab().setText("Local"));
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"),Color.parseColor("#FD6A02"));


    }



    @Override
    protected void onResume() {
        super.onResume();
        try{
            if(data.isEmpty())
                new FetchData().execute("https://storage.googleapis.com/uamp/catalog.json");
        }
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    private  class FetchData extends AsyncTask<String,String,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection=null;

            try{
                URL url=new URL(strings[0]);
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();

                InputStream stream=new BufferedInputStream(connection.getInputStream());
                String read=convertStreamToString(stream);
                data=read;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null){
                    connection.disconnect();
                }
            }

            return  data;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(data);
                JSONArray songs=jsonObject.getJSONArray("music");
                if(id.size()<=songs.length()) {
                    for (int i = 0; i < songs.length(); i++) {
                        JSONObject songsJSONObject = songs.getJSONObject(i);
                        id.add(i, songsJSONObject.getString("id"));
                        title.add(i, songsJSONObject.getString("title"));
                        album.add(i, songsJSONObject.getString("album"));
                        artist.add(i, songsJSONObject.getString("artist"));
                        genre.add(i, songsJSONObject.getString("genre"));
                        source.add(i, songsJSONObject.getString("source"));
                        image.add(i, songsJSONObject.getString("image"));
                        trackNumber.add(i, songsJSONObject.getString("trackNumber"));
                        totalTrackCount.add(i, songsJSONObject.getString("totalTrackCount"));
                        duration.add(i, songsJSONObject.getString("duration"));
                        site.add(i, songsJSONObject.getString("site"));

                    }
                }
                Toast.makeText(MainActivity.this, ""+id.size(), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {

                for(int i=0;i<image.size();i++){
                    try {
                        URL imageurl=new URL(image.get(i));
                        HttpURLConnection image_connection=(HttpURLConnection)imageurl.openConnection();
                        image_connection.setDoInput(true);
                        image_connection.connect();
                        InputStream inputStream=new BufferedInputStream(image_connection.getInputStream());
                        Bitmap bt= BitmapFactory.decodeStream(inputStream);
                        bitmaps.add(i,bt);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);

                }


                final ViewPager viewPager=findViewById(R.id.viewpager);
                final PagerAdapter pagerAdapter=new tab_adapter(getSupportFragmentManager(),tabLayout.getTabCount(),id,title,album,artist,genre,source,bitmaps,trackNumber,totalTrackCount,duration,site);
                viewPager.setAdapter(pagerAdapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }

        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}