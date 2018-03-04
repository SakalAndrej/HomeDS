package at.htl.web;

import at.htl.exceptions.NoConnectionException;
import at.htl.model.Display;
import at.htl.xiboClient.DisplayApi;
import at.htl.xiboClient.StatusApi;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Timer;

@Model
@Named
public class IndexController {

    @Inject
    StatusApi statusApi;

    public DateTime

    public boolean isServerOnline() {

        try {
            return statusApi.getIsOnline();
        } catch (NoConnectionException e) {
            return false;
        }
    }
}
