package tech.yashtiwari.firebasevideoapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import tech.yashtiwari.firebasevideoapp.fragment.record.RecordFragment;
import tech.yashtiwari.firebasevideoapp.fragment.videostack.VideoStackFragment;


public class MainActivityViewPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "MainActivityViewPagerAd";

    public MainActivityViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return new VideoStackFragment();
        } else {
            return new RecordFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
