package at.htl.web;

import at.htl.exceptions.NoConnectionException;
import at.htl.model.Display;
import at.htl.xiboClient.DisplayApi;
import at.htl.xiboClient.StatusApi;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Model
@Named
public class IndexController {

    @Inject
    StatusApi statusApi;

    @Inject
    DisplayApi displayApi;

    private boolean isOnline = false;

    @PostConstruct
    public void init() {
        try {
            displayApi.GetAllDisplays();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }

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
