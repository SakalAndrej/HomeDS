package at.htl.xiboClient;

import at.htl.enums.RequestTypeEnum;
import at.htl.exceptions.NoConnectionException;
import at.htl.utils.AuthentificationHandler;
import at.htl.utils.RequestHelper;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

@Stateless
public class CrawlerApi {

    // --Commented out by Inspection (08.03.18, 23:43):private static RequestHelper requestHelper = new RequestHelper();

    public String getLayoutsWithAllSubEntities(long layoutId, String layoutName) throws NoConnectionException {
        String resp = "";
        String query = "";

        if (layoutId != -1)
            query += "&layoutId="+layoutId;
        if (layoutName != null && !layoutName.isEmpty())
            query += "&layout=" + layoutName;

        try {
            //Get all Datasets
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.GET, null,
                            new RequestHelper().BASE_URL + "api/layout?embed=regions,playlists,widgets,widgetOptions"+query,
                            AuthentificationHandler.getTOKEN());

            if (con.getResponseCode() == 401) {
                AuthentificationHandler.Authenticate();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String output;
            StringBuilder response = new StringBuilder();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            resp = response.toString();
        } catch (IOException e) {
            throw new NoConnectionException("Keine Verbindung zum Server möglich!");
        }
        return resp;
    }
}
