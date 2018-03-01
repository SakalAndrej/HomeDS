package at.htl.model;

public class Media {

    private Long mediaId;
    private Long ownerId;
    private String name;
    private String mediaType;

    public Media(Long mediaId, Long ownerId, String name, String mediaType) {
        this.mediaId = mediaId;
        this.ownerId = ownerId;
        this.name = name;
        this.mediaType = mediaType;
    }

    public Media() { }

    //region Getter & Setter
    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    //endregion
}
