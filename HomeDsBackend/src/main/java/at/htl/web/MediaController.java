package at.htl.web;

import at.htl.exceptions.NoConnectionException;
import at.htl.model.Media;
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

    private List<Media> medias;

    @PostConstruct
    public void init() {
        try {
            this.updateList();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }

    private void updateList() throws NoConnectionException {
        this.medias = mediaApi.getAllMedia();
    }

    public void playMedia(long mediaId) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            long widgetId = mediaApi.findWidgetByPlaylist();
            if (widgetId > 0) {
                if (mediaApi.deleteWidget(widgetId) == 200) {
                    if (mediaApi.editWidget(mediaId) == 200) {
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
    //endregion
}
