package com.example.android.project4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import java.io.IOException;

public class ComposeActivity extends AppCompatActivity {

    private EditText et_tweet, et_hashtag;
    private ImageView img_tweet_pic;
    private FloatingActionButton fab;
    private int PICK_IMAGE_REQUEST = 1;
    private Button btn_compose;
    private Uri imageUri;
    public static final String TAG="URI";
    final TwitterSession session = TwitterCore.getInstance().getSessionManager()
            .getActiveSession();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        fab = (FloatingActionButton) findViewById(R.id.fab_pic);
        et_tweet = (EditText) findViewById(R.id.et_tweet);
        et_hashtag = (EditText) findViewById(R.id.et_hashtag);
        img_tweet_pic = (ImageView) findViewById(R.id.img_tweet_pic);
        btn_compose= (Button) findViewById(R.id.btn_compose);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        btn_compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri!=null){
                    final Intent intent = new ComposerActivity.Builder(ComposeActivity.this)
                            .session(session)
                            .image(imageUri)
                            .text(et_tweet.getText().toString())
                            .hashtags(et_hashtag.getText().toString())
                            .createIntent();
                    startActivity(intent);
                }
                else{
                    final Intent intent = new ComposerActivity.Builder(ComposeActivity.this)
                            .session(session)
                            .text(et_tweet.getText().toString())
                            .hashtags(et_hashtag.getText().toString())
                            .createIntent();
                    startActivity(intent);
                }

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                img_tweet_pic.setImageBitmap(bitmap);
                Log.d(TAG, "onActivityResult: " +uri);
                imageUri=uri;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
