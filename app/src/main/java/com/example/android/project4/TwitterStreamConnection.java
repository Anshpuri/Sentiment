package com.example.android.project4;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by amandhapola on 08/07/17.
 */

public class TwitterStreamConnection {

    private ConfigurationBuilder cb;
    private TwitterStream twitterStream;

    public ConfigurationBuilder getCb() {
        return cb;
    }


    public TwitterStream getTwitterStream() {
        return twitterStream;
    }

    private static final TwitterStreamConnection ourInstance = new TwitterStreamConnection();

    public static TwitterStreamConnection getInstance() {
        return ourInstance;
    }

    private TwitterStreamConnection() {
        cb=new ConfigurationBuilder();
        cb.setOAuthConsumerKey("dKUQo0IIf1ZYo3EtMNdbPT374")
                .setOAuthConsumerSecret("lnRA8izXCEG6mRMePUV88k6XL3SzUs5fe3iOtKbArMFlaw3jio")
                .setOAuthAccessToken("3565074266-3Zb3chDQv8FZTeVc5JFNCqqbSFU65f8oF8bNXRl")
                .setOAuthAccessTokenSecret("SmcYCHvaTOZobS3ecK2sPiZQOAFlr1wgGIexVECl2Mlk3");

        twitterStream=new TwitterStreamFactory(cb.build()).getInstance();
    }
}
