package com.example.android.project4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoginActivity extends AppCompatActivity {

    private TwitterLoginButton loginButton;
    TwitterSession session;
    SharedPreferences preferences;
    int count=0;
    public static final String TAG="LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("dKUQo0IIf1ZYo3EtMNdbPT374", "lnRA8izXCEG6mRMePUV88k6XL3SzUs5fe3iOtKbArMFlaw3jio"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences=getSharedPreferences("loginPref",LoginActivity.MODE_PRIVATE);
        final SharedPreferences.Editor editor =preferences.edit();

        if(preferences.contains("token")){
            //already looged in
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

        loginButton= (TwitterLoginButton) findViewById(R.id.login_button);

        ////////////////////////////////////////////////////////////////////////////////////

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                session =result.data;
//                Toast.makeText(getApplicationContext(),"login done",Toast.LENGTH_SHORT);
                editor.putString("userName",session.getUserName().toString());
                Log.d(TAG, "success: " +session.getUserName().toString());
                Log.d(TAG, "success: " + session.getAuthToken().token.toString());
                Log.d(TAG, "success: " + session.getAuthToken().secret.toString());
                editor.putLong("userId",session.getUserId());

                TwitterAuthToken authToken=session.getAuthToken();
                editor.putBoolean("isLogin",true);
                editor.putString("token",authToken.token.toString());
                editor.putString("secret",authToken.secret.toString());
                editor.apply();

                Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("userName",session.getUserName().toString());
                intent.putExtra("userId",String.valueOf(session.getUserId()));
                intent.putExtra("token",session.getAuthToken().token.toString());
                intent.putExtra("secret",session.getAuthToken().secret.toString());
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                // TODO: 10/07/17  put welcome messsage here
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
