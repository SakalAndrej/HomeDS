package at.htl.web;

import at.htl.facades.DataSetDataFacade;
import at.htl.model.DataSet;
import at.htl.model.DataSetData;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.LinkedList;

@Model
public class DataSetController implements Serializable {

    @Inject
    DataSetDataFacade dataSetDataFacade;

    private DataSet selectedDataSet;

    private LinkedList<DataSetData> dataSetData;

    public DataSetController() {
        selectedDataSet = new DataSet();

    }

    @PostConstruct
    public void init() {
        dataSetData = new LinkedList<>();
        dataSetData.add(dataSetDataFacade.findById(1));
    }

    public void onSelect(DataSet dataSet, String typeOfSelection, String indexes) {
        if (null != dataSet) {
            selectedDataSet = dataSet;

            MessagesController cr = new MessagesController();
            cr.setMessage(" " + dataSet.getDataSetName() + " wurde ausgewählt");
            cr.TriggerInfoMessage();
        }
    }

    //region Getter

    public LinkedList<DataSetData> getDataSetData() {
        return dataSetData;
    }

    public void setDataSetData(LinkedList<DataSetData> dataSetData) {
        this.dataSetData = dataSetData;
    }

    //endregion
}
