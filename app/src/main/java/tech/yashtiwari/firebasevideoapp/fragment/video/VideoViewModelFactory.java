package tech.yashtiwari.firebasevideoapp.fragment.video;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.SimpleExoPlayer;

public class VideoViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    Context context;
    SimpleExoPlayer player;

    public VideoViewModelFactory(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new VideoViewModel();
    }
}
