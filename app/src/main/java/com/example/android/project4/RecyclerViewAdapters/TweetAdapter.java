package com.example.android.project4.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.project4.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import synesketch.emotion.Emotion;
import synesketch.emotion.EmotionalState;
import twitter4j.Status;

/**
 * Created by amandhapola on 08/07/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {

    private ArrayList<Status> tweetList;
    private ArrayList<Pair<Status,EmotionalState>> tweetWithEmotionList;
    private Context context;
    private EmotionalState emotionalState=null;
    private HashMap<Integer,String> map=new HashMap<Integer,String>();


    public TweetAdapter(ArrayList<Pair<Status,EmotionalState>> tweetList, Context context,EmotionalState emotionalState) {
        this.tweetWithEmotionList = tweetList;
        this.context = context;
        this.emotionalState=emotionalState;
        map.put(Emotion.ANGER,"anger");
        map.put(Emotion.DISGUST,"disgust");
        map.put(Emotion.FEAR,"fear");
        map.put(Emotion.HAPPINESS,"happy");
        map.put(Emotion.NEUTRAL,"neutral");
        map.put(Emotion.SADNESS,"sad");
        map.put(Emotion.SURPRISE,"surprise");
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
        Pair<Status,EmotionalState> p = tweetWithEmotionList.get(position);
        Status status = p.first;
        EmotionalState emotionalState = p.second;
//        Status status=tweetList.get(position);
        holder.tv_username.setText(status.getUser().getName().toString());
        holder.tv_tweet.setText(status.getText().toString());
        if(emotionalState!=null){
            if(map.containsKey(emotionalState.getStrongestEmotion().getType())){
                holder.tv_emotion.setText(map.get(emotionalState.getStrongestEmotion().getType()));
            }
        }

        Picasso.with(context).load(status.getUser().getOriginalProfileImageURL()).transform(new CropCircleTransformation()).placeholder(R.drawable.rsz_1placeholder_twitter).into(holder.img_user);
    }

    @Override
    public int getItemCount() {
        return tweetWithEmotionList.size();
    }

    class TweetViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_tweet,tv_username,tv_emotion;
        private ImageView img_user;
        private View v;
        public TweetViewHolder(View itemView) {
            super(itemView);
            tv_username=(TextView) itemView.findViewById(R.id.tv_username);
            tv_emotion=(TextView) itemView.findViewById(R.id.tv_sentiment);
            img_user=(ImageView) itemView.findViewById(R.id.img_user);
            tv_tweet=(TextView) itemView.findViewById(R.id.tv_tweet);
            v=itemView;
        }
    }
}
