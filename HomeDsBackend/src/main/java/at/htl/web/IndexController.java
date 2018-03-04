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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Timer;

@Model
@Named
public class IndexController {

    @Inject
    StatusApi statusApi;

    private static boolean on;

    private static int cnt = 0;

    private static LocalDateTime lastOnline;

    public boolean isServerOnline() {
        if (cnt == 0 || (lastOnline.minusMinutes(1).isAfter(LocalDateTime.now()))) {
            try {
                cnt++;
                on = true;
                lastOnline = LocalDateTime.now();
                return statusApi.getIsOnline();
            } catch (NoConnectionException e) {
                cnt++;
                on=false;
                lastOnline = LocalDateTime.now();
                return false;
            }
        }
        else {
            return on;
        }
    }
}
