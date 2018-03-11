package at.htl.rest;

import at.htl.exceptions.NoConnectionException;
import at.htl.xiboClient.CrawlerApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("crawler")
@Api(value = "Crawler - API", description = "Crawler Operation")
public class CrawlerEndpoint {

    @Inject
    private
    CrawlerApi crawler;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @ApiOperation(value = "Get all Crawled things", code = 200, response = String.class )
    public Response getCrawler(@QueryParam("layoutId") long id,
                               @QueryParam("layout") String name) {
        try {
            return Response.ok(crawler.getLayoutsWithAllSubEntities(id,name)).build();
        } catch (NoConnectionException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
