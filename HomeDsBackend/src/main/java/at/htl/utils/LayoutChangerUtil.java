package at.htl.utils;

import at.htl.exceptions.NoConnectionException;
import at.htl.model.DataSet;
import at.htl.model.Display;
import at.htl.xiboClient.DataSetApi;
import at.htl.xiboClient.DisplayApi;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.LinkedList;

@Stateless
public class LayoutChangerUtil {

    @Inject
    DisplayApi displayApi;

    @Inject
    DataSetApi dataSetApi;

    public void changeLayoutForAll(int layoutId) {
        try {
            LinkedList<Display> displays = displayApi.GetAllDisplays();

            /*for (int i = 0; i < displays.size(); i++) {
                displayApi.ChangeLayout(displays.get(i).getDisplayGroupId(), layoutId);
            }*/
            displayApi.ScheduleLayout(LocalDateTime.now(),LocalDateTime.now().plusMinutes(2));
            dataSetApi.collectNowAll();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }

    }
}
