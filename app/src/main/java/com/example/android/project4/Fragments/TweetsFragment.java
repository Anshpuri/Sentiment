package com.example.android.project4.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.project4.R;
import com.example.android.project4.TwitterObserver;
import com.example.android.project4.TwitterStreamConnection;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;


public class TweetsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Disposable disposable;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TweetsFragment() {
        // Required empty public constructor
    }
    public static TweetsFragment newInstance(String param1, String param2) {
        TweetsFragment fragment = new TweetsFragment();
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
        View v =  inflater.inflate(R.layout.fragment_tweets, container, false);

        final PublishSubject<Status> subject =PublishSubject.create();
        TwitterStream twitterStream= TwitterStreamConnection.getInstance().getTwitterStream();
        twitterStream.addListener(new StatusAdapter(){
            @Override
            public void onStatus(Status status) {
                subject.onNext(status);
            }
        });

        TwitterObserver twitterObserver = new TwitterObserver();
        FilterQuery tweetFilterQuery = new FilterQuery();
        tweetFilterQuery.track(new String[]{"narendra modi","india","modi"});
        tweetFilterQuery.language(new String[]{"en"});
        twitterObserver.setFilterQuery(tweetFilterQuery);
        subject.subscribe(twitterObserver.getObserver());

        return v;
    }

}
