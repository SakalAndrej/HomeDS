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
    private
    StatusApi statusApi;

    private static boolean on;

    private static int cnt = 0;

    private static LocalDateTime lastOnline;

    public boolean isServerOnline() {
        if (cnt == 0 || (lastOnline.plusSeconds(30).isBefore(LocalDateTime.now()))) {
            try {
                cnt++;
                lastOnline = LocalDateTime.now();
                on = statusApi.getIsOnline();
                return on;
            } catch (NoConnectionException e) {
                cnt++;
                lastOnline = LocalDateTime.now();
                on = false;
                return on;
            }
        } else {
            return on;
        }
    }
}
