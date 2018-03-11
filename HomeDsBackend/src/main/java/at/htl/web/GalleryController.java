package at.htl.web;

import at.htl.exceptions.NoConnectionException;
import at.htl.xiboClient.CrawlerApi;
import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
@Named
public class GalleryController implements Serializable {

    private List<String> images;

    @PostConstruct
    public void init() throws IOException {
        images = new LinkedList<>();
        InputStream input = new URL("http://vm59.htl-leonding.ac.at:8080/homeds/img/").openStream();
        Document document = new Tidy().parseDOM(input, null);
        NodeList imgs = document.getElementsByTagName("a");
        List<String> srcs = new ArrayList<String>();

        for (int i = 0; i < imgs.getLength(); i++) {
            String src = "";
            if (!(src = imgs.item(i).getAttributes().getNamedItem("href").getNodeValue()).equals("/"))
                images.add(src);
        }

        for (String src: srcs) {
            System.out.println(src);
        }
    }

    public List<String> getImages() {
        return images;
    }

}
