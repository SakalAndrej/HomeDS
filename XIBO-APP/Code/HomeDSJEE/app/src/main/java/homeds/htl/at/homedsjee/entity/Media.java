package homeds.htl.at.homedsjee.entity;

/**
 * Created by Felix on 04.03.2018.
 */

public class Media {

    Long mediaId;
    Long ownerId;
    String name;
    String mediaType;


    public Media(Long mediaId,Long ownerId,String name, String mediaType) {
        this.mediaId = mediaId;
        this.name = name;
        this.ownerId = ownerId;
        this.mediaType = mediaType;
    }

    public Media() {
    }

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
}
