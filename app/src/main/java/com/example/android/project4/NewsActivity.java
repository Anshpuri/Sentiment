package com.example.android.project4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.project4.RecyclerViewAdapters.NewsAdapter;
import com.example.android.project4.api.ApiService;
import com.example.android.project4.api.NewsService;
import com.example.android.project4.models.Response;
import com.example.android.project4.models.ValueItem;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;

public class NewsActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    ArrayList<ValueItem> newsList = new ArrayList<>();
    NewsAdapter newsAdapter = new NewsAdapter(newsList);
    NewsService newsService;
    Disposable disposable;
    public static final String TAG= "NEWS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView= (RecyclerView) findViewById(R.id.rv_news);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setTitle("News");

        newsService= ApiService.getInstance().getNewsService();
        if(newsService==null){
            Log.d(TAG, "onCreate: sewsservice is null" );
        }
        Call<Response> newsObservable =  newsService.getNews("india",50,0,"en-in","Moderate");
        Log.d(TAG, "onCreate: here");
        newsObservable.enqueue(new Callback<Response>() {
            @Override
            public void success(Result<Response> result) {
                newsList.addAll(result.data.getValue());
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });


    }

    @Override
    protected void onPause() {
        //disposable.dispose();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //disposable.dispose();
        super.onDestroy();
    }
}
