package tech.yashtiwari.firebasevideoapp.adapter;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import tech.yashtiwari.firebasevideoapp.fragment.video.VideoFragment;

import static tech.yashtiwari.firebasevideoapp.util.Utility.VIDEO_KEY;


public class VideoViewPagerAdapter2 extends FragmentStateAdapter {

    private String[] videos = {
            "file:///storage/emulated/0/Android/data/tech.yashtiwari.videoapp/files/Movies/1587702645432.mp4",
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
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/406256768.sd.mp4?s=0abcb1ccc536ea688f53e28e81046c56b8b47deb&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/353540228.sd.mp4?s=a19ba351f2d4008b5145d843ab8951dc3d0e8452&profile_id=165&oauth2_token_id=57447761",
            "https://player.vimeo.com/external/369238103.sd.mp4?s=401e9858f3e05098c73b6cdb4042bbdcce9fff19&profile_id=165&oauth2_token_id=57447761"};


    public VideoViewPagerAdapter2(FragmentActivity activity) {
        super(activity);
    }

    private Fragment onVideoFragment(String url){
        VideoFragment newFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_KEY, url);
        newFragment.setArguments(bundle);
        return newFragment;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return onVideoFragment(videos[position]);
    }

    @Override
    public int getItemCount() {
        return videos.length;
    }
}
