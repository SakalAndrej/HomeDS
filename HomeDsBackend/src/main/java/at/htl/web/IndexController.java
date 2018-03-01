package at.htl.web;

import at.htl.exceptions.NoConnectionException;
import at.htl.xiboClient.StatusApi;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Model
@Named
public class IndexController {

    @Inject
    StatusApi statusApi;

    private boolean isOnline = false;

    //region Getter & Setter
    public boolean isOnline() {
        try {
            return statusApi.getIsOnline();
        } catch (NoConnectionException e) {
            return false;
        }
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
    //endregion

}
