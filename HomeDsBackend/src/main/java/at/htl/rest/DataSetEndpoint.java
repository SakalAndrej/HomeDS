package at.htl.rest;

import at.htl.enums.RequestTypeEnum;
import at.htl.model.DataSet;
import at.htl.model.DataSetData;
import at.htl.xiboClient.DataSetApi;
import at.htl.xiboClient.helper.AuthentificationHandler;
import at.htl.xiboClient.helper.RequestHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
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
    @ApiOperation("Get all or filter DataSets from Server")
    public Response getDataSetData(@PathParam("dataSetId") long dataSetId) {

        LinkedList<DataSetData> dataSetDatas = dataSetApi.getAllDataSetData(dataSetId);

        return Response.ok(dataSetDatas).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{dataSetId}")
    @ApiOperation("Get all or filter DataSets from Server")
    public Response get(@PathParam("dataSetId") long dataSetId) {

        LinkedList<DataSetData> dataSetDatas = dataSetApi.getAllDataSetData(dataSetId);

        return Response.ok(dataSetDatas).build();
    }
}
