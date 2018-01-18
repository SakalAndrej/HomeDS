package at.htl.web;

import at.htl.model.DataSet;
import at.htl.model.DataSetData;
import at.htl.xiboClient.DataSetApi;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Model
public class DataSetController implements Serializable {

    @Inject
    DataSetApi dataSetApi;

    private DataSet selectedDataSet;

    public DataSetController() {
        selectedDataSet = new DataSet();
    }

    public List<DataSet> getDataSets() {
        return dataSetApi.getAllDataSet(-1,null,null);
    }

    public void onSelect(DataSet dataSet, String typeOfSelection, String indexes) {
        if (null != dataSet) {
            selectedDataSet = dataSet;

            MessagesController cr = new MessagesController();
            cr.setMessage(" " + dataSet.getDataSetName() + " wurde ausgewählt");
            cr.TriggerInfoMessage();
        }
    }

    public LinkedList<DataSetData> getDataSetDataById() {
        LinkedList<DataSetData> f = new LinkedList<>();
        f = dataSetApi.getAllDataSetData(3);

        return f;
    }

    //region Getter
    public DataSet getSelectedDataSets() {
        return selectedDataSet;
    }
    //endregion
}
