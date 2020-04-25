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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private DatabaseReference dbReference, conditionReference;

    // Required empty public constructor
    public VideoStackFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_stack, container, false);

        dbReference = FirebaseDatabase.getInstance().getReference();
        conditionReference = dbReference.child("videos");        
        
        conditionReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded: ");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged: ");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved: ");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildMoved: ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: ");
            }
        });
        
        binding.videoFrame.setAdapter(new VideoViewPagerAdapter2(getActivity().getSupportFragmentManager(), getLifecycle()));
        return binding.getRoot();
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
