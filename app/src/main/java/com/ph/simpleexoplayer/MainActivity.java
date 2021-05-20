package com.ph.simpleexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

public class MainActivity extends AppCompatActivity {
    PlayerView exoPLayerView;
    ProgressBar prgsExoPlayer;
    ImageView imgExoFullScrn;
    SimpleExoPlayer exoPlayer;
    boolean blnFlag;
    Uri videoUrl=Uri.parse("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blnFlag=false;
        hideHideActionBar();
        initExoViews();
        initActivityFullScrn();
        initExoPlayerProperties(this);
        initExoPlayerListener();
        initOnclickListener();
    }

    private void initActivityFullScrn() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initExoViews() {
        exoPLayerView=findViewById(R.id.tag_main_playerview);
        prgsExoPlayer=findViewById(R.id.tag_main_progressbar);
        imgExoFullScrn =findViewById(R.id.tag_exo_custom_bt_fullscr);
    }

    private void initExoPlayerProperties(Context context){
        exoPlayer= new SimpleExoPlayer.Builder(context).build();
        exoPLayerView.setPlayer(exoPlayer); //Add Player to ExoView
        exoPLayerView.setKeepScreenOn(true);
        exoPlayer.setMediaItem(MediaItem.fromUri(videoUrl));
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }

    private void initExoPlayerListener(){
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if(state==Player.STATE_BUFFERING)
                    prgsExoPlayer.setVisibility(View.VISIBLE);
                else
                    prgsExoPlayer.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void initOnclickListener(){
        imgExoFullScrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blnFlag)
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                else
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });
    }
    private void  hideHideActionBar(){
        getSupportActionBar().hide();
    }
}