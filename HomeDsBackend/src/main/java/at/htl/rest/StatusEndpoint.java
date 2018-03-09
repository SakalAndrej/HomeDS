package at.htl.rest;

import at.htl.exceptions.NoConnectionException;
import at.htl.xiboClient.StatusApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("status")
@Api("Status - API")
public class StatusEndpoint {

    @Inject
    private
    StatusApi statusApi;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @ApiOperation("Get server Status")
    public Response getCrawler() {
        try {
            if (statusApi.getIsOnline()) {
                return Response.ok().build();
            }
            else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
