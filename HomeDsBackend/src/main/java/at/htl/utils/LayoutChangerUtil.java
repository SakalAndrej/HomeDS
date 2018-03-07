package at.htl.utils;

import at.htl.enums.XiboEnum;
import at.htl.exceptions.NoConnectionException;
import at.htl.facades.CampaignFacade;
import at.htl.facades.DataSetFieldFacade;
import at.htl.model.Campaign;
import at.htl.xiboClient.DataSetApi;
import at.htl.xiboClient.DisplayApi;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
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

    public void changeLayout(long campaignLayoutId, LocalDateTime toDate, XiboEnum xiboEnum) throws NoConnectionException {

        if (xiboEnum != null) {
            if (xiboEnum.name().equals(XiboEnum.DATASET.name())) {
                if (campaignFacade.getAllDataSet().size() > 0) {
                    // cancel campaign
                } else {
                    Campaign c = new Campaign();
                    c.setXiboEnum(xiboEnum);
                    long id;
                    if ((id = displayApi.scheduleLayout(campaignLayoutId, LocalDateTime.now(), toDate, xiboEnum)) > -1) {
                        c.setCampaignId(id);
                        campaignFacade.save(c);
                    }
                    dataSetApi.collectNowAll();
                }
            } else if (xiboEnum.name().equals(XiboEnum.MEDIA.name())) {

                //Abbrechen wenn eine bestehende campaign für medias existiert!
                List<Campaign> temp = campaignFacade.getAllMedia();
                if (temp.size() > 0) {
                    if (displayApi.deleteEvent(temp.get(0).getCampaignId())) {
                        campaignFacade.delete(temp.get(0).getId());
                    }
                }

                // hinzufügen
                Campaign c = new Campaign();
                c.setXiboEnum(xiboEnum);
                long id;
                if ((id = displayApi.scheduleLayout(campaignLayoutId, LocalDateTime.now(), toDate, xiboEnum)) > -1) {
                    c.setCampaignId(id);
                    campaignFacade.save(c);
                }
                dataSetApi.collectNowAll();
            }
        }
    }

    /*
    Wenn keine DataSets verfügbar Layout auf normal wechseln (alte kampagne löschen falls es eine alte gibt wenn nicht nichts tun)
    Wenn DataSets verfügbar sind dataset layout einlegen (neue kampagne anlegen)
     */
    public void campaignLogicDataSet() {

        if (dataSetFieldFacade.getActiveDataSetRows().size() > 0) {
            try {
                long id = -1;
                if (campaignFacade.getAll().size() <= 0 && (id = displayApi.scheduleLayout(43, LocalDateTime.now(), LocalDateTime.now().plusYears(2), XiboEnum.DATASET)) > 0) {
                    campaignFacade.save(new Campaign(id,XiboEnum.DATASET));
                }
            } catch (NoConnectionException e) {
                e.printStackTrace();
            }
        } else {
            List<Campaign> temp = campaignFacade.getAllDataSet();
            if (temp != null && temp.size() > 0) {
                long deleteId = temp.get(0).getId();

                try {
                    if (displayApi.deleteEvent(temp.get(0).getCampaignId())) {
                        campaignFacade.delete(deleteId);
                    }
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
