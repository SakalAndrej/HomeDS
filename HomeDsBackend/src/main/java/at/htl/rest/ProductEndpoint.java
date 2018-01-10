package at.htl.rest;

import filter.MotoroilDirektHarvester;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import at.htl.model.Products;
import seo.SeoAudit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedList;

@Path("product")
@Api("Product")
public class ProductEndpoint {

    @Inject
    MotoroilDirektHarvester md;

    @Path("start")
    @ApiOperation("Das Harvesten wird gestartet")
    @GET
    public Response startHarvest() {
        md.InitializeLinks();
        LinkedList<String> all = md.getAllLinks();
        md.HarvestAllSites(all);
        return Response.ok().build();
    }

    @Path("seo")
    @ApiOperation("Seo Audit triggern")
    @GET
    public Response seoAudit() {
        SeoAudit.addOilFinderMannol(md.GetMannolProducts());
        return Response.ok().build();
    }

    @Path("export")
    @ApiOperation("Exportieren des Betandes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response exportStockByBrand(@QueryParam("sku") boolean sku,@QueryParam("baseimage") boolean baseimage, @QueryParam("brand") boolean brand, @QueryParam("container") boolean container, @QueryParam("description") boolean description, @QueryParam("instock") boolean instock, @QueryParam("metatitle") boolean metatitle, @QueryParam("price") boolean price, @QueryParam("related") boolean related, @QueryParam("deliverytime") boolean deliverytime, @QueryParam("orderprocessingTime") boolean orderprocessingTime ) {
        ArrayList<Products> all = md.GetProducts();

        md.ExportDatabase(all,sku,baseimage,brand,container,description,instock,metatitle,price,related,deliverytime,orderprocessingTime);
        return Response.ok(all).build();
    }

    @Path("imagename")
    @ApiOperation("Image Names making pretier")
    @GET
    public Response imagename() {
        md.MakeFileNamePrettyDb();
        return Response.ok().build();
    }

    @Path("setRelated")
    @ApiOperation("Setting the Related Products")
    @GET
    public Response setRelated() {
        ArrayList<Products> all = md.GetProducts();
        for (int i = 0; i < all.size(); i++) {
            md.CalculateRelatedProducts(all.get(i), all);
        }
        return Response.ok().build();
    }

    @Path("status")
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public String getStatus() {
        return md.GetStatus();
    }
}
