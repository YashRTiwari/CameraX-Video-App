package tech.yashtiwari.firebasevideoapp.model;

public class VideoModel {


    private String videoLink;
    private String videoName;
    private String videoId;
    private long videoCreatedOn;

    public VideoModel() {

    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setVideoCreatedOn(long videoCreatedOn) {
        this.videoCreatedOn = videoCreatedOn;
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
