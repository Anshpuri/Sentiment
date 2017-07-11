package com.example.android.project4.RecyclerViewAdapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.OnCompositionLoadedListener;
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
    LottieDrawable drawable = new LottieDrawable();


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
        holder.tv_username.setText(status.getUser().getName().toUpperCase().toString());
        holder.tv_tweet.setText(status.getText().toString());
        if(emotionalState!=null){
            if(map.containsKey(emotionalState.getStrongestEmotion().getType())){
                if(emotionalState.getStrongestEmotion().getType()==Emotion.ANGER){
                    holder.tv_emotion.setText(map.get(emotionalState.getStrongestEmotion().getType()));
                    holder.tv_emotion.setTextColor(Color.parseColor("#c62828"));
//                    LottieComposition.Factory.fromAssetFileName(context, "emojishock.json", new OnCompositionLoadedListener() {
//                        @Override
//                        public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                            drawable.setComposition(composition);
//
//                        }
//                    });

                    holder.lottieAnimationView.setAnimation("emojishock.json");
                    holder.lottieAnimationView.loop(true);
                    holder.lottieAnimationView.playAnimation();
                }
                if(emotionalState.getStrongestEmotion().getType()==Emotion.DISGUST){
                    holder.tv_emotion.setText(map.get(emotionalState.getStrongestEmotion().getType()));
                    holder.tv_emotion.setTextColor(Color.parseColor("#1a237e"));
//                    LottieComposition.Factory.fromAssetFileName(context, "emojishock.json", new OnCompositionLoadedListener() {
//                        @Override
//                        public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                            drawable.setComposition(composition);
//
//                        }
//                    });
                    holder.lottieAnimationView.setAnimation("emojishock.json");
                    holder.lottieAnimationView.loop(true);
                    holder.lottieAnimationView.playAnimation();
                }
                if(emotionalState.getStrongestEmotion().getType()==Emotion.FEAR){
                    holder.tv_emotion.setText(map.get(emotionalState.getStrongestEmotion().getType()));
                    holder.tv_emotion.setTextColor(Color.parseColor("#ffebee"));
                    LottieComposition.Factory.fromAssetFileName(context, "emojishock.json", new OnCompositionLoadedListener() {
                        @Override
                        public void onCompositionLoaded(@Nullable LottieComposition composition) {
                            drawable.setComposition(composition);

                        }
                    });
//                    holder.lottieAnimationView.setAnimation("emojishock.json");
//                    holder.lottieAnimationView.loop(true);
                    holder.lottieAnimationView.playAnimation();
                }
                if(emotionalState.getStrongestEmotion().getType()==Emotion.HAPPINESS){
                    holder.tv_emotion.setText(map.get(emotionalState.getStrongestEmotion().getType()));
                    holder.tv_emotion.setTextColor(Color.parseColor("#ffeb3b"));
//                    LottieComposition.Factory.fromAssetFileName(context, "emojitounge.json", new OnCompositionLoadedListener() {
//                        @Override
//                        public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                            drawable.setComposition(composition);
//
//                        }
//                    });
                    holder.lottieAnimationView.setAnimation("emojitounge.json");
                    holder.lottieAnimationView.loop(true);
                    holder.lottieAnimationView.playAnimation();
                }
                if(emotionalState.getStrongestEmotion().getType()==Emotion.NEUTRAL){
                    holder.tv_emotion.setText(map.get(emotionalState.getStrongestEmotion().getType()));
                    holder.tv_emotion.setTextColor(Color.parseColor("#757575"));
//                    LottieComposition.Factory.fromAssetFileName(context, "emojiwink.json", new OnCompositionLoadedListener() {
//                        @Override
//                        public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                            drawable.setComposition(composition);
//
//                        }
//                    });
                    holder.lottieAnimationView.setAnimation("emojiwink.json");
                    holder.lottieAnimationView.loop(true);
                    holder.lottieAnimationView.playAnimation();
                }
                if(emotionalState.getStrongestEmotion().getType()==Emotion.SADNESS){
                    holder.tv_emotion.setText(map.get(emotionalState.getStrongestEmotion().getType()));
                    holder.tv_emotion.setTextColor(Color.parseColor("#263238"));
//                    LottieComposition.Factory.fromAssetFileName(context, "emojishock.json", new OnCompositionLoadedListener() {
//                        @Override
//                        public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                            drawable.setComposition(composition);
//
//                        }
//                    });
                    holder.lottieAnimationView.setAnimation("emojishock.json");
                    holder.lottieAnimationView.loop(true);
                    holder.lottieAnimationView.playAnimation();
                }
                if(emotionalState.getStrongestEmotion().getType()==Emotion.SURPRISE){
                    holder.tv_emotion.setText(map.get(emotionalState.getStrongestEmotion().getType()));
                    holder.tv_emotion.setTextColor(Color.parseColor("#388e3c"));
//                    LottieComposition.Factory.fromAssetFileName(context, "emojitounge.json", new OnCompositionLoadedListener() {
//                        @Override
//                        public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                            drawable.setComposition(composition);
//
//                        }
//                    });
                    holder.lottieAnimationView.setAnimation("emojitounge.json");
                    holder.lottieAnimationView.loop(true);
                    holder.lottieAnimationView.playAnimation();
                }


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
        private ImageView img_user,img_tweebird;
        private LottieAnimationView lottieAnimationView;
        private View v;
        public TweetViewHolder(View itemView) {
            super(itemView);
            Typeface tf = Typeface.createFromAsset(itemView.getContext().getAssets(), "helvetica-neue.ttf");
            lottieAnimationView=(LottieAnimationView)itemView.findViewById(R.id.emoji_view);
            img_tweebird=(ImageView) itemView.findViewById(R.id.img_tweebird);
            img_tweebird.setImageResource(R.drawable.tbird);
            tv_username=(TextView) itemView.findViewById(R.id.tv_username);
            tv_username.setTypeface(tf);
            tv_emotion=(TextView) itemView.findViewById(R.id.tv_sentiment);
            tv_emotion.setTypeface(tf);
            img_user=(ImageView) itemView.findViewById(R.id.img_user);
            tv_tweet=(TextView) itemView.findViewById(R.id.tv_tweet);
            tv_tweet.setTypeface(tf);
            v=itemView;
        }
    }
}
