package tech.yashtiwari.firebasevideoapp.fragment.video;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import tech.yashtiwari.firebasevideoapp.Application;
import tech.yashtiwari.firebasevideoapp.R;
import tech.yashtiwari.firebasevideoapp.adapter.VideoViewPagerAdapter2;
import tech.yashtiwari.firebasevideoapp.databinding.FragmentVideoStackBinding;
import tech.yashtiwari.firebasevideoapp.event.Events;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoStackFragment extends Fragment {


    private FragmentVideoStackBinding binding;
    private static final String TAG = "VideoStackFragment";

    // Required empty public constructor
    public VideoStackFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_stack, container, false);
        binding.videoFrame.setAdapter(new VideoViewPagerAdapter2(getActivity().getSupportFragmentManager(), getLifecycle()));
        return binding.getRoot();
    }

    private void getAllFilesInFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child("photos/");

        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            Task<Uri> task = item.getDownloadUrl();

                            task.addOnSuccessListener(getActivity(), new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d(TAG, uri.toString());
                                }
                            });

                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // Uh-oh, an error occurred!
                    e.printStackTrace();
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "createFragment: 0");
        Application.getApplication()
                .bus()
                .send(new Events.VideoEvent(Events.State.RESUME));
        Application.getApplication()
                .bus()
                .send(new Events.RecordEvent(Events.State.STOP));
        Log.d(TAG, "onResume: "+binding.videoFrame.getCurrentItem());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: "+binding.videoFrame.getCurrentItem());
        Application.getApplication()
                .bus()
                .send(new Events.VideoEvent(Events.State.STOP));

    }
}
