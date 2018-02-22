package at.htl.rest;

import at.htl.exceptions.NoConnectionException;
import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;
import at.htl.xiboClient.DataSetApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("datasetdatafield")
@Api("DatasetDataField")
public class DataSetDataFieldEndpoint {

    @Inject
    DataSetFieldFacade dataSetFieldFacade;

    @Inject
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
    @Path("/")
    @ApiOperation("Save DataSetRow")
    public Response addDataSetDataField(DataSetDataField dataField) {
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    @ApiOperation("Edit DataSetRow")
    public Response editDataSetDataField(DataSetDataField dataField) {
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

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{dataid}/{datarowid}")
    @ApiOperation("Delete DataSetRow")
    public Response addDataSetDataField(
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
