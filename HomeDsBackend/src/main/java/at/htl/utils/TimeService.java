package at.htl.utils;

import at.htl.exceptions.NoConnectionException;
import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;
import at.htl.xiboClient.DataSetApi;
import at.htl.xiboClient.helper.AuthentificationHandler;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.LinkedList;
import java.util.List;

@Singleton
@Startup
public class TimeService {

    @EJB
    DataSetFieldFacade dataSetFieldFacade;

    @EJB
    DataSetApi dataSetApi;

    @Schedule(minute = "*", hour = "*")
    public void doWork() {
        List<DataSetDataField> datafields = dataSetFieldFacade.getAll();
        if (datafields != null && datafields.size() > 0) {
            for (int i = 0; i < datafields.size(); i++) {

                // Null checks of to date
                if (datafields.get(i) != null && datafields.get(i).getToDate() != null ) {
                    // check if todate is after today
                    if (datafields.get(i).getToDate().isAfter(LocalDate.now()))
                        System.out.println(String.format("Entity: %s, Everything okay, the toDate is after today", datafields.get(i).getDataRowId()));
                    else {
                        try {
                            if (204 == dataSetApi.removeRow(datafields.get(i).getDataRowId(), datafields.get(i).getDataSetId())) {
                                dataSetFieldFacade.delete(datafields.get(i).getDataRowId());
                                System.out.println(String.format("Entity: %s, the toDate is expired!", datafields.get(i).getDataRowId()));
                            }

                        } catch (NoConnectionException e) {
                            System.out.println("Could not establish a connection");
                        }
                    }
                } else {
                    System.out.println(String.format("Entity: %s, toDate is unavailable or entity is null", datafields.get(i).getDataRowId()));
                }
            }
            System.out.println("Every min scheduler succesfully did his job :)!");
        }
        else {
            System.out.println("Every min scheduler had no job :(!");
        }
    }

    @Schedule(hour = "20", minute = "10")
    public void doSomething() {
        System.out.println("hour triggered");
    }

    public TimeService() {
        System.out.println("Hello one minute");
    }

    @PostConstruct
    public void init() {
        System.out.println("something");
    }


}
