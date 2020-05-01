package tech.yashtiwari.firebasevideoapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import tech.yashtiwari.firebasevideoapp.Application;
import tech.yashtiwari.firebasevideoapp.R;
import tech.yashtiwari.firebasevideoapp.adapter.MainActivityViewPagerAdapter;
import tech.yashtiwari.firebasevideoapp.databinding.ActivityMainBinding;
import tech.yashtiwari.firebasevideoapp.event.Events;
import tech.yashtiwari.firebasevideoapp.model.VideoModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private MainActivityViewPagerAdapter pagerAdapter;
    private CollectionReference mCollection = FirebaseFirestore.getInstance().collection("videos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        pagerAdapter = new MainActivityViewPagerAdapter(this);
        binding.vpFrame.setAdapter(pagerAdapter);
//        setUpDummyData();
    }



    private void setUpDummyData() {
        VideoModel v1 = new VideoModel();
        v1.setVideoLink("https://player.vimeo.com/external/380067769.sd.mp4?s=11d48683df43eed69d4c4ee6f9def5e154d39cfa&profile_id=165&oauth2_token_id=57447761");
        v1.setVideoCreatedOn(1587780591906L);

        VideoModel v2 = new VideoModel();
        v2.setVideoLink("https://player.vimeo.com/external/380067769.sd.mp4?s=11d48683df43eed69d4c4ee6f9def5e154d39cfa&profile_id=165&oauth2_token_id=57447761");
        v2.setVideoCreatedOn(1587781530746L);


        VideoModel v3 = new VideoModel();
        v3.setVideoLink("https://player.vimeo.com/external/380067769.sd.mp4?s=11d48683df43eed69d4c4ee6f9def5e154d39cfa&profile_id=165&oauth2_token_id=57447761");
        v3.setVideoCreatedOn(1587801934558L);

        VideoModel v4 = new VideoModel();
        v4.setVideoLink("https://player.vimeo.com/external/380067769.sd.mp4?s=11d48683df43eed69d4c4ee6f9def5e154d39cfa&profile_id=165&oauth2_token_id=57447761");
        v4.setVideoCreatedOn(1587804677552L);

        mCollection.document("-M5j5Hfie5CtW-UMyKda").set(v1);
        mCollection.document("-M5j8rtId-U-ym6HcyV7").set(v2);
        mCollection.document("-M5kMhFJMJeCUUFgVS2W").set(v3);
        mCollection.document( "-M5kX9y-sZq-YuQNVup5").set(v4);
    }

    @Override
    protected void onResume() {
        super.onResume();


        /*
         * This event will restrict swipe when user is recording the video
         * */
        Application.getApplication()
                .bus()
                .toObservable()
                .subscribe(o -> {
                    if (o instanceof Events.ViewPagerSwipe) {
                        Events.ViewPagerSwipe swipe = (Events.ViewPagerSwipe) o;
                        if (swipe.getSwipe()) {
                            binding.vpFrame.setUserInputEnabled(true);
                        } else {
                            binding.vpFrame.setUserInputEnabled(false);
                        }
                    }
                });

    }
}
