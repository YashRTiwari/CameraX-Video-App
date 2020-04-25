package tech.yashtiwari.firebasevideoapp.adapter;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import tech.yashtiwari.firebasevideoapp.fragment.video.VideoFragment;
import tech.yashtiwari.firebasevideoapp.fragment.video.VideoStackFragment;

import static tech.yashtiwari.firebasevideoapp.util.Utility.VIDEO_KEY;


public class VideoViewPagerAdapter2 extends FragmentStateAdapter {

    private String[] videos = {
            "file:///storage/emulated/0/Android/data/tech.yashtiwari.firebasevideoapp/files/Movies/1587781519931.mp4",
            "file:///storage/emulated/0/Android/data/tech.yashtiwari.firebasevideoapp/files/Movies/1587781519931.mp4",
            "file:///storage/emulated/0/Android/data/tech.yashtiwari.firebasevideoapp/files/Movies/1587781519931.mp4",
            "file:///storage/emulated/0/Android/data/tech.yashtiwari.firebasevideoapp/files/Movies/1587781519931.mp4"
    };


    public VideoViewPagerAdapter2(FragmentManager fm, Lifecycle lifecycle) {
        super(fm, lifecycle);
    }

    private Fragment onVideoFragment(String url) {
        VideoFragment newFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_KEY, url);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public boolean containsItem(long itemId) {
        return super.containsItem(itemId);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return onVideoFragment(videos[position]);
    }

    @Override
    public int getItemCount() {
        return videos.length;
    }
}
