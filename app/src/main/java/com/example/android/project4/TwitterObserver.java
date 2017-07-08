package com.example.android.project4;



import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import twitter4j.FilterQuery;
import twitter4j.Status;


/**
 * Created by amandhapola on 08/07/17.
 */

public class TwitterObserver {
    private Observer<Status> observer;
    private FilterQuery filterQuery;

    public Disposable getDisposable() {
        return disposable;
    }

    private Disposable disposable;

    public Observer<Status> getObserver() {
        return observer;
    }

    public FilterQuery getFilterQuery() {
        return filterQuery;
    }

    public void setFilterQuery(FilterQuery filterQuery) {
        this.filterQuery = filterQuery;
    }


    public static final String TAG="TWITTER_OBSERVER";
    public TwitterObserver() {
        observer=new Observer<Status>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
            }

            @Override
            public void onNext(Status status) {
                Log.d(TAG, "onNext: "+ status.getUser().getName().toString());
                Log.d(TAG, "onNext: "+ status.getText().toString());
                Log.d(TAG, "onNext: ---------------------------------------");
            }

            @Override
            public void onError(Throwable e) {
//                if(disposable.isDisposed()!=true)
//                    disposable.dispose();
            }

            @Override
            public void onComplete() {
//                if (disposable.isDisposed()!=true)
//                disposable.dispose();

            }
        };
    }
}
