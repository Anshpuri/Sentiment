package com.example.android.project4.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.project4.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import synesketch.emotion.EmotionalState;
import twitter4j.Status;

/**
 * Created by amandhapola on 08/07/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {

    private ArrayList<Status> tweetList;
    private Context context;
    private EmotionalState emotionalState=null;

    public TweetAdapter(ArrayList<Status> tweetList, Context context,EmotionalState emotionalState) {
        this.tweetList = tweetList;
        this.context = context;
        this.emotionalState=emotionalState;
    }
    public TweetAdapter(ArrayList<Status> tweetList, Context context) {
        this.tweetList = tweetList;
        this.context = context;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =li.inflate(R.layout.tweet_list_item,null);
        return new TweetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        Status status=tweetList.get(position);
        holder.tv_username.setText(status.getUser().getName().toString());
        holder.tv_tweet.setText(status.getText().toString());
        if(emotionalState!=null)
            holder.tv_emotion.setText(emotionalState.getStrongestEmotion().toString());

        Picasso.with(context).load(status.getUser().getMiniProfileImageURL()).into(holder.img_user);
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    class TweetViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_tweet,tv_username,tv_emotion;
        private ImageView img_user;
        private View v;
        public TweetViewHolder(View itemView) {
            super(itemView);
            tv_username=(TextView) itemView.findViewById(R.id.tv_username);
            tv_emotion=(TextView) itemView.findViewById(R.id.tv_sentiment);
            tv_tweet=(TextView) itemView.findViewById(R.id.tv_tweet);
            v=itemView;
        }
    }
}
