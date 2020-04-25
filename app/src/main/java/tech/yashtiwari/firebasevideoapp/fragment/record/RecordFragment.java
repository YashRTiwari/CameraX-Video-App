package tech.yashtiwari.firebasevideoapp.fragment.record;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.impl.VideoCaptureConfig;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;

import tech.yashtiwari.firebasevideoapp.Application;
import tech.yashtiwari.firebasevideoapp.R;
import tech.yashtiwari.firebasevideoapp.activity.PreviewVideoActivity;
import tech.yashtiwari.firebasevideoapp.databinding.RecordFragmentBinding;
import tech.yashtiwari.firebasevideoapp.event.Events;
import tech.yashtiwari.firebasevideoapp.util.Utility;

import static tech.yashtiwari.firebasevideoapp.util.Utility.VIDEO_KEY;


public class RecordFragment extends Fragment {

    private RecordViewModel mViewModel;
    private static final String TAG = "RecordFragment";
    private RecordFragmentBinding binding;
    private DisplayMetrics metrics;
    private int screenAspectRatio, rotation;
    private CameraSelector cameraSelector;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Preview preview;
    private VideoCapture videoCapture;
    private Camera camera;
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL = 1;
    private static final int PERMISSION_REQUEST_AUDIO = 2;
    private Snackbar snackbar;
    private VideoCaptureConfig videoCaptureConfig;
    private int CAMERA_FACING_LES;
    private ProcessCameraProvider cameraProvider = null;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecordViewModel.class);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.record_fragment, container, false);
        CAMERA_FACING_LES = CameraSelector.LENS_FACING_FRONT;
        binding.ibRotate.setOnClickListener(v -> {
            if (CAMERA_FACING_LES == CameraSelector.LENS_FACING_FRONT) {
                CAMERA_FACING_LES = CameraSelector.LENS_FACING_BACK;
            } else {
                CAMERA_FACING_LES = CameraSelector.LENS_FACING_FRONT;
            }
            checkPermissions();
        });

        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        Application.getApplication()
                .bus()
                .send(new Events.VideoEvent(Events.State.STOP));
        checkPermissions();
    }


    @Override
    public void onStop() {
        super.onStop();
        cameraProvider.unbindAll();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraProvider.unbindAll();
    }
    /*
     * Camer
     *   -> False -> Show Snack.
     *   -> True -> Audio
     *                  -> False -> Show Snack.
     *                  -> True -> Write
     *                              -> False -> Show Snack.
     *                              -> True -> Start Everything.
     */

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    /* Start */
                    attachRecordButtonListener();
                    attachCameraToView();
                } else {
                    requestWritePermission();
                }
            } else {
                requestAudioPermission();
            }
        } else {
            requestCameraPermission();
        }
    }


    private void attachRecordButtonListener() {
        binding.ibVideoCapture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    File file = Utility.createFile(requireContext(), System.currentTimeMillis() + "");
                    if (videoCapture != null)
                        videoCapture.startRecording(file, Runnable::run, videoSavedCallback);
                    else
                        Toast.makeText(requireContext(), "Video Capture Null", Toast.LENGTH_SHORT).show();
                } else {
                    videoCapture.stopRecording();
                }
            }
        });
        binding.ibVideoCapture.setEnabled(true);
    }

    private void requestWritePermission() {
        snackbar = Snackbar.make(getActivity().findViewById(R.id.layout), R.string.write_permission_required,
                Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_WRITE_EXTERNAL);
            }
        });
        snackbar.show();

    }


    private void requestAudioPermission() {
        // Permission has not been granted and must be requested.
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.RECORD_AUDIO},
                PERMISSION_REQUEST_AUDIO);
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            Snackbar.make(getActivity().findViewById(R.id.layout), R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else {
            Snackbar.make(getActivity().findViewById(R.id.layout), R.string.camera_unavailable, Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }


    @SuppressLint("RestrictedApi")
    private Runnable cameraListener = new Runnable() {
        @Override
        public void run() {

            // CameraProvider
            try {
                cameraProvider = cameraProviderFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            preview = new Preview.Builder()
                    .setTargetAspectRatio(screenAspectRatio)
                    .setTargetRotation(rotation)
                    .build();

            videoCaptureConfig = new VideoCaptureConfig.Builder()
                    .setTargetAspectRatio(screenAspectRatio)
                    .setTargetRotation(rotation)
                    .setMaxResolution(new Size((int) Math.abs(metrics.widthPixels / 2), (int) Math.abs(metrics.heightPixels / 2)))
                    .getUseCaseConfig();

            // Preview
            videoCapture = new VideoCapture(videoCaptureConfig);


            // Must unbind the use-cases before rebinding them
            cameraProvider.unbindAll();

            try {
                // A variable number of use-cases can be passed here -
                // camera provides access to CameraControl & CameraInfo
                camera = cameraProvider.bindToLifecycle(RecordFragment.this, cameraSelector, preview, videoCapture);
                // Attach the viewfinder's surface provider to preview use case
                preview.setSurfaceProvider(binding.textureViewCamera.createSurfaceProvider(camera.getCameraInfo()));


            } catch (Exception exc) {
                Log.e(TAG, "Use case binding failed", exc);
            }

        }
    };


    private void bindCameraUseCases() {
        metrics = getContext().getResources().getDisplayMetrics();
        screenAspectRatio = Utility.aspectRatio(metrics.widthPixels, metrics.heightPixels);
//        rotation = binding.textureViewCamera.getDisplay().getRotation();
        rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        cameraSelector = new CameraSelector.Builder().requireLensFacing(CAMERA_FACING_LES).build();
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
        cameraProviderFuture.addListener(cameraListener, ContextCompat.getMainExecutor(requireContext()));
    }

    private void attachCameraToView() {
        binding.textureViewCamera.post(new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                bindCameraUseCases();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                checkPermissions();
            } else {
                // Permission request was denied.
                snackbar = Snackbar.make(getActivity().findViewById(R.id.layout), R.string.camera_permission_denied,
                        Snackbar.LENGTH_INDEFINITE).setAction("Allow", v -> checkPermissions());
                snackbar.show();
            }
        } else if (requestCode == PERMISSION_REQUEST_AUDIO) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                checkPermissions();
            } else {
                // Permission request was denied.
                snackbar = Snackbar.make(getActivity().findViewById(R.id.layout), R.string.audio_permission_denied,
                        Snackbar.LENGTH_INDEFINITE).setAction("Allow", v -> checkPermissions());
                snackbar.show();
            }
        } else if (requestCode == PERMISSION_REQUEST_WRITE_EXTERNAL) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermissions();
            } else {
                // Permission request was denied.
                snackbar = Snackbar.make(getActivity().findViewById(R.id.layout), R.string.write_permission_denied, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Allow", v -> checkPermissions());
                snackbar.show();
            }
        }
    }


    VideoCapture.OnVideoSavedCallback videoSavedCallback = new VideoCapture.OnVideoSavedCallback() {
        @Override
        public void onVideoSaved(@NonNull File file) {
            Log.d(TAG, "onVideoSaved: " + file.getAbsolutePath());
            Intent showPreview = new Intent(getActivity(), PreviewVideoActivity.class);
            showPreview.putExtra(VIDEO_KEY, Uri.fromFile(file).toString());
            startActivity(showPreview);
        }

        @Override
        public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
            cause.printStackTrace();
        }
    };


}
