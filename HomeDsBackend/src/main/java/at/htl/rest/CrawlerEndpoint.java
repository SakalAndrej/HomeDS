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
@Api("Crawler - API")
public class CrawlerEndpoint {

    @Inject
    private
    CrawlerApi crawler;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @ApiOperation("Get all Crawled things")
    public Response getCrawler(@QueryParam("layoutId") long id,
                               @QueryParam("layout") String name) {
        try {
            return Response.ok(crawler.getLayoutsWithAllSubEntities(id,name)).build();
        } catch (NoConnectionException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
