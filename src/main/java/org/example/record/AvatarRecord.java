package org.example.record;

public class AvatarRecord {

    private long id;
    private String mediaTipe;
    private String url;

    public AvatarRecord() {
    }

    public AvatarRecord(long id, String mediaTipe, String url) {
        this.id = id;
        this.mediaTipe = mediaTipe;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMediaTipe() {
        return mediaTipe;
    }

    public void setMediaTipe(String mediaTipe) {
        this.mediaTipe = mediaTipe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
