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

@ApplicationScoped
@Named
public class CrawlController implements Serializable {

    @Inject
    private
    CrawlerApi crawler;

    private String crawl;

    private String actLayoutName;

    private long actLayoutId;

    private String formatterUrl;

    private String collapsed;

    private CrawlController() { }

    @PostConstruct
    public void init() {
        actLayoutId = -1;
        this.startCrawl();
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
        try {
            crawl = crawler.getLayoutsWithAllSubEntities(actLayoutId, name);
        } catch (NoConnectionException e) {
            // throw growl
        }

        if (!crawl.isEmpty() && crawl.length() > 2) {
            crawl = new JSONArray(crawl).toString();
            collapsed = "false";
        } else
            collapsed = "true";
    }

    public void startedCrawlGrowl() {
        FacesContext context = FacesContext.getCurrentInstance();
        startCrawl();

        if (!crawl.isEmpty() && crawl.length() > 2) {
            context.addMessage(null, new FacesMessage("Successful"));
        } else {
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

    public CrawlerApi getCrawler() {
        return crawler;
    }

    public void setCrawler(CrawlerApi crawler) {
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
