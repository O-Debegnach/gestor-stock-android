package com.ispc.gestorstock.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.VideoView;

import com.ispc.gestorstock.R;
import com.ispc.gestorstock.helpers.VideoHelper;

public class MainActivity extends AppCompatActivity {
    private static final String BANNER_VIDEO = "bgbanner";

    VideoView mBannerVideo;
    Button mLoginButton;
    Button mSignUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBannerVideo = findViewById(R.id.bannerVideo);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        mSignUpButton = findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
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