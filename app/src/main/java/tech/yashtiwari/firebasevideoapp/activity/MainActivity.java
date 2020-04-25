package tech.yashtiwari.firebasevideoapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tech.yashtiwari.firebasevideoapp.R;
import tech.yashtiwari.firebasevideoapp.adapter.MainActivityViewPagerAdapter;
import tech.yashtiwari.firebasevideoapp.databinding.ActivityMainBinding;
import tech.yashtiwari.firebasevideoapp.model.VideoModel;
import tech.yashtiwari.firebasevideoapp.services.MyUploadService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private BroadcastReceiver broadcastReceiver;
    private DatabaseReference dbReference;
    private DatabaseReference conditionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.vpFrame.setAdapter(new MainActivityViewPagerAdapter(this));
        dbReference = FirebaseDatabase.getInstance().getReference();
        conditionReference = dbReference.child("videos");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (intent.getAction()) {
                    case MyUploadService.UPLOAD_COMPLETED:
                    case MyUploadService.UPLOAD_ERROR:
                        onUploadResultIntent(intent);
                        break;
                }

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Register receiver for uploads and downloads
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(broadcastReceiver, MyUploadService.getIntentFilter());
    }

    private void onUploadResultIntent(Intent intent) {
        Uri mDownloadUrl = intent.getParcelableExtra(MyUploadService.EXTRA_DOWNLOAD_URL);
        String url = mDownloadUrl.toString();
        String id = conditionReference.push().getKey();
        VideoModel videoModel = new VideoModel(id, url);
        conditionReference.child(id).setValue(videoModel)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Success"));
    }

}
