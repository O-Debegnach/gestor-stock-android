package com.ispc.gestorstock.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.ispc.gestorstock.R;
import com.ispc.gestorstock.helpers.VideoHelper;

public class MainActivity extends AppCompatActivity {
    private static final String BANNER_VIDEO = "bgbanner";

    VideoView mBannerVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBannerVideo = findViewById(R.id.bannerVideo);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeBannerVideoPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer(){
        mBannerVideo.stopPlayback();
    }


    private void initializeBannerVideoPlayer(){
        Uri uri = VideoHelper.getMedia(BANNER_VIDEO, getPackageName());
        mBannerVideo.setVideoURI(uri);
        mBannerVideo.start();

        mBannerVideo.setOnCompletionListener(mediaPlayer -> {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
            Log.d("VIDEO", "RESTART");
        });
    }
}