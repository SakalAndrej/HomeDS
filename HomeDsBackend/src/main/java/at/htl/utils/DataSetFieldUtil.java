package at.htl.utils;

import at.htl.exceptions.NoConnectionException;
import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;
import at.htl.xiboClient.DataSetApi;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

public class DataSetFieldUtil {

    @Inject
    DataSetFieldFacade dataSetFieldFacade;

    @Inject
    DataSetApi dataSetApi;

    public Response.Status editDataSetField(DataSetDataField dataSetFieldToEdit) {
        if (dataSetFieldToEdit.getFromDate() != null && dataSetFieldToEdit.getToDate() != null) {

            // editing from date so that it should be active
            if (dataSetFieldToEdit.getFromDate().isBefore(LocalDate.now().plusDays(1)) && dataSetFieldToEdit.isActive() == false) {

                //copy entity
                DataSetDataField temp = dataSetFieldToEdit;
                dataSetFieldFacade.deleteById(dataSetFieldToEdit.getId());
                temp.setId(0);
                this.addDataSetToXibo(temp);

                return Response.Status.OK;
            }
            // just edit unactive dataset
            else if ((dataSetFieldToEdit.getDataRowId() < 0 || dataSetFieldToEdit.isActive() == false)) {
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

    public Response.Status removeDataSet(DataSetDataField dataSet) {
        try {
            if (dataSet != null && dataSet.isActive() && (dataSet.getDataSetId() != -1 || dataSet.getDataSetId() != 0)) {
                if (dataSetApi.removeRow(dataSet.getDataRowId(), dataSet.getDataSetId()) == 204) {
                    dataSetFieldFacade.deleteByRowId(dataSet.getDataRowId());
                    return Response.Status.OK;
                } else {
                    return Response.Status.INTERNAL_SERVER_ERROR;
                }
            } else if (dataSet.isActive() == false) {
                dataSetFieldFacade.deleteById(dataSet.getId());
                return Response.Status.OK;
            } else {
                return Response.Status.NO_CONTENT;
            }
        } catch (NoConnectionException ex) {
            return Response.Status.INTERNAL_SERVER_ERROR;
        }
    }


    public Response.Status addDataSet(DataSetDataField dataSetToAdd) {
        if ((dataSetToAdd.getFromDate() != null) && (dataSetToAdd.getFromDate().isAfter(LocalDate.now()) == false || dataSetToAdd.getFromDate().isEqual(LocalDate.now()) == true)) {
            this.addDataSetToXibo(dataSetToAdd);
            return Response.Status.OK;
        } else if ((dataSetToAdd.getFromDate() == null)) {
            this.addDataSetToXibo(dataSetToAdd);
            return Response.Status.OK;
        } else {
            dataSetToAdd.setDataRowId(-1);
            dataSetToAdd.setActive(false);
            dataSetFieldFacade.save(dataSetToAdd);
            dataSetToAdd = new DataSetDataField();
            return Response.Status.OK;
        }
    }

    public Response.Status addDataSetToXibo(DataSetDataField dataFieldToAdd) {
        try {
            long id = this.dataSetApi.addDataSetField(dataFieldToAdd);

            if (id > 0) {
                dataFieldToAdd.setActive(true);
                dataFieldToAdd.setDataRowId(id);
                dataSetFieldFacade.save(dataFieldToAdd);

                //clear add variable
                return Response.Status.OK;
            } else {
                return Response.Status.INTERNAL_SERVER_ERROR;
            }
        } catch (NoConnectionException ex) {
            return Response.Status.INTERNAL_SERVER_ERROR;
        }
    }
}
