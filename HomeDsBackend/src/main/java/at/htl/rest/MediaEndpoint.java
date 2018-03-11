package at.htl.rest;

import at.htl.enums.XiboEnum;
import at.htl.exceptions.NoConnectionException;
import at.htl.utils.LayoutChangerUtil;
import at.htl.xiboClient.MediaApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Path("media")
@Api("Media - API")
public class MediaEndpoint {

    @Inject
    private
    MediaApi mediaApi;

    @Inject
    private
    LayoutChangerUtil layoutChangerUtil;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @ApiOperation("Get all Medias")
    public Response getMedias(@QueryParam("start") int start, @QueryParam("length") int length, @QueryParam("tags") String tags) {
        try {
            if (length == 0 && start == 0) {

                return Response.ok(mediaApi.getAllMedia(start, length, tags)).build();

            } else {
                return Response.ok(mediaApi.getAllMedia(start, length, tags)).build();
            }
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("play")
    @ApiOperation("Play Media")
    public Response playMedia(@QueryParam("mediaId") int mediaId, @QueryParam("displayId") int displayId) {
        try {
            long widgetId = mediaApi.findWidgetByPlaylist();
            if (widgetId > 0) {
                if (mediaApi.deleteWidget(widgetId) == 200) {
                    if (mediaApi.editWidget(mediaId) == 200) {
                        layoutChangerUtil.changeLayout(44, LocalDateTime.now().plusYears(2), XiboEnum.MEDIA,displayId);

                        Response.ok().build();
                    }
                    return Response.status(Response.Status.OK).build();
                }
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

}
