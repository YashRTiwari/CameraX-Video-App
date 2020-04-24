package tech.yashtiwari.firebasevideoapp.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static tech.yashtiwari.firebasevideoapp.services.MyUploadService.EXTRA_DOWNLOAD_URL;
import static tech.yashtiwari.firebasevideoapp.services.MyUploadService.UPLOAD_COMPLETED;

public class MyBroadCastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == UPLOAD_COMPLETED){

            String downloadUri = intent.getStringExtra(EXTRA_DOWNLOAD_URL);
            Toast.makeText(context, downloadUri, Toast.LENGTH_SHORT).show();


        }
    }
}
