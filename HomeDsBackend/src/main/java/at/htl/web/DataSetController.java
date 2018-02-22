package at.htl.web;

import at.htl.exceptions.NoConnectionException;
import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;
import at.htl.xiboClient.DataSetApi;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
        FacesContext context = FacesContext.getCurrentInstance();
        try {

            if (dataSet != null && (dataSet.getDataSetId() != -1 || dataSet.getDataSetId() != 0)) {
                if (dataSetApi.removeRow(dataSet.getDataRowId(), dataSet.getDataSetId()) == 204) {
                    dataSetFieldFacade.delete(dataSet.getDataRowId());
                    dataSetData = dataSetFieldFacade.getAll();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Succesfully deleted Entity: " + dataSet.getDataRowId()));
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error while deleting Entity: " + dataSet.getDataRowId()));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Nothing to delete" + dataSet.getDataRowId()));
            }
        } catch (NoConnectionException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Error while establishing a connection"));
        }
    }

    public void addDataSet() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            long id = this.dataSetApi.addDataSetField(dataSetToAdd.getTitle(), dataSetToAdd.getValue());

            if (id > 0) {
                dataSetToAdd.setDataRowId(id);
                dataSetFieldFacade.save(dataSetToAdd);
                dataSetData = dataSetFieldFacade.getAll();
                dataSetToAdd = new DataSetDataField();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Succesfully added new DataSetRow"));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error while adding Entity"));
            }
        } catch (NoConnectionException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Error while establishing a connection"));
        }
    }

    public void editDataSet(DataSetDataField dataSetFieldToEdit) {
        if (dataSetFieldToEdit != null && dataSetFieldToEdit.getDataRowId() > 0 && dataSetFieldToEdit.getValue().isEmpty() == false && dataSetFieldToEdit.getTitle().isEmpty() == false) {
            this.dataSetApi.editDataSetField(dataSetFieldToEdit.getDataSetId(), dataSetFieldToEdit.getDataRowId())
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
    //endregion
}
