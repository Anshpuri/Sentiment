package com.example.android.project4.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.project4.R;
import com.example.android.project4.RecyclerViewAdapters.TweetAdapter;
import com.example.android.project4.TwitterStreamConnection;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import synesketch.emotion.Emotion;
import synesketch.emotion.EmotionalState;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
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
    private StatusListener statusListener;
    private AVLoadingIndicatorView loader;

    private HashMap<Integer,String> Hmap=new HashMap<Integer,String>();

    private EmotionalState emotionalState=null;
    private synesketch.emotion.Empathyscope empathyscope = null;
    public static final String TAG="TWITTER_OBSERVER";

    private ArrayList<Pair<Status,EmotionalState>> tweetWithEmotionList= new ArrayList<Pair<Status, EmotionalState>>();
    // TODO: Rename and change types of parameters
    private String query;
    private String mParam2;


    int count_anger=0,count_disgust=0,count_fear=0,count_happy=0,count_neutral=0,count_sad=0,count_surprise=0;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
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
            query = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_tweets, container, false);

        preferences=getActivity().getSharedPreferences("emotionPref", Context.MODE_PRIVATE);
        editor=preferences.edit();
        Hmap.put(Emotion.ANGER,"anger");
        Hmap.put(Emotion.DISGUST,"disgust");
        Hmap.put(Emotion.FEAR,"fear");
        Hmap.put(Emotion.HAPPINESS,"happy");
        Hmap.put(Emotion.NEUTRAL,"neutral");
        Hmap.put(Emotion.SADNESS,"sad");
        Hmap.put(Emotion.SURPRISE,"surprise");

        ////////////////////////////////////////////////////////////////////////////////////////////////
        bottomNavigation=(BottomNavigationView) v.findViewById(R.id.bottom_navigation);
        adapter=new TweetAdapter(tweetWithEmotionList,getActivity().getApplicationContext(),emotionalState);
        rv=(RecyclerView) v.findViewById(R.id.rv_tweets);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        loader=(AVLoadingIndicatorView)v.findViewById(R.id.avi_loader);
        ////////////////////////////////////////////////////////////////////////////////////////////////
//        subject =PublishSubject.create();
        twitterStream= TwitterStreamConnection.getInstance().getTwitterStream();
        configurationBuilder= TwitterStreamConnection.getInstance().getCb();
        ////////////////////////////////////////////////////////////////////////////////////////////////
        tweetFilterQuery = new FilterQuery();
//        tweetFilterQuery.track(new String[]{"narendra modi","india","modi"});
        tweetFilterQuery.track(new String[]{query});
        tweetFilterQuery.language(new String[]{"en"});

//        twitterObservable=gettwitterObservable();
////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            empathyscope = synesketch.emotion.Empathyscope.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
////////////////////////////////////////////////////////////////////////////////////////////////
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.stop:
                        disposable.dispose();
                        twitterStream.removeListener(statusListener);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                twitterStream.cleanUp();
                                twitterStream.shutdown();
                            }
                        }).start();
                        editor.putInt("anger",count_anger);
                        editor.putInt("disgust",count_disgust);
                        editor.putInt("fear",count_fear);
                        editor.putInt("happy",count_happy);
                        editor.putInt("neutral",count_neutral);
                        editor.putInt("sad",count_sad);
                        editor.putInt("surprise",count_surprise);
                        editor.apply();
                        break;

                    case R.id.start:
                        loader.show();
                        twitterObservable=gettwitterObservable();
//                        twitterStream.addListener(statusListener);
                        twitterObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new Observer<Status>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        disposable=d;
                                    }

                                    @Override
                                    public void onNext(Status status) {
                                        loader.hide();
                                        if(emotionalState.getStrongestEmotion().getType()==Emotion.ANGER){count_anger++;}
                                        if(emotionalState.getStrongestEmotion().getType()==Emotion.DISGUST){count_disgust++;}
                                        if(emotionalState.getStrongestEmotion().getType()==Emotion.SADNESS){count_sad++;}
                                        if(emotionalState.getStrongestEmotion().getType()==Emotion.FEAR){count_fear++;}
                                        if(emotionalState.getStrongestEmotion().getType()==Emotion.HAPPINESS){count_happy++;}
                                        if(emotionalState.getStrongestEmotion().getType()==Emotion.NEUTRAL){count_neutral++;}
                                        if(emotionalState.getStrongestEmotion().getType()==Emotion.SURPRISE){count_surprise++;}
                                        tweetWithEmotionList.add(0,new Pair<Status, EmotionalState>(status,emotionalState));
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


        return v;
    }




    public Observable<Status> gettwitterObservable(){

        return Observable.create(new ObservableOnSubscribe<Status>() {
            @Override
            public void subscribe(final ObservableEmitter<Status> e) throws Exception {
                statusListener=new StatusListener() {
                    @Override
                    public void onStatus(Status status) {
                        try {
                            emotionalState = empathyscope.feel(status.getText().toString());


                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        e.onNext(status);
                    }

                    @Override
                    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

                    }

                    @Override
                    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

                    }

                    @Override
                    public void onScrubGeo(long userId, long upToStatusId) {

                    }

                    @Override
                    public void onStallWarning(StallWarning warning) {

                    }

                    @Override
                    public void onException(Exception ex) {

                    }
                };
//                twitterStream.addListener(new StatusAdapter(){
//                    @Override
//                    public void onStatus(Status status) {
//                        e.onNext(status);
//                    }
//                });
                twitterStream.addListener(statusListener);
                twitterStream.filter(tweetFilterQuery);
            }
        }).debounce(300, TimeUnit.MILLISECONDS);

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
