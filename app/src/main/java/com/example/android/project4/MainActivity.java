package com.example.android.project4;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.project4.FragmentAdpaters.TwitterViewPagerAdapter;
import com.example.android.project4.Fragments.TweetsFragment;
import com.example.android.project4.Fragments.TwitterGraphFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    TabLayout tabLayout;
    ViewPager viewPager;
    TwitterViewPagerAdapter twitterViewPagerAdapter;
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    ArrayList<String> fragmentTitleList=new ArrayList<>();
    private String searchQuery="narendra modi";
    private TextView tv_nav_title,tv_nav_email;
    private View header;
    private NavigationView navigationView ;
    public static final String TAG="search";
    SharedPreferences preferences;
    private User user;
    private TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
    private TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();

    private ImageView img_nav_profile;
    private LinearLayout nav_ll;


//    @Override
//    protected void onNewIntent(Intent intent) {
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            Toast.makeText(this,query,Toast.LENGTH_SHORT).show();
//            searchQuery=query;
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("HashTag");

        navigationView= (NavigationView) findViewById(R.id.nav_view);
        header=navigationView.getHeaderView(0);

        tv_nav_email= (TextView) header.findViewById(R.id.tv_nav_email);
        tv_nav_title= (TextView) header.findViewById(R.id.tv_nav_title);
        img_nav_profile=(ImageView) header.findViewById(R.id.img_nav_profile);
        nav_ll=(LinearLayout)header.findViewById(R.id.nav_background);
        preferences=getSharedPreferences("loginPref",MainActivity.MODE_PRIVATE);

//        tv_nav_title.setText(getIntent().getStringExtra("userName"));
        tv_nav_title.setText(preferences.getString("userName","no name"));
        ///////////////////////////////////////////////////////
        ///////////// twitter client
        ///////////////////////////////////////////////////////



        twitterApiClient.getAccountService().verifyCredentials(false,false,true).enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                user  =result.data;
                tv_nav_email.setText(user.description.toString());

                Picasso.with(getApplicationContext()).load(user.profileImageUrl)
                        .placeholder(R.drawable.rsz_1placeholder_twitter)
                        .transform(new CropCircleTransformation())
                        .into(img_nav_profile);

                Picasso.with(getApplicationContext())
                        .load(user.profileBackgroundImageUrl).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        nav_ll.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                    }
                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

        //////////////////////////////////////////////////////

//        Intent intent=getIntent();
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchQuery=query;
        }

        ///////////////////////////////////////////////////////
        //adding Fragments
        fragmentList.add(TweetsFragment.newInstance(searchQuery,""));
        fragmentList.add(TwitterGraphFragment.newInstance("",""));
        fragmentTitleList.add("Tweets");
        fragmentTitleList.add("Graph");

        tabLayout= (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.tweet_view_pager);

        fragmentManager=getSupportFragmentManager();
        twitterViewPagerAdapter=new TwitterViewPagerAdapter(fragmentManager,fragmentList,fragmentTitleList);
        viewPager.setAdapter(twitterViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this,TweetActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this,ComposeActivity.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this, NewsActivity.class));
        }
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
