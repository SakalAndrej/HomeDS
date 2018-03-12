package homeds.htl.at.homedsjee.entity;

/**
 * Created by Felix on 12.03.2018.
 */

public class Display {
    private long displayId;
    private String display;
    private String description;
    private long defaultLayoutId;
    private long displayGroupId;
    private String deviceName;
    private long currentLayoutId;

    public Display(long displayId, String display, String description, long defaultLayoutId, long displayGroupId, String deviceName, long currentLayoutId) {
        this.displayId = displayId;
        this.display = display;
        this.description = description;
        this.defaultLayoutId = defaultLayoutId;
        this.displayGroupId = displayGroupId;
        this.deviceName = deviceName;
        this.currentLayoutId = currentLayoutId;
    }

    public Display() {

    }


    public long getDisplayId() {
        return displayId;
    }

    public void setDisplayId(long displayId) {
        this.displayId = displayId;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDefaultLayoutId() {
        return defaultLayoutId;
    }

    public void setDefaultLayoutId(long defaultLayoutId) {
        this.defaultLayoutId = defaultLayoutId;
    }

    public long getDisplayGroupId() {
        return displayGroupId;
    }

    public void setDisplayGroupId(long displayGroupId) {
        this.displayGroupId = displayGroupId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public long getCurrentLayoutId() {
        return currentLayoutId;
    }

    public void setCurrentLayoutId(long currentLayoutId) {
        this.currentLayoutId = currentLayoutId;
    }


}
