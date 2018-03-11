package at.htl.web;

import at.htl.exceptions.NoConnectionException;
import at.htl.xiboClient.CrawlerApi;
import org.json.JSONArray;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named
public class GalleryController implements Serializable {

    private List<String> images;

    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        images.add("HomesLogoTrans.png");
    }

    public List<String> getImages() {
        return images;
    }

}
