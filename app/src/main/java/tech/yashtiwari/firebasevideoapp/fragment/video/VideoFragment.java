package tech.yashtiwari.firebasevideoapp.fragment.video;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import tech.yashtiwari.firebasevideoapp.Application;
import tech.yashtiwari.firebasevideoapp.R;
import tech.yashtiwari.firebasevideoapp.databinding.VideoFragmentBinding;
import tech.yashtiwari.firebasevideoapp.event.Events;

import static tech.yashtiwari.firebasevideoapp.util.Utility.VIDEO_KEY;


public class VideoFragment extends Fragment implements Player.EventListener {

    private VideoViewModel mViewModel;
    private VideoFragmentBinding binding;
    private String url = null;
    private static final String TAG = "VideoFragment";
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.video_fragment, container, false);
        url = getArguments().getString(VIDEO_KEY);
//        initializePlayer();
        Application.getApplication().bus().toObservable()
                .subscribe(o -> {
                    if (o instanceof Events.VideoEvent) {
                        Events.VideoEvent obj = (Events.VideoEvent) o;
                        if (obj.getState() == Events.State.STOP) {
                            onPause();
                        } else {
                            onResume();
                        }
                    }
                });
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VideoViewModel.class);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity().getApplicationContext(), "videoapp");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }



    private void initializePlayer() {

        if (url == null) return;

        player = new SimpleExoPlayer.Builder(getActivity().getApplicationContext()).build();
        player.addListener(this);
        Uri uri = Uri.parse(url);
        MediaSource mediaSource = buildMediaSource(uri);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(0);
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
//        binding.playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
//        player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        player.prepare(mediaSource, true, false);
        binding.playerView.setUseController(false);
        binding.playerView.setPlayer(player);


    }

    @Override
    public void onResume() {
        super.onResume();
        if (url != null) {
            if (player == null) {
                initializePlayer();
            }
            player.setPlayWhenReady(true);
            player.seekTo(0);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) player.setPlayWhenReady(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        releasePlayer();
    }

    private void releasePlayer(){
        if (player != null) {
            player.setPlayWhenReady(false);
            player.release();
        }
    }
}
