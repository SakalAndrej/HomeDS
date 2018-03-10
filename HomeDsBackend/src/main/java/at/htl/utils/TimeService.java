package at.htl.utils;

import at.htl.exceptions.NoConnectionException;
import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;
import at.htl.xiboClient.DataSetApi;
import at.htl.xiboClient.MediaApi;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.time.LocalDate;
import java.util.List;

@Singleton
@Startup
public class TimeService {

    @EJB
    private
    DataSetFieldFacade dataSetFieldFacade;

    @EJB
    private
    DataSetApi dataSetApi;

    @EJB
    private MediaApi mediaApi;

    @EJB
    private
    LayoutChangerUtil layoutChangerUtil;

    @Schedule(minute = "3", hour = "11")
    public void doWork() {
        List<DataSetDataField> datafields = dataSetFieldFacade.getAll();

        // nullchecks
        if (datafields != null && datafields.size() > 0) {

            //another null checks
            for (DataSetDataField datafield : datafields) {
                if (datafield != null && datafield.getFromDate() != null) {

                    // check if fromdate is before or today
                    if (!datafield.isActive() && (datafield.getFromDate().isEqual(LocalDate.now()) || datafield.getFromDate().isBefore(LocalDate.now()))) {
                        long id;
                        try {
                            // if suc added then set active true and add id
                            if ((id = dataSetApi.addDataSetField(datafield)) > 0) {
                                datafield.setDataRowId(id);
                                datafield.setActive(true);
                                dataSetFieldFacade.save(datafield);
                                System.out.println(String.format("FROMDATE: Entity: %s, is now active, the fromDate is before or today", datafield.getDataRowId()));
                            } else {
                                System.out.println(String.format("FROMDATE: Entity: %s, should be active, but error while adding", datafield.getDataRowId()));
                            }
                        } catch (NoConnectionException e) {
                            System.out.println(String.format("FROMDATE: No connection could be established"));
                        }
                    } else {
                        System.out.println(String.format("FROMDATE: Entity: %s, are not ready yet, the fromDate is after or today", datafield.getDataRowId()));
                    }
                }


                // Null checks of to date
                if (datafield != null && datafield.getToDate() != null) {
                    // check if todate is after today
                    if (datafield.getToDate().isAfter(LocalDate.now()) || datafield.getToDate().equals(LocalDate.now()))
                        System.out.println(String.format("TODATE: Entity: %s, Everything okay, the toDate is after today", datafield.getDataRowId()));
                    else {
                        try {
                            if (204 == dataSetApi.removeRow(datafield.getDataRowId(), datafield.getDataSetId())) {
                                dataSetFieldFacade.deleteByRowId(datafield.getDataRowId());
                                System.out.println(String.format("TODATE: Entity: %s, the toDate is expired!", datafield.getDataRowId()));
                            }

                        } catch (NoConnectionException e) {
                            System.out.println("TODATE: Could not establish a connection");
                        }
                    }
                } else {
                    System.out.println(String.format("TODATE: Entity: %s, toDate is unavailable or entity is null", datafield.getDataRowId()));
                }
            }
            System.out.println("Every min scheduler succesfully did his job :)!");
        }
        else {
            System.out.println("Every min scheduler had no job :(!");
        }

        layoutChangerUtil.campaignLogicDataSet();

    }

    @Schedule(minute = "15", hour = "*")
    public void reAuthenticate() {
        AuthentificationHandler.Authenticate();
    }

    public TimeService() { }

    @PostConstruct
    public void init() {
        AuthentificationHandler.Authenticate();
        //mediaApi.uploadMedia();
    }


}
