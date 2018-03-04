package at.htl.utils;

import at.htl.exceptions.NoConnectionException;
import at.htl.facades.CampaignFacade;
import at.htl.facades.DataSetFieldFacade;
import at.htl.model.Campaign;
import at.htl.model.DataSet;
import at.htl.model.DataSetDataField;
import at.htl.model.Display;
import at.htl.xiboClient.DataSetApi;
import at.htl.xiboClient.DisplayApi;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class LayoutChangerUtil {

    @Inject
    DisplayApi displayApi;

    @Inject
    DataSetApi dataSetApi;

    @Inject
    DataSetFieldFacade dataSetFieldFacade;

    @Inject
    CampaignFacade campaignFacade;

    public void changeLayout(long campaignLayoutId,LocalDateTime toDate) throws NoConnectionException {
            displayApi.ScheduleLayout(campaignLayoutId, LocalDateTime.now(),toDate);
            dataSetApi.collectNowAll();
    }

    /*
    Wenn keine DataSets verfügbar Layout auf normal wechseln (alte kampagne löschen)
    Wenn DataSets verfügbar sind dataset layout einlegen (neue kampagne anlegen)
     */
    public void campaignLogic() {
        if (dataSetFieldFacade.getActiveDataSetRows().size()>0) {
            try {
                long id = -1;
                if (campaignFacade.getAll().size() <= 0 && (id = displayApi.ScheduleLayout(43,LocalDateTime.now(), LocalDateTime.MAX)) > 0) {
                    campaignFacade.save(new Campaign(id));
                }
            } catch (NoConnectionException e) {
                e.printStackTrace();
            }
        }
        else {
            campaignFacade.delete(campaignFacade.getAll().get(0).getId());
        }
    }
}
