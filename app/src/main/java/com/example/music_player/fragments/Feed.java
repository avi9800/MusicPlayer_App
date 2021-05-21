package com.example.music_player.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.music_player.R;
import com.example.music_player.adapters.feed_adapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Feed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Feed extends Fragment {

    ArrayList<String> ids,titles,albums,artists,genres,sources,trackNumbers,totalTrackCounts,durations,sites;
    ArrayList<Bitmap> images;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Feed() {
        // Required empty public constructor
    }

    public Feed(ArrayList<String> id, ArrayList<String> title, ArrayList<String> album, ArrayList<String> artist, ArrayList<String> genre, ArrayList<String> source, ArrayList<Bitmap> image, ArrayList<String> trackNumber, ArrayList<String> totalTrackCount, ArrayList<String> duration, ArrayList<String> site) {
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Feed.
     */
    // TODO: Rename and change types and number of parameters
    public static Feed newInstance(String param1, String param2) {
        Feed fragment = new Feed();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.Feed_Recyclerview);
        feed_adapter ad=new feed_adapter(getActivity(),titles,images,sources,durations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(ad);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}