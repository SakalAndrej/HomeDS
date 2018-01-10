package at.htl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("product")
@Api("Product")
public class ProductEndpoint {

    @Path("seo")
    @ApiOperation("Seo Audit triggern")
    @GET
    public Response seoAudit() {
        return Response.ok("hi").build();
    }
}
