package tech.yashtiwari.firebasevideoapp.util;

import android.content.Context;
import android.os.Environment;

import androidx.camera.core.AspectRatio;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utility {


    public static String TAG = "CameraXBasic";
    public static String FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS";
    public static String VIDEO_EXTENSION = ".mp4";
    public static double RATIO_4_3_VALUE = 4.0 / 3.0;
    public static double RATIO_16_9_VALUE = 16.0 / 9.0;
    public static final String VIDEO_KEY = "video";

    /**
     * Helper function used to create a timestamped file
     */
    public static File createFile(Context context, String format) {

        String fileName =  getFileName(format);
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES),fileName);
        return file;

    }
    private static String getFileName(String format){
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


}
