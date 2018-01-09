package at.htl.remotexibo.entity;

public class Layout {

    private Long layoutId;
    private String ownerId;
    private String campaignId;
    private String backgroundImageId;
    private String schemaVersion;
    private String layout;
    private String description;
    private String backgroundColor;
    private String createdDt;
    private String modifiedDt;
    private String status;
    private String retired;
    private String backgroundzIndex;
    private String width;
    private String height;
    private String displayOrder;
    private String duration;
    private String statusMessage;

    public Layout() {
    }

    public Layout(String layout, Long layoutId ){
        this.layout = layout;
        this.layoutId = layoutId;
    }
    public Layout(Long layoutId, String ownerId, String campaignId, String backgroundImageId, String schemaVersion,
                  String layout, String description, String backgroundColor, String createdDt, String modifiedDt, String status,
                  String retired, String backgroundzIndex, String width, String height, String displayOrder, String duration,
                  String statusMessage) {
        this.layoutId = layoutId;
        this.ownerId = ownerId;
        this.campaignId = campaignId;
        this.backgroundImageId = backgroundImageId;
        this.schemaVersion = schemaVersion;
        this.layout = layout;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.createdDt = createdDt;
        this.modifiedDt = modifiedDt;
        this.status = status;
        this.retired = retired;
        this.backgroundzIndex = backgroundzIndex;
        this.width = width;
        this.height = height;
        this.displayOrder = displayOrder;
        this.duration = duration;
        this.statusMessage = statusMessage;
    }

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getBackgroundImageId() {
        return backgroundImageId;
    }

    public void setBackgroundImageId(String backgroundImageId) {
        this.backgroundImageId = backgroundImageId;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    public String getModifiedDt() {
        return modifiedDt;
    }

    public void setModifiedDt(String modifiedDt) {
        this.modifiedDt = modifiedDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRetired() {
        return retired;
    }

    public void setRetired(String retired) {
        this.retired = retired;
    }

    public String getBackgroundzIndex() {
        return backgroundzIndex;
    }

    public void setBackgroundzIndex(String backgroundzIndex) {
        this.backgroundzIndex = backgroundzIndex;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
