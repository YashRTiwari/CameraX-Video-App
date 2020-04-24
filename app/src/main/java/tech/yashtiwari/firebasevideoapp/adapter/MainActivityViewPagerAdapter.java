package tech.yashtiwari.firebasevideoapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import tech.yashtiwari.firebasevideoapp.Application;
import tech.yashtiwari.firebasevideoapp.event.Events;
import tech.yashtiwari.firebasevideoapp.fragment.record.RecordFragment;
import tech.yashtiwari.firebasevideoapp.fragment.video.VideoStackFragment;


public class MainActivityViewPagerAdapter extends FragmentStateAdapter {
    VideoStackFragment videoStackFragment;
    RecordFragment recordFragment;

    public MainActivityViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {

            Application.getApplication()
                    .bus()
                    .send(new Events.RecordEvent(Events.State.STOP));

            videoStackFragment = new VideoStackFragment();
            return videoStackFragment;
        } else {

            Application.getApplication()
                    .bus()
                    .send(new Events.VideoEvent(Events.State.STOP));

            recordFragment = new RecordFragment();
            return recordFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
