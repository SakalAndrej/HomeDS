package at.htl.web;

import at.htl.enums.XiboEnum;
import at.htl.exceptions.NoConnectionException;
import at.htl.facades.CampaignFacade;
import at.htl.model.Campaign;
import at.htl.model.Display;
import at.htl.model.Media;
import at.htl.utils.LayoutChangerUtil;
import at.htl.xiboClient.DisplayApi;
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

@SuppressWarnings("StatementWithEmptyBody")
@Model
@Named
public class MediaController implements Serializable {

    @Inject
    private
    MediaApi mediaApi;

    @Inject
    private
    DisplayApi displayApi;

    @Inject
    private
    LayoutChangerUtil layoutChangerUtil;

    @Inject
    private
    CampaignFacade campaignFacade;

    private Display actDisplay;

    private List<Display> displays;

    private TagCloudModel model;

    private String tags;

    private static List<Media> medias;

    private static List<Media> shortMedias;

    private static LocalDateTime lastOnline;

    @PostConstruct
    public void init() {
        tags = "";
        try {
            displays = displayApi.getAllDisplays();
            if (medias != null && lastOnline.isAfter(LocalDateTime.now())) {
                //no need for update
            } else {
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

    public void updateList(String cloudTags) throws NoConnectionException {
        medias = mediaApi.getAllMedia(0, 50, cloudTags);
        if (medias.size() > 10)
            shortMedias = medias.subList(0, 5);
        else {
            shortMedias = medias;
        }
    }

    public void clearCloud() {
        tags = "";
        try {
            this.updateList(tags);
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }

    public void playMedia(long mediaId) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            long widgetId = mediaApi.findWidgetByPlaylist();
            if (widgetId > 0) {
                if (mediaApi.deleteWidget(widgetId) == 200) {
                    if (mediaApi.editWidget(mediaId) == 200) {
                        layoutChangerUtil.changeLayout(44,LocalDateTime.now().plusYears(2), XiboEnum.MEDIA,actDisplay.getDisplayGroupId());
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Succesfully played media"));
                    } else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Error while playing media"));
                    }
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error while aborting media"));
                }
            }
        } catch (NoConnectionException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Could not establish connection"));
        }
    }

    public void onSelect(SelectEvent event) {
        TagCloudItem item = (TagCloudItem) event.getObject();
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (!tags.equals(item.getLabel())) {
                tags = item.getLabel();
                this.updateList(tags);
            }
        } catch (NoConnectionException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Could not establish connection"));

        }
    }

    public void clearMedia() {
        FacesContext context = FacesContext.getCurrentInstance();
        List<Campaign> campaigns = campaignFacade.getAllMedia();
        if (campaigns != null && campaigns.size() > 0) {
            for (Campaign campaign : campaigns) {
                try {
                    if (displayApi.deleteEvent(campaign.getCampaignId())) {
                        campaignFacade.delete(campaign.getId());
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Succesfully stopped media!"));
                    }
                } catch (NoConnectionException e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Could not establish connection"));
                }
            }
        }
        else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Nothing to stop!"));
        }
    }

    //region Getter & Setter
    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        MediaController.medias = medias;
    }

    public List<Media> getShortMedias() {
        return shortMedias;
    }

    public void setShortMedias(List<Media> shortMedias) {
        MediaController.shortMedias = shortMedias;
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

    public Display getActDisplay() {
        return actDisplay;
    }

    public void setActDisplay(Display actDisplay) {
        this.actDisplay = actDisplay;
    }

    public List<Display> getDisplays() {
        return displays;
    }

    public void setDisplays(List<Display> displays) {
        this.displays = displays;
    }

    //endregion
}
