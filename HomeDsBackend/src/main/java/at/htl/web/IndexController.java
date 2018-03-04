package at.htl.web;

import at.htl.exceptions.NoConnectionException;
import at.htl.xiboClient.StatusApi;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;

@Model
@Named
public class IndexController {

    @Inject
    StatusApi statusApi;

    private static boolean on;

    private static int cnt = 0;

    private static LocalDateTime lastOnline;

    public boolean isServerOnline() {
        if (cnt == 0 || (lastOnline.plusMinutes(2).isBefore(LocalDateTime.now()))) {
            try {
                cnt++;
                on = true;
                lastOnline = LocalDateTime.now();
                return statusApi.getIsOnline();
            } catch (NoConnectionException e) {
                cnt++;
                on = false;
                lastOnline = LocalDateTime.now();
                return false;
            }
        } else {
            return on;
        }
    }
}
