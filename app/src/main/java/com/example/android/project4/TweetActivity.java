package com.example.android.project4;

import android.app.ListActivity;
import android.os.Bundle;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TweetActivity extends ListActivity{

    private TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
    private TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
//    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        setTitle("TimeLine");
//        listView= (ListView) findViewById(android.R.id.list);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("AmanDhapola")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();
        setListAdapter(adapter);
//        listView.setAdapter(adapter);
    }


}
