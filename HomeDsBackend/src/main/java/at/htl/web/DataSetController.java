package at.htl.web;

import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;
import at.htl.xiboClient.DataSetApi;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Model
@Named
public class DataSetController implements Serializable {

    @Inject
    DataSetFieldFacade dataSetFieldFacade;

    @Inject
    DataSetApi dataSetApi;

    private Date fromDate;

    private Date toDate;

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
        if (dataSet != null && (dataSet.getDataSetId() != -1 || dataSet.getDataSetId() != 0)) {
            if (dataSetApi.removeRow(dataSet.getDataRowId(), dataSet.getDataSetId()) == 204) {
                dataSetFieldFacade.delete(dataSet.getDataRowId());
                dataSetData = dataSetFieldFacade.getAll();
            }
        }
    }

    public void onSelect(DataSetDataField dataSet, String typeOfSelection, String indexes) {
        if (null != dataSet) {

            MessagesController cr = new MessagesController();
            cr.setMessage(" " + dataSet.getDataRowId() + " wurde ausgewählt");
            cr.TriggerInfoMessage();
        }
    }

    public void addDataSet() {
        long id = this.dataSetApi.addDataSetField(dataSetToAdd.getTitle(),dataSetToAdd.getValue());

        if (id > 0) {
            dataSetToAdd.setDataRowId(id);
            dataSetFieldFacade.save(dataSetToAdd);
            dataSetData = dataSetFieldFacade.getAll();
            dataSetToAdd = new DataSetDataField();
        }
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    //endregion
}
