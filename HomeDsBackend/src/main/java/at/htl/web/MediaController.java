package at.htl.web;

import at.htl.exceptions.NoConnectionException;
import at.htl.model.Media;
import at.htl.xiboClient.MediaApi;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Model
@Named
public class MediaController  implements Serializable{


    @Inject
    MediaApi mediaApi;


    private List<Media> medias;

    @PostConstruct
    public void init(){
        try {
            this.updaeList();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }

        try {
            mediaApi.eidtWidget("16");
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }

    }



    private void updaeList() throws NoConnectionException {
        this.medias = mediaApi.getAllMedia();
    }


    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }
}
