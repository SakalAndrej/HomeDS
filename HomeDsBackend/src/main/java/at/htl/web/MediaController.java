package at.htl.web;

import at.htl.exceptions.NoConnectionException;
import at.htl.model.Media;
import at.htl.utils.LayoutChangerUtil;
import at.htl.xiboClient.MediaApi;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Model
@Named
public class MediaController implements Serializable {

    @Inject
    MediaApi mediaApi;

    @Inject
    LayoutChangerUtil layoutChangerUtil;

    private static List<Media> medias;

    private static List<Media> shortMedias;

    @PostConstruct
    public void init() {
        try {
            if (medias != null) {
                //no need for update
            }
            else {
                this.updateList();
            }
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }

    private void updateList() throws NoConnectionException {
        this.medias = mediaApi.getAllMedia(0,300);
        shortMedias = medias.subList(0,5);
    }

    public void playMedia(long mediaId) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            long widgetId = mediaApi.findWidgetByPlaylist();
            if (widgetId > 0) {
                if (mediaApi.deleteWidget(widgetId) == 200) {
                    if (mediaApi.editWidget(mediaId) == 200) {
                        layoutChangerUtil.changeLayoutForAll(39);
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Succesfully set media to playlist"));
                    }
                    else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Error while playing medi"));
                    }
                }
                else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error while removing media from playlist"));
                }
            }
        } catch (NoConnectionException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Could not establish connection"));
        }
    }

    //region Getter & Setter
    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public List<Media> getShortMedias() {
        return shortMedias;
    }

    public void setShortMedias(List<Media> shortMedias) {
        this.shortMedias = shortMedias;
    }
    //endregion
}
