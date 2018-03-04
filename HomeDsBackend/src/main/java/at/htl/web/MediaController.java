package at.htl.web;

import at.htl.exceptions.NoConnectionException;
import at.htl.model.Media;
import at.htl.utils.LayoutChangerUtil;
import at.htl.xiboClient.MediaApi;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Model
@Named
public class MediaController implements Serializable {

    @Inject
    MediaApi mediaApi;

    @Inject
    LayoutChangerUtil layoutChangerUtil;

    private TagCloudModel model;

    private String tags;

    private static List<Media> medias;

    private static List<Media> shortMedias;

    private static LocalDateTime lastOnline;

    @PostConstruct
    public void init() {
        tags = "";
        try {
            if (medias != null && lastOnline.isAfter(LocalDateTime.now())) {
                //no need for update
            }
            else {
                this.updateList(tags);
                lastOnline = LocalDateTime.now().plusMinutes(5);
            }
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        model = new DefaultTagCloudModel();
        model.addTag(new DefaultTagCloudItem("Informatik", 5));
        model.addTag(new DefaultTagCloudItem("Medientechnik", 1));
        model.addTag(new DefaultTagCloudItem("Biomedizin", 1));
        model.addTag(new DefaultTagCloudItem("Elektronik", 1));
        model.addTag(new DefaultTagCloudItem("Projekte", 4));
        model.addTag(new DefaultTagCloudItem("Projektvideos", "#", 2));
    }

    private void updateList(String cloudTags) throws NoConnectionException {
        this.medias = mediaApi.getAllMedia(0,50, cloudTags);
        if (medias.size()>10)
            shortMedias = medias.subList(0,5);
        else {
            shortMedias = medias;
        }
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

    public void onSelect(SelectEvent event) {
        TagCloudItem item = (TagCloudItem) event.getObject();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", item.getLabel());

        //bec of starting not with ,
        if (tags.isEmpty())
            tags = item.getLabel();
        else
            tags += ","+item.getLabel();

        FacesContext.getCurrentInstance().addMessage(null, msg);
        try {
            mediaApi.getAllMedia(0,100, tags);
        } catch (NoConnectionException e) {
            e.printStackTrace();
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

    public TagCloudModel getModel() {
        return model;
    }

    public void setModel(TagCloudModel model) {
        this.model = model;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    //endregion
}
