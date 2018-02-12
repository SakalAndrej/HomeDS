package at.htl.web;

import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;
import at.htl.xiboClient.DataSetApi;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import static sun.jvm.hotspot.runtime.PerfMemory.start;

@Model
@Named
public class DataSetController implements Serializable {

    @Inject
    DataSetFieldFacade dataSetFieldFacade;

    private List<DataSetDataField> dataSetData;

    private DataSetDataField dataSetToAdd;

    public DataSetController() {
        dataSetToAdd = new DataSetDataField();
    }

    @PostConstruct
    public void init() {
        dataSetData = dataSetFieldFacade.getAll();
        dataSetToAdd = new DataSetDataField();
    }

    public void removeDataSet(DataSetDataField dataSet) {
        if (dataSet != null && (dataSet.getDataSetColumnId() != -1 || dataSet.getDataSetColumnId() != 0)) {
            dataSetFieldFacade.delete(dataSet.getDataSetColumnId());
            dataSetData = dataSetFieldFacade.getAll();
        }
    }

    public void onSelect(DataSetDataField dataSet, String typeOfSelection, String indexes) {
        if (null != dataSet) {

            MessagesController cr = new MessagesController();
            cr.setMessage(" " + dataSet.getDataId() + " wurde ausgewählt");
            cr.TriggerInfoMessage();
        }
    }

    public void addDataSet() {
        dataSetFieldFacade.save(dataSetToAdd);
        dataSetData = dataSetFieldFacade.getAll();
        DataSetApi.addDataSetField(dataSetToAdd.getTitle(),dataSetToAdd.getValue());
        dataSetToAdd = new DataSetDataField();
    }

    //region Getter

    public List<DataSetDataField> getDataSetData() {
        return dataSetData;
    }

    public void setDataSetData(List<DataSetDataField> DataSetDataField) {
        this.dataSetData = dataSetData;
    }

    public DataSetFieldFacade getDataSetFieldFacade() {
        return dataSetFieldFacade;
    }

    public void setDataSetFieldFacade(DataSetFieldFacade dataSetFieldFacade) {
        this.dataSetFieldFacade = dataSetFieldFacade;
    }

    public DataSetDataField getDataSetToAdd() {
        return dataSetToAdd;
    }

    public void setDataSetToAdd(DataSetDataField dataSetToAdd) {
        this.dataSetToAdd = dataSetToAdd;
    }

    //endregion
}
