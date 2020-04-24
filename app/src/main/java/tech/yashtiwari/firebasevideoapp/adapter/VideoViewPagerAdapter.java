package tech.yashtiwari.firebasevideoapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.List;

import tech.yashtiwari.firebasevideoapp.R;
import tech.yashtiwari.firebasevideoapp.databinding.RvVideoBinding;


/*NOT IN USE*/
public class VideoViewPagerAdapter extends RecyclerView.Adapter<VideoViewPagerAdapter.ViewHolder> {

    private final Context context;
    private List<String> videoURL;
    private RvVideoBinding binding;
    private static final String TAG = "VideoViewPagerAdapter";
    private String[] videos = {"https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761"};

    public VideoViewPagerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.rv_video, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewPagerAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return videos.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RvVideoBinding binding;
        SimpleExoPlayer player;

        private boolean playWhenReady = true;


        public ViewHolder(@NonNull RvVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private MediaSource buildMediaSource(Uri uri) {
            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(context, "videoapp");
            return new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri);
        }

        private void initializePlayer(int position) {

            if (player != null){
                player.seekTo(0);
            } else {

                player = new SimpleExoPlayer.Builder(context).build();
                Uri uri = Uri.parse(videos[position]);
                MediaSource mediaSource = buildMediaSource(uri);
                player.setPlayWhenReady(playWhenReady);
                player.seekTo(0);
            player.setRepeatMode(Player.REPEAT_MODE_ALL);
                player.prepare(mediaSource, true, true);
                binding.playerView.setUseController(false);
                binding.playerView.setPlayer(player);
            }
        }

        private void releasePlayer() {
            if (player != null) {
                playWhenReady = true;
                player.release();
                player = null;
            }
        }

        public void bind(int position) {
            releasePlayer();
            initializePlayer(position);
        }
    }
}
