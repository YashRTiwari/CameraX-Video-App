package tech.yashtiwari.firebasevideoapp.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import tech.yashtiwari.firebasevideoapp.Application;
import tech.yashtiwari.firebasevideoapp.event.Events;
import tech.yashtiwari.firebasevideoapp.fragment.record.RecordFragment;
import tech.yashtiwari.firebasevideoapp.fragment.video.VideoStackFragment;


public class MainActivityViewPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "MainActivityViewPagerAd";

    public MainActivityViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            Log.d(TAG, "createFragment: Video");
            return new VideoStackFragment();
        } else {
            Log.d(TAG, "createFragment: Record");
            return new RecordFragment();
        }
    }

    @Override
    public int getItemViewType(int position) {
//        Log.d(TAG, "getItemViewType: "+position);
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
//        Log.d(TAG, "getItemId: " + position);
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
