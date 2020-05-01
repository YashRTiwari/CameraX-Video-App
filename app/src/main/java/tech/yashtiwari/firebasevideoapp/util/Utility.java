package tech.yashtiwari.firebasevideoapp.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import androidx.camera.core.AspectRatio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import tech.yashtiwari.firebasevideoapp.R;

public class Utility {


    public static String TAG = "CameraXBasic";
    public static String FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS";
    public static String VIDEO_EXTENSION = ".fmp4";
    public static double RATIO_4_3_VALUE = 4.0 / 3.0;
    public static double RATIO_16_9_VALUE = 16.0 / 9.0;
    public static final String VIDEO_KEY = "video";

    /**
     * Helper function used to create a timestamped file
     */
    public static File createFile(Context context, String format) {

        String fileName = getFileName(format);
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), fileName);
        return file;

    }

    private static String getFileName(String format) {
        return new SimpleDateFormat(format, Locale.US)
                .format(System.currentTimeMillis()) + VIDEO_EXTENSION;
    }

    public static int aspectRatio(int width, int height) {
        double previewRatio = (double) Math.max(width, height) / ((double) Math.min(width, height));
        if (Math.abs(previewRatio - Utility.RATIO_4_3_VALUE) <= Math.abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3;
        }
        return AspectRatio.RATIO_16_9;
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static int updateGalleryInfo(Context context, File file, Uri uri) {

        ContentValues valuesvideos;
        valuesvideos = new ContentValues();
        valuesvideos.put(MediaStore.Video.Media.TITLE, file.getName());
        valuesvideos.put(MediaStore.Video.Media.DISPLAY_NAME, file.getName());
        valuesvideos.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        valuesvideos.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        valuesvideos.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
        valuesvideos.put(MediaStore.Video.Media.IS_PENDING, 1);
        ContentResolver resolver = context.getContentResolver();
        Uri collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        Uri uriSavedVideo = resolver.insert(collection, valuesvideos);


        ParcelFileDescriptor pfd;

        try {
            pfd = context.getContentResolver().openFileDescriptor(uriSavedVideo, "w");

            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
            File videoFile = new File(storageDir, file.getName());
            FileOutputStream out = new FileOutputStream(pfd.getFileDescriptor());

            FileInputStream in = new FileInputStream(videoFile);

            byte[] buf = new byte[8192];
            int len;
            while ((len = in.read(buf)) > 0) {

                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            pfd.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
        valuesvideos.clear();
        valuesvideos.put(MediaStore.Video.Media.IS_PENDING, 0);
        int numOfRowsUpdated = context.getContentResolver().update(uriSavedVideo, valuesvideos, null, null);
        return numOfRowsUpdated;
    }

}
