package at.htl.rest;

import at.htl.enums.RequestTypeEnum;
import at.htl.utils.AuthentificationHandler;
import at.htl.utils.RequestHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;

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

        //Get all Datasets
        HttpURLConnection con = RequestHelper.get_instance()
                .executeRequest(RequestTypeEnum.GET,
                        null,
                        RequestHelper.get_instance().BASE_URL + "api/dataset",
                        AuthentificationHandler.getTOKEN());

        if (responseCode == 200) {
            return Response.ok(responseCode).build();
        } else {
            return Response.status(responseCode).build();
        }
    }
}
