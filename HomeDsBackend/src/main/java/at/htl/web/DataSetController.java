package at.htl.web;

import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Model
@Named
public class DataSetController implements Serializable {

    @Inject
    DataSetFieldFacade dataSetFieldFacade;

    private List<DataSetDataField> dataSetData;

    private DataSetDataField selectedDataSet;

    public DataSetController() {
        selectedDataSet = new DataSetDataField();
    }

    @PostConstruct
    public void init() {
        dataSetData = dataSetFieldFacade.getAll();
    }

    public void removeDataSet(DataSetDataField dataSet) {
        if (dataSet != null && (dataSet.getDataSetColumnId() != -1 || dataSet.getDataSetColumnId() != 0)) {
            dataSetFieldFacade.delete(dataSet.getDataSetColumnId());
        }
    }

    public void onSelect(DataSetDataField dataSet, String typeOfSelection, String indexes) {
        if (null != dataSet) {
            selectedDataSet = dataSet;

            MessagesController cr = new MessagesController();
            cr.setMessage(" " + dataSet.getDataId() + " wurde ausgewählt");
            cr.TriggerInfoMessage();
        }
    }

    //region Getter

    public List<DataSetDataField> getDataSetData() {
        return dataSetData;
    }

    public void setDataSetData(List<DataSetDataField> DataSetDataField) {
        this.dataSetData = dataSetData;
    }

    //endregion
}
