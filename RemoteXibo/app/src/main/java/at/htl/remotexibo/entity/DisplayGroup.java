package at.htl.remotexibo.entity;

public class DisplayGroup {

    public String displayGroupName;

    public String displayGroupDescription;

    public long displayGroupId;


    //region Getter & Setter


    public DisplayGroup(String displayGroupName, String displayGroupDescription, long displayGroupId) {
        this.displayGroupName = displayGroupName;
        this.displayGroupDescription = displayGroupDescription;
        this.displayGroupId = displayGroupId;
    }

    public DisplayGroup(String displayGroupName, String displayGroupDescription) {
        this.displayGroupName = displayGroupName;
        this.displayGroupDescription = displayGroupDescription;
    }

    public DisplayGroup() {
    }

    public long getDisplayGroupId() {
        return displayGroupId;
    }

    public void setDisplayGroupId(long displayGroupId) {
        this.displayGroupId = displayGroupId;
    }

    public String getDisplayGroupName() {
        return displayGroupName;
    }

    public void setDisplayGroupName(String displayGroupName) {
        this.displayGroupName = displayGroupName;
    }

    public String getDisplayGroupDescription() {
        return displayGroupDescription;
    }

    public void setDisplayGroupDescription(String displayGroupDescription) {
        this.displayGroupDescription = displayGroupDescription;
    }

    //endregion
}
