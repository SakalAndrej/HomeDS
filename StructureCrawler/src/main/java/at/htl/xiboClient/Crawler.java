package at.htl.xiboClient;

import at.htl.enums.RequestTypeEnum;
import at.htl.helper.AuthentificationHandler;
import at.htl.helper.RequestHelper;
import at.htl.model.DataSet;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

@Stateless
public class Crawler {

    private static RequestHelper requestHelper = new RequestHelper();

    public String getLayoutsWithAllSubEntities(long layoutId, String layoutName) {
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
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            resp = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }
}
