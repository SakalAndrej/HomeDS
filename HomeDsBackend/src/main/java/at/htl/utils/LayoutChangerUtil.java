package at.htl.utils;

import at.htl.exceptions.NoConnectionException;
import at.htl.facades.CampaignFacade;
import at.htl.facades.DataSetFieldFacade;
import at.htl.model.Campaign;
import at.htl.xiboClient.DataSetApi;
import at.htl.xiboClient.DisplayApi;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;

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

    public void changeLayout(long campaignLayoutId, LocalDateTime toDate) throws NoConnectionException {
        if (campaignFacade.getAll().size()>0) {
            // cancel campaign
        }
        else {
            Campaign c = new Campaign();
            long id;
            if((id=displayApi.scheduleLayout(campaignLayoutId, LocalDateTime.now(), toDate)) > -1) {
                c.setCampaignId(id);
                campaignFacade.save(c);
            }
            dataSetApi.collectNowAll();
        }

    }

    /*
    Wenn keine DataSets verfügbar Layout auf normal wechseln (alte kampagne löschen falls es eine alte gibt wenn nicht nichts tun)
    Wenn DataSets verfügbar sind dataset layout einlegen (neue kampagne anlegen)
     */
    public void campaignLogic() {
        if (dataSetFieldFacade.getActiveDataSetRows().size() > 0) {
            try {
                long id = -1;
                if (campaignFacade.getAll().size() <= 0 && (id = displayApi.scheduleLayout(43, LocalDateTime.now(), LocalDateTime.now().plusMinutes(2))) > 0) {
                    campaignFacade.save(new Campaign(id));
                }
            } catch (NoConnectionException e) {
                e.printStackTrace();
            }
        } else {
            if (campaignFacade.getAll() != null && campaignFacade.getAll().size()>0) {
                long deleteId = campaignFacade.getAll().get(0).getId();

                try {
                    if (displayApi.deleteEvent(campaignFacade.getAll().get(0).getCampaignId())) {
                        campaignFacade.delete(deleteId);
                    }
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
