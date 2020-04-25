package tech.yashtiwari.firebasevideoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tech.yashtiwari.firebasevideoapp.R;
import tech.yashtiwari.firebasevideoapp.databinding.ActivityPreviewVideoBinding;
import tech.yashtiwari.firebasevideoapp.model.VideoModel;
import tech.yashtiwari.firebasevideoapp.services.MyUploadService;

import static tech.yashtiwari.firebasevideoapp.util.Utility.VIDEO_KEY;

public class PreviewVideoActivity extends AppCompatActivity {

    private static final String TAG = "PreviewVideoActivity";
    private ActivityPreviewVideoBinding binding;
    private BroadcastReceiver broadcastReceiver;
    private DatabaseReference dbReference;
    private DatabaseReference conditionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_video);

        binding = DataBindingUtil.setContentView(PreviewVideoActivity.this, R.layout.activity_preview_video);
        String videoUrl = getIntent().getStringExtra(VIDEO_KEY);
        Toast.makeText(this, videoUrl, Toast.LENGTH_SHORT).show();

        dbReference = FirebaseDatabase.getInstance().getReference();
        conditionReference = dbReference.child("videos");

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(binding.videoView);
        binding.videoView.setMediaController(mediaController);
        binding.videoView.setVideoPath(videoUrl);
        binding.videoView.start();

        binding.ibCancel.setOnClickListener(v -> finish());

        binding.ibUpload.setOnClickListener(v -> {
            binding.videoView.stopPlayback();
            uploadFromUri(Uri.parse(videoUrl));
        });

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

    private void uploadFromUri(Uri fileUri) {
        Log.d(TAG, "uploadFromUri:src:" + fileUri.toString());
        startService(new Intent(PreviewVideoActivity.this, MyUploadService.class)
                .putExtra(MyUploadService.EXTRA_FILE_URI, fileUri)
                .setAction(MyUploadService.ACTION_UPLOAD));
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
