package tech.yashtiwari.firebasevideoapp.adapter;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import tech.yashtiwari.firebasevideoapp.fragment.video.VideoFragment;
import tech.yashtiwari.firebasevideoapp.model.VideoModel;

import static tech.yashtiwari.firebasevideoapp.util.Utility.VIDEO_KEY;


public class VideoViewPagerAdapter2 extends FragmentStateAdapter {

    private List<VideoModel> videos;

    public VideoViewPagerAdapter2(FragmentManager fm, Lifecycle lifecycle) {
        super(fm, lifecycle);
    }

    public void addVideoList(List<VideoModel> videos){
        this.videos = videos;
        notifyDataSetChanged();
    }


    private Fragment onVideoFragment(String url) {
        VideoFragment newFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_KEY, url);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return onVideoFragment(videos.get(position).getVideoLink());
    }

    @Override
    public int getItemCount() {
        return videos != null ? videos.size() : 0;
    }
}
