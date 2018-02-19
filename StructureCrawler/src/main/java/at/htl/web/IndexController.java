package at.htl.web;

import at.htl.xiboClient.Crawler;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import java.io.Serializable;

@ApplicationScoped
@Named
public class IndexController implements Serializable {

    @Inject
    Crawler crawler;

    private String crawl;

    private String actLayoutName;

    private long actLayoutId;

    private String formatterUrl;

    private String collapsed;

    private IndexController() {
    }

    @PostConstruct
    public void init() {
        this.startCrawl();
        actLayoutId = -1;
    }

    public void startCrawl() {
        String name;
        long id;
        String query = "";
        formatterUrl = "";

        query += "?layoutId=" + actLayoutId;

        if (actLayoutName != null && !actLayoutName.isEmpty()) {
            name = actLayoutName;
            query += "&layout=" + name;
        } else
            name = "";

        formatterUrl = "\'http://localhost:8080/homeds/rs/crawler" + query + "\'";
        crawl = crawler.getLayoutsWithAllSubEntities(actLayoutId, name);

        if (!crawl.isEmpty() && crawl.length()>2) {
            crawl = new JSONArray(crawl).toString();
            collapsed = "false";
        }
        else
            collapsed = "true";
    }

    public void startedCrawlGrowl() {
        FacesContext context = FacesContext.getCurrentInstance();
        startCrawl();

        if (!crawl.isEmpty() && crawl.length()>2) {
            context.addMessage(null, new FacesMessage("Successful"));
        }
        else {
            context.addMessage(null, new FacesMessage("No Content"));
        }
    }

    //region Getter & Setter
    public String getCrawl() {
        return crawl;
    }

    public void setCrawl(String crawl) {
        this.crawl = crawl;
    }

    public Crawler getCrawler() {
        return crawler;
    }

    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }

    public String getActLayoutName() {
        return actLayoutName;
    }

    public void setActLayoutName(String actLayoutName) {
        this.actLayoutName = actLayoutName;
    }

    public long getActLayoutId() {
        return actLayoutId;
    }

    public void setActLayoutId(long actLayoutId) {
        this.actLayoutId = actLayoutId;
    }

    public String getFormatterUrl() {
        return formatterUrl;
    }

    public void setFormatterUrl(String formatterUrl) {
        this.formatterUrl = formatterUrl;
    }

    public String getCollapsed() {
        return collapsed;
    }

    public void setCollapsed(String collapsed) {
        this.collapsed = collapsed;
    }

    //endregion
}
