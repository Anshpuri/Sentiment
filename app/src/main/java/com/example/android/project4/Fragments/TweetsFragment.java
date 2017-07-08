package com.example.android.project4.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.project4.R;
import com.example.android.project4.RecyclerViewAdapters.TweetAdapter;
import com.example.android.project4.TwitterStreamConnection;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.conf.ConfigurationBuilder;


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
    private Observable<Status> twitterObservable;
    private TwitterStream twitterStream;
    private ConfigurationBuilder configurationBuilder;
    private FilterQuery tweetFilterQuery;

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
        adapter=new TweetAdapter(tweetList,getActivity().getApplicationContext());
        rv=(RecyclerView) v.findViewById(R.id.rv_tweets);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        ////////////////////////////////////////////////////////////////////////////////////////////////
        subject =PublishSubject.create();
        twitterStream= TwitterStreamConnection.getInstance().getTwitterStream();
        configurationBuilder= TwitterStreamConnection.getInstance().getCb();
        ////////////////////////////////////////////////////////////////////////////////////////////////
        tweetFilterQuery = new FilterQuery();
        tweetFilterQuery.track(new String[]{"narendra modi","india","modi"});
        tweetFilterQuery.language(new String[]{"en"});



////////////////////////////////////////////////////////////////////////////////////////////////
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.stop:
                        disposable.dispose();
                        break;

                    case R.id.start:
                        twitterObservable=gettwitterObservable();
                        twitterObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new Observer<Status>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        disposable=d;
                                    }

                                    @Override
                                    public void onNext(Status status) {
                                        tweetList.add(0,status);
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        break;
                }

                return true;
            }
        });

//        subject.subscribe(twitterObserver.getObserver());

        return v;
    }




    public Observable<Status> gettwitterObservable(){

        return Observable.create(new ObservableOnSubscribe<Status>() {
            @Override
            public void subscribe(final ObservableEmitter<Status> e) throws Exception {
                twitterStream.addListener(new StatusAdapter(){
                    @Override
                    public void onStatus(Status status) {
                        e.onNext(status);
                    }
                });
                twitterStream.filter(tweetFilterQuery);
            }
        });

//        return Observable.create(subscriber -> {
//                    twitterStream=new TwitterStreamFactory(configurationBuilder.build()).getInstance();
//
//                    if (twitterStream==null){
//                        Log.d(TAG, "twitterObservable: twitterstream NULL");
//                    }
//                    twitterStream.addListener(new StatusAdapter(){
//                        @Override
//                        public void onStatus(Status status) {
//                            subscriber.onNext(status);
//                        }
//                    });
//
//                    twitterStream.filter(tweetFilterQuery);
//
//                }
//        );

    }
}
