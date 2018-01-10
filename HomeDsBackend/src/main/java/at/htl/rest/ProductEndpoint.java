package at.htl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("dataset")
@Api("Dataset")
public class ProductEndpoint {

    @GET
    @Produces("application/json")
    @Path("/")
    @ApiOperation("Get all or filter DataSets from Server")
    public Response getDataSet(@QueryParam("dataSetId") int dataSetId,
                               @QueryParam("dataSet") String dataSet,
                               @QueryParam("dataSetCode") String dataSetCode) {
        int responseCode = 400;


        if (responseCode == 200) {
            return Response.ok(responseCode).build();
        }
        else {
            return Response.status(responseCode).build();
        }
    }
}
