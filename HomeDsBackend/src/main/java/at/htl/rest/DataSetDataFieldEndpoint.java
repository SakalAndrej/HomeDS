package at.htl.rest;

import at.htl.facades.DataSetFieldFacade;
import at.htl.model.DataSetDataField;
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

    @GET
    @Produces("application/json")
    @Path("/")
    @ApiOperation("Get all DataSets")
    public Response getDataSet() {
        List<DataSetDataField> dataFields = dataSetFieldFacade.getAll();

        if (dataFields != null) {
            return Response.ok(dataFields).build();
        }
        return Response.noContent().build();
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
