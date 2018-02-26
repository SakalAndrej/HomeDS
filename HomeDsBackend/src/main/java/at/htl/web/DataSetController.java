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
import java.time.LocalDate;
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
        this.updateList();
        dataSetToAdd = new DataSetDataField();
    }

    public void removeDataSet(DataSetDataField dataSet) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (dataSet != null && dataSet.isActive() && (dataSet.getDataSetId() != -1 || dataSet.getDataSetId() != 0)) {
                if (dataSetApi.removeRow(dataSet.getDataRowId(), dataSet.getDataSetId()) == 204) {
                    dataSetFieldFacade.deleteByRowId(dataSet.getDataRowId());
                    this.updateList();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Succesfully deleted DataSetRow: " + dataSet.getDataRowId()));
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error while deleting DataSetRow: " + dataSet.getDataRowId()));
                }
            } else if (dataSet.isActive() == false) {
                dataSetFieldFacade.deleteById(dataSet.getId());
                this.updateList();
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Nothing to deleteByRowId" + dataSet.getDataRowId()));
            }
        } catch (NoConnectionException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Error while establishing a connection"));
        }
    }

    public void addDataSet() {
        FacesContext context = FacesContext.getCurrentInstance();
        if ((dataSetToAdd.getFromDate() != null) && (dataSetToAdd.getFromDate().isAfter(LocalDate.now()) == false || dataSetToAdd.getFromDate().isEqual(LocalDate.now()) == true)) {
            this.addDataSetToXibo(dataSetToAdd);
        } else if ((dataSetToAdd.getFromDate() == null)) {
            this.addDataSetToXibo(dataSetToAdd);
        } else {
            dataSetToAdd.setDataRowId(-1);
            dataSetToAdd.setActive(false);
            dataSetFieldFacade.save(dataSetToAdd);
            this.updateList();
            dataSetToAdd = new DataSetDataField();
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Succesfully added new DataSetRow"));
    }

    public void addDataSetToXibo(DataSetDataField dataFieldToAdd) {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            long id = this.dataSetApi.addDataSetField(dataFieldToAdd);

            if (id > 0) {
                dataFieldToAdd.setActive(true);
                dataFieldToAdd.setDataRowId(id);
                dataSetFieldFacade.save(dataFieldToAdd);
                this.updateList();

                //clear add variable
                dataSetToAdd = new DataSetDataField();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Succesfully added new DataSetRow"));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error while adding DataSetRow"));
            }
        } catch (NoConnectionException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Error while establishing a connection"));
        }
    }

    public void editDataSet(DataSetDataField dataSetFieldToEdit) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (dataSetFieldToEdit != null && dataSetFieldToEdit.getValue().isEmpty() == false && dataSetFieldToEdit.getTitle().isEmpty() == false) {

            if (dataSetFieldToEdit.getFromDate() != null && dataSetFieldToEdit.getToDate() != null) {

                // editing from date so that it should be active
                if (dataSetFieldToEdit.getFromDate().isBefore(LocalDate.now().plusDays(1)) && dataSetFieldToEdit.isActive() == false) {

                    //copy entity
                    DataSetDataField temp = dataSetFieldToEdit;
                    dataSetFieldFacade.deleteById(dataSetFieldToEdit.getId());
                    temp.setId(0);
                    this.addDataSetToXibo(temp);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Succesfully updated and activated DataSetRow"));
                }
                // just edit unactive dataset
                else if ((dataSetFieldToEdit.getDataRowId() < 0 || dataSetFieldToEdit.isActive() == false)) {
                    dataSetFieldFacade.merge(dataSetFieldToEdit);
                    this.updateList();

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", String.format("Succesfully edited DataSetRow: " + dataSetFieldToEdit.getDataRowId())));
                }
                // edit active event
                else if (dataSetFieldToEdit.isActive() && dataSetFieldToEdit.getFromDate().isBefore(LocalDate.now().plusDays(1))) {
                    try {
                        if (dataSetApi.editDataSetField(dataSetFieldToEdit.getDataSetId(), dataSetFieldToEdit.getDataRowId(), 8, dataSetFieldToEdit.getTitle()) == 200 && this.dataSetApi.editDataSetField(dataSetFieldToEdit.getDataSetId(), dataSetFieldToEdit.getDataRowId(), 9, dataSetFieldToEdit.getValue()) == 200) {
                            //dataSetFieldFacade.deleteByRowId(dataSetFieldToEdit.getDataRowId());
                            //dataSetFieldFacade.save(dataSetFieldToEdit);
                            dataSetFieldFacade.merge(dataSetFieldToEdit);
                            this.updateList();

                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", String.format("Succesfully edited DataSetRow: " + dataSetFieldToEdit.getDataRowId())));
                        } else {
                            this.updateList();
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Error while editing DataSetRow: " + dataSetFieldToEdit.getDataRowId()));
                        }
                    } catch (NoConnectionException e) {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Error while establishing a connection"));
                    }
                }
                //should edit active event to unactive
                else if (dataSetFieldToEdit.isActive() && dataSetFieldToEdit.getFromDate().isAfter(LocalDate.now())) {
                    this.removeDataSet(dataSetFieldToEdit);
                    dataSetFieldToEdit.setId(0);
                    dataSetFieldToEdit.setActive(false);
                    dataSetFieldToEdit.setDataRowId(-1);
                    dataSetFieldFacade.save(dataSetFieldToEdit);
                    this.updateList();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Succesfully updated and deactivated DataSetRow"));
                }
            }
        }
    }

    public void updateList() {
        this.dataSetData = dataSetFieldFacade.getAll();
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
