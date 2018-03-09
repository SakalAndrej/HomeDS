package at.htl.rest;

import at.htl.exceptions.NoConnectionException;
import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;
import at.htl.xiboClient.DataSetApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Path("datasetdatafield")
@Api("DataSetDataField - API")
public class DataSetDataFieldEndpoint {


    //swagger url http://localhost:8080/homeds/swagger/swagger.html

    @Inject
    private
    DataSetFieldFacade dataSetFieldFacade;

    @Inject
    private
    DataSetApi dataSetApi;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @ApiOperation("Get all DataSetRows")
    public Response getDataSet() {
        List<DataSetDataField> dataFields = dataSetFieldFacade.getAll();

        if (dataFields != null) {
            return Response.ok(dataFields).build();
        }
        return Response.noContent().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save")
    @ApiOperation("Save DataSetRow")
    public Response addDataSetDataField(String request) {
        JSONObject json = new JSONObject(request);
        DataSetDataField dataField = new DataSetDataField();
        dataField.setValue( json.getString("value"));
        dataField.setTitle(json.getString("title"));
        dataField.setDataRowId(json.getLong("dataRowId"));

        if (!json.isNull("fromDate") && !json.isNull("toDate")) {
            dataField.setFromDate((new Date(json.getLong("fromDate")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            dataField.setToDate((new Date(json.getLong("toDate")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        }
        if (!json.isNull("id")&& !json.isNull("dataRowId")){
            dataField.setId(json.getLong("id"));
            dataField.setDataSetId(json.getLong("dataSetId"));
        }
        if (dataField != null) {
            dataSetFieldFacade.save(dataField);
            try {
                dataSetApi.addDataSetField(dataField);
            } catch (NoConnectionException e) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.ok(dataField.getDataRowId()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/edit")
    @ApiOperation("Edit DataSetRow")
    public Response editDataSetDataField(String request) {
        JSONObject json = new JSONObject(request);
        DataSetDataField dataField = new DataSetDataField();
        dataField.setValue( json.getString("value"));
        dataField.setTitle(json.getString("title"));
        if (!json.isNull("fromDate") && !json.isNull("toDate")) {
            dataField.setFromDate((new Date(json.getLong("fromDate")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            dataField.setToDate((new Date(json.getLong("toDate")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        }
        if (!json.isNull("id")&& !json.isNull("dataRowId")){
            dataField.setId(json.getLong("id"));
            dataField.setDataSetId(json.getLong("dataSetId"));
            dataField.setDataRowId(json.getLong("dataRowId"));
        }

        //DataSetDataField dataField = new DataSetDataField(json.getLong("dataSetId"), json.getLong("dataRowId"), json.getString("value"), json.getString("title"), LocalDate.parse(json.getString("fromDate")), LocalDate.parse(json.getString("toDate")));
        if (dataField != null) {
            dataSetFieldFacade.merge(dataField);
            try {
                dataSetApi.editDataSetField(dataField.getDataSetId(), dataField.getDataRowId(),8, dataField.getTitle());
                dataSetApi.editDataSetField(dataField.getDataSetId(), dataField.getDataRowId(),9, dataField.getValue());
            } catch (NoConnectionException e) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.ok(dataField.getDataRowId()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{dataid}/{datarowid}")
    @ApiOperation("Delete DataSetRow")
    public Response deleteDataSetDataField(
            @PathParam("dataid") long dataId,
            @PathParam("datarowid") long dataRowId) {
        try {
            if (dataSetApi.removeRow(dataRowId, dataId) == 204) {
                dataSetFieldFacade.deleteByRowId(dataRowId);
                return Response.ok().build();
            }
            else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (NoConnectionException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /*@GET
    @Produces("application/json")
    @Path("/{dataSetId}")
    @ApiOperation("Get the DataSetData filtered by DataSet")
    public Response getDataSetData(@PathParam("dataSetId") long dataSetId) {
        LinkedList<DataSetData> dataSetDatas = dataSetApi.getAllDataSetData(dataSetId);
        return Response.ok(dataSetDatas).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/edit")
    @ApiOperation("Edit a DataSetField")
    public Response editDataSetField(DataSetDataField dataSetField) {

    }*/

}
