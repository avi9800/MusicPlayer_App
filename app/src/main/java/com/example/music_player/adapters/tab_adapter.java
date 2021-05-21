package com.example.music_player.adapters;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.music_player.fragments.Downloaded;
import com.example.music_player.fragments.Feed;
import com.example.music_player.fragments.Local;

import java.util.ArrayList;

public class tab_adapter extends FragmentPagerAdapter {
    public int tabcounts;
    ArrayList<String> ids,titles,albums,artists,genres,sources,trackNumbers,totalTrackCounts,durations,sites;
    ArrayList<Bitmap> images;




    public tab_adapter(FragmentManager fm, int numberoftabs, ArrayList<String> id, ArrayList<String> title, ArrayList<String> album, ArrayList<String> artist, ArrayList<String> genre, ArrayList<String> source, ArrayList<Bitmap> image, ArrayList<String> trackNumber, ArrayList<String> totalTrackCount, ArrayList<String> duration, ArrayList<String> site)
    {        super(fm);
        this.tabcounts=numberoftabs;
        titles=new ArrayList<>();
        ids=new ArrayList<>();
        albums=new ArrayList<>();
        artists=new ArrayList<>();
        genres=new ArrayList<>();
        sources=new ArrayList<>();
        images=new ArrayList<>();
        trackNumbers=new ArrayList<>();
        totalTrackCounts=new ArrayList<>();
        durations=new ArrayList<>();
        sites=new ArrayList<>();
        this.titles=title;
        this.ids=id;
        this.albums=album;
        this.artists=artist;
        this.genres=genre;
        this.sources=source;
        this.images=image;
        this.trackNumbers=trackNumber;
        this.totalTrackCounts=totalTrackCount;
        this.durations=duration;
        this.sites=site;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;


        switch (position){
            case 0:
                fragment=new Feed(ids,titles,albums,artists,genres,sources,images,trackNumbers,totalTrackCounts,durations,sites);
                Log.d("tab", String.valueOf(ids.size()));
                break;
            case 1:
                fragment=new Local();
                break;
            case 2:
                fragment=new Downloaded();
                break;
            default:
                fragment= null;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabcounts;
    }

}
