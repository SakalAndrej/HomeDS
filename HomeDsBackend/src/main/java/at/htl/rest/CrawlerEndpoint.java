package at.htl.rest;

import at.htl.xiboClient.Crawler;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("crawler")
public class CrawlerEndpoint {

    @Inject
    Crawler crawler;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getCrawler(@QueryParam("layoutId") long id,
                               @QueryParam("layout") String name) {
        return Response.ok(crawler.getLayoutsWithAllSubEntities(id,name)).build();
    }
}
