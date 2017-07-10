package com.example.android.project4.BroadCastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

public class ComposeTweetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            // success
            final Long tweetId = intent.getLongExtra(TweetUploadService.EXTRA_TWEET_ID,1);
            Toast.makeText(context,"Tweeted!",Toast.LENGTH_SHORT).show();
        } else {
            // failure
            final Intent retryIntent = intent.getParcelableExtra(TweetUploadService.EXTRA_RETRY_INTENT);
        }

    }
}
