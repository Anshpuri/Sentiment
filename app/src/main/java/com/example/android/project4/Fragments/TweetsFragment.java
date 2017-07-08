package com.example.android.project4.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.project4.R;
import com.example.android.project4.RecyclerViewAdapters.TweetAdapter;
import com.example.android.project4.TwitterStreamConnection;

import java.util.ArrayList;

import io.reactivex.Observer;
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
    private PublishSubject<Status> subject=null;
    BottomNavigationView bottomNavigation;
    private RecyclerView rv;
    private ArrayList<Status> tweetList=new ArrayList<>();
    private TweetAdapter adapter;


    public static final String TAG="TWITTER_OBSERVER";
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
        ////////////////////////////////////////////////////////////////////////////////////////////////
        bottomNavigation=(BottomNavigationView) v.findViewById(R.id.bottom_navigation);
        adapter=new TweetAdapter(tweetList,v.getContext());
        rv=(RecyclerView) v.findViewById(R.id.rv_tweets);
        rv.setAdapter(adapter);
        ////////////////////////////////////////////////////////////////////////////////////////////////
        subject =PublishSubject.create();
        TwitterStream twitterStream= TwitterStreamConnection.getInstance().getTwitterStream();
        twitterStream.addListener(new StatusAdapter(){
            @Override
            public void onStatus(Status status) {
                subject.onNext(status);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////
        FilterQuery tweetFilterQuery = new FilterQuery();
        tweetFilterQuery.track(new String[]{"narendra modi","india","modi"});
        tweetFilterQuery.language(new String[]{"en"});
        twitterStream.filter(tweetFilterQuery);
////////////////////////////////////////////////////////////////////////////////////////////////
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.stop:
                        disposable.dispose();
                        break;
                    case R.id.start:
                        subject.subscribe(new Observer<Status>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable=d;
                            }

                            @Override
                            public void onNext(Status status) {
                                tweetList.add(0,status);
                                adapter.notifyDataSetChanged();
                                Log.d(TAG, "onNext: "+ status.getUser().getName().toString());
                                Log.d(TAG, "onNext: "+ status.getText().toString());

                                Log.d(TAG, "onNext: ---------------------------------------");
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                }

                return true;
            }
        });

//        subject.subscribe(twitterObserver.getObserver());

        return v;
    }

}
