package at.htl.utils;

import at.htl.exceptions.NoConnectionException;
import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;
import at.htl.xiboClient.DataSetApi;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class DataSetFieldUtil {

    @Inject
    private
    DataSetFieldFacade dataSetFieldFacade;

    @Inject
    private
    DataSetApi dataSetApi;

    public Response.Status editDataSetField(DataSetDataField dataSetFieldToEdit) {
        if (dataSetFieldToEdit.getFromDate() != null && dataSetFieldToEdit.getToDate() != null) {

            // editing from date so that it should be active
            if (dataSetFieldToEdit.getFromDate().isBefore(LocalDate.now().plusDays(1)) && !dataSetFieldToEdit.isActive()) {

                //copy entity
                dataSetFieldFacade.deleteById(dataSetFieldToEdit.getId());
                dataSetFieldToEdit.setId(0);
                this.addDataSetToXibo(dataSetFieldToEdit);

                return Response.Status.OK;
            }
            // just edit unactive dataset
            else if ((dataSetFieldToEdit.getDataRowId() < 0 || !dataSetFieldToEdit.isActive())) {
                dataSetFieldFacade.merge(dataSetFieldToEdit);
                return Response.Status.OK;

            }
            // edit active event
            else if (dataSetFieldToEdit.isActive() && dataSetFieldToEdit.getFromDate().isBefore(LocalDate.now().plusDays(1))) {
                try {
                    if (dataSetApi.editDataSetField(dataSetFieldToEdit.getDataSetId(), dataSetFieldToEdit.getDataRowId(), 8, dataSetFieldToEdit.getTitle()) == 200 && this.dataSetApi.editDataSetField(dataSetFieldToEdit.getDataSetId(), dataSetFieldToEdit.getDataRowId(), 9, dataSetFieldToEdit.getValue()) == 200) {
                        dataSetFieldFacade.merge(dataSetFieldToEdit);

                        return Response.Status.OK;
                    } else {
                        return Response.Status.BAD_REQUEST;
                    }
                } catch (NoConnectionException e) {
                    return Response.Status.INTERNAL_SERVER_ERROR;
                }
            }
            //should edit active event to unactive
            else if (dataSetFieldToEdit.isActive() && dataSetFieldToEdit.getFromDate().isAfter(LocalDate.now())) {
                this.removeDataSet(dataSetFieldToEdit);
                dataSetFieldToEdit.setId(0);
                dataSetFieldToEdit.setActive(false);
                dataSetFieldToEdit.setDataRowId(-1);
                dataSetFieldFacade.save(dataSetFieldToEdit);
                return Response.Status.OK;
            }
        }
        return Response.Status.INTERNAL_SERVER_ERROR;
    }

    private void removeDataSet(DataSetDataField dataSet) {
        try {
            if (dataSet != null && dataSet.isActive() && (dataSet.getDataSetId() != -1 || dataSet.getDataSetId() != 0)) {
                if (dataSetApi.removeRow(dataSet.getDataRowId(), dataSet.getDataSetId()) == 204) {
                    dataSetFieldFacade.deleteByRowId(dataSet.getDataRowId());
                } else {
                }
            } else if (!dataSet.isActive()) {
                dataSetFieldFacade.deleteById(dataSet.getId());
            } else {
            }
        } catch (NoConnectionException ex) {
        }
    }

    public Response.Status addDataSet(DataSetDataField dataSetToAdd) {
        if ((dataSetToAdd.getFromDate() != null) && (!dataSetToAdd.getFromDate().isAfter(LocalDate.now()) || dataSetToAdd.getFromDate().isEqual(LocalDate.now()))) {
            this.addDataSetToXibo(dataSetToAdd);
            return Response.Status.OK;
        } else if ((dataSetToAdd.getFromDate() == null)) {
            this.addDataSetToXibo(dataSetToAdd);
            return Response.Status.OK;
        } else {
            dataSetToAdd.setDataRowId(-1);
            dataSetToAdd.setActive(false);
            dataSetFieldFacade.save(dataSetToAdd);
            return Response.Status.OK;
        }
    }

    private void addDataSetToXibo(DataSetDataField dataFieldToAdd) {
        try {
            long id = this.dataSetApi.addDataSetField(dataFieldToAdd);

            if (id > 0) {
                dataFieldToAdd.setActive(true);
                dataFieldToAdd.setDataRowId(id);
                dataSetFieldFacade.save(dataFieldToAdd);

                //clear add variable
            } else {
            }
        } catch (NoConnectionException ex) {
        }
    }

    public void checkIfAnyDataSetFieldIsAvailAbleAndActive() {
        boolean change = false;
        List<DataSetDataField> dataFields;
        if ((dataFields = dataSetFieldFacade.getAll()) != null && dataFields.size() > 0) {
            for (DataSetDataField dataField : dataFields) {
                if (dataField.isActive()) {
                    change = true;
                }
            }
        }
        if (change) {
            // do layout change
        }
    }
}
