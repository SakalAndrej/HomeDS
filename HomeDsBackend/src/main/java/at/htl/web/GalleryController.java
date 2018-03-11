package at.htl.web;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Model
@Named
public class GalleryController implements Serializable {

    private List<String> images;

    @PostConstruct
    public void init(){
        updateImages();
    }

    private void updateImages() {
        images = new LinkedList<>();
        InputStream input = null;
        try {
            input = new URL("http://vm59.htl-leonding.ac.at:8080/homeds/img/").openStream();

            Document document = new Tidy().parseDOM(input, null);
            NodeList imgs = document.getElementsByTagName("a");
            List<String> srcs = new ArrayList<String>();

            for (int i = 0; i < imgs.getLength(); i++) {
                String src = "";
                if (!(src = imgs.item(i).getAttributes().getNamedItem("href").getNodeValue()).equals("/homeds")) {
                    src = src.replace("/homeds", "");
                    if (src.toLowerCase().contains(".png")
                            || src.toLowerCase().contains(".jpg")
                            || src.toLowerCase().contains(".jpeg")
                            || src.toLowerCase().contains(".ico")
                            || src.toLowerCase().contains(".gif")
                            || src.toLowerCase().contains(".bmp")) {
                        images.add(src);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
