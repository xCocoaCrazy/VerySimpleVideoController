package com.rosca.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private int[] videoIds = { R.raw.lamb_says_yeah, R.raw.ronco_de_morto, R.raw.whimpy_goat };
    private int currentVideoIndex = 0;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.video_view);
        Uri videoUri = getVideoUri(videoIds[currentVideoIndex]);
        videoView.setVideoURI(videoUri);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
        mediaController.setPrevNextListeners(
                v -> {
                    // Play the next video
                    currentVideoIndex = (currentVideoIndex + 1) % videoIds.length;
                    Uri nextVideoUri = getVideoUri(videoIds[currentVideoIndex]);
                    videoView.setVideoURI(nextVideoUri);
                    videoView.start();
                },
                v -> {
                    // Play the previous video
                    currentVideoIndex = (currentVideoIndex - 1 + videoIds.length) % videoIds.length;
                    Uri previousVideoUri = getVideoUri(videoIds[currentVideoIndex]);
                    videoView.setVideoURI(previousVideoUri);
                    videoView.start();
                }
        );
    }

    private Uri getVideoUri(int videoId) {
        return Uri.parse("android.resource://" + getPackageName() + "/" + videoId);
    }
}