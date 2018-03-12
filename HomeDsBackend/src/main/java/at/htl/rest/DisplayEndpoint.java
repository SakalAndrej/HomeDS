package at.htl.rest;

import at.htl.exceptions.NoConnectionException;
import at.htl.xiboClient.DisplayApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("display")
@Api("Display - API")
public class DisplayEndpoint {

    @Inject
    private DisplayApi displayApi;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @ApiOperation("Get Displays")
    public Response playMedia() {
        try {
            return Response.ok(displayApi.getAllDisplays()).build();
        } catch (NoConnectionException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
