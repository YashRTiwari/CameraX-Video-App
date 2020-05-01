package tech.yashtiwari.firebasevideoapp.fragment.videostack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

import tech.yashtiwari.firebasevideoapp.Application;
import tech.yashtiwari.firebasevideoapp.R;
import tech.yashtiwari.firebasevideoapp.adapter.VideoViewPagerAdapter2;
import tech.yashtiwari.firebasevideoapp.databinding.FragmentVideoStackBinding;
import tech.yashtiwari.firebasevideoapp.event.Events;
import tech.yashtiwari.firebasevideoapp.model.VideoModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoStackFragment extends Fragment {


    private FragmentVideoStackBinding binding;
    private static final String TAG = "VideoStackFragment";
    private VideoViewPagerAdapter2 videoViewPagerAdapter2;
    private VideoStackViewModel viewModel;


    // Required empty public constructor
    public VideoStackFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_stack, container, false);
        viewModel = new ViewModelProvider(this).get(VideoStackViewModel.class);
        viewModel.getListOfVideos();
        videoViewPagerAdapter2 = new VideoViewPagerAdapter2(
                Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                getLifecycle());
        subscribeToLiveData();
        return binding.getRoot();
    }

    private void subscribeToLiveData() {
        viewModel.mldVideos.observe(getViewLifecycleOwner(), videos -> {
            if (videos.size() > 0) {
                Snackbar.make(getView(), "Data found", Snackbar.LENGTH_LONG).show();
                videoViewPagerAdapter2.addVideoList(videos);
                binding.videoFrame.setAdapter(videoViewPagerAdapter2);
            } else {
                Snackbar.make(getView(), "No Data found", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Application.getApplication()
                .bus()
                .send(new Events.VideoEvent(Events.State.RESUME));
        Application.getApplication()
                .bus()
                .send(new Events.RecordEvent(Events.State.STOP));
    }

    @Override
    public void onPause() {
        super.onPause();
        Application.getApplication()
                .bus()
                .send(new Events.VideoEvent(Events.State.STOP));

    }
}
