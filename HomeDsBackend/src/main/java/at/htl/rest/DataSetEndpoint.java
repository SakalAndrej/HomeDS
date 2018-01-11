package at.htl.rest;

import at.htl.model.DataSet;
import at.htl.model.DataSetData;
import at.htl.xiboClient.DataSetApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.LinkedList;

@Path("dataset")
@Api("Dataset")
public class DataSetEndpoint {

    @Inject
    DataSetApi dataSetApi;

    @GET
    @Produces("application/json")
    @Path("/")
    @ApiOperation("Get all or filter DataSets from Server")
    public Response getDataSet(@QueryParam("dataSetId") long dataSetId,
                               @QueryParam("dataSet") String dataSet,
                               @QueryParam("dataSetCode") String dataSetCode) {

        LinkedList<DataSet> dataSets = dataSetApi.getAllDataSet(dataSetId, dataSet, dataSetCode);

        return Response.ok(dataSets).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{dataSetId}")
    @ApiOperation("Get the DataSetData filtered by DataSet")
    public Response getDataSetData(@PathParam("dataSetId") long dataSetId) {

        LinkedList<DataSetData> dataSetDatas = dataSetApi.getAllDataSetData(dataSetId);

        return Response.ok(dataSetDatas).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{dataSetId}/{dataSetDataId}")
    @ApiOperation("Edit from a DataSet in the Row one Field by columnid")
    public Response editDataSetField(@PathParam("dataSetId") long dataSetId,
                                     @PathParam("dataSetDataId") long dataSetDataId,
                                     @QueryParam("dataSetColumnId") long dataSetColumnId,
                                     @QueryParam("dataSetFieldValue") String dataSetFieldValue) {

        long id = dataSetApi.editDataSetField(dataSetId, dataSetDataId, dataSetColumnId, dataSetFieldValue);

        return Response.ok(id).build();
    }

}
