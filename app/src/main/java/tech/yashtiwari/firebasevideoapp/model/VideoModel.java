package tech.yashtiwari.firebasevideoapp.model;

public class VideoModel {


    private String videoLink;
    private String videoName;
    private String videoId;
    private long videoCreatedOn;

    public VideoModel(String id, String url) {
        this.videoLink = url;
        this.videoName = id;
        videoId = id;
        videoCreatedOn = System.currentTimeMillis();
    }

    public String getVideoLink() {
        return videoLink;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoId() {
        return videoId;
    }

    public long getVideoCreatedOn() {
        return videoCreatedOn;
    }

}
