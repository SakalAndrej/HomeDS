package at.htl.utils;

import at.htl.exceptions.NoConnectionException;
import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;
import at.htl.xiboClient.DataSetApi;

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
    DataSetFieldFacade dataSetFieldFacade;

    @EJB
    DataSetApi dataSetApi;

    @Schedule(minute = "15", hour = "22")
    public void doWork() {
        List<DataSetDataField> datafields = dataSetFieldFacade.getAll();

        // nullchecks
        if (datafields != null && datafields.size() > 0) {

            //another null checks
            for (int i = 0; i < datafields.size(); i++) {
                if (datafields.get(i) != null && datafields.get(i).getFromDate() != null) {

                    // check if fromdate is before or today
                    if (datafields.get(i).isActive() == false && (datafields.get(i).getFromDate().isEqual(LocalDate.now()) || datafields.get(i).getFromDate().isBefore(LocalDate.now()))) {
                        long id;
                        try {
                            // if suc added then set active true and add id
                            if ((id = dataSetApi.addDataSetField(datafields.get(i))) > 0) {
                                datafields.get(i).setDataRowId(id);
                                datafields.get(i).setActive(true);
                                dataSetFieldFacade.save(datafields.get(i));
                                System.out.println(String.format("FROMDATE: Entity: %s, is now active, the fromDate is before or today", datafields.get(i).getDataRowId()));
                            }
                            else {
                                System.out.println(String.format("FROMDATE: Entity: %s, should be active, but error while adding", datafields.get(i).getDataRowId()));
                            }
                        } catch (NoConnectionException e) {
                            System.out.println(String.format("FROMDATE: No connection could be established"));
                        }
                    }
                    else {
                        System.out.println(String.format("FROMDATE: Entity: %s, are not ready yet, the fromDate is after or today", datafields.get(i).getDataRowId()));
                    }
                }


                // Null checks of to date
                if (datafields.get(i) != null && datafields.get(i).getToDate() != null ) {
                    // check if todate is after today
                    if (datafields.get(i).getToDate().isAfter(LocalDate.now()) || datafields.get(i).getToDate().equals(LocalDate.now()))
                        System.out.println(String.format("TODATE: Entity: %s, Everything okay, the toDate is after today", datafields.get(i).getDataRowId()));
                    else {
                        try {
                            if (204 == dataSetApi.removeRow(datafields.get(i).getDataRowId(), datafields.get(i).getDataSetId())) {
                                dataSetFieldFacade.deleteByRowId(datafields.get(i).getDataRowId());
                                System.out.println(String.format("TODATE: Entity: %s, the toDate is expired!", datafields.get(i).getDataRowId()));
                            }

                        } catch (NoConnectionException e) {
                            System.out.println("TODATE: Could not establish a connection");
                        }
                    }
                } else {
                    System.out.println(String.format("TODATE: Entity: %s, toDate is unavailable or entity is null", datafields.get(i).getDataRowId()));
                }
            }
            System.out.println("Every min scheduler succesfully did his job :)!");
        }
        else {
            System.out.println("Every min scheduler had no job :(!");
        }
    }

    @PostConstruct
    public void init() {
        AuthentificationHandler.Authenticate();
    }


}
