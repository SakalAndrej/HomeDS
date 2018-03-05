package at.htl.xiboClient;

import at.htl.enums.RequestTypeEnum;
import at.htl.exceptions.NoConnectionException;
import at.htl.model.Display;
import at.htl.utils.AuthentificationHandler;
import at.htl.utils.RequestHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

@Stateless
public class DisplayApi {

    public LinkedList<Display> getAllDisplays() throws NoConnectionException {
        BufferedReader in;
        LinkedList<Display> displays = new LinkedList<>();
        Display act = new Display();
        try {
            //Get all Datasets
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.GET,
                            null,
                            new RequestHelper().BASE_URL + "api/display",
                            AuthentificationHandler.getTOKEN());

            try {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } catch (NullPointerException ex) {
                throw new NoConnectionException("Es ist kein Response vorhanden", ex);
            }

            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            JSONArray jsonarray = new JSONArray(response.toString());

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonObject = jsonarray.getJSONObject(i);

                // Parse DataSet
                act.setDisplay(jsonObject.getString("display"));
                act.setDisplayId(jsonObject.getLong("displayId"));
                act.setDisplayGroupId(jsonObject.getLong("displayGroupId"));

                //because of
                //if (jsonObject.getString("description").)
                //act.setDescription();
                //act.setDefaultLayoutId(jsonObject.getLong("defaultLayoutId"));
                //act.setCurrentLayoutId(jsonObject.getLong("currentLayoutId"));
                //act.setDeviceName(jsonObject.getString("deviceName"));

                displays.add(act);
                act = new Display();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return displays;
    }

    public long scheduleLayout(long campaingLayoutId, LocalDateTime fromDate, LocalDateTime toDate) throws NoConnectionException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        BufferedReader in;
        try {
            //Get all Datasets
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.POST,
                            "eventTypeId=1&campaignId=" + campaingLayoutId +
                                    "&displayOrder=0&isPriority=11&displayGroupIds[]="+14+"&fromDt="+fromDate.format(formatter)+"&toDt="+toDate.format(formatter)+"&syncTimezone=1",
                            new RequestHelper().BASE_URL + "api/schedule",
                            AuthentificationHandler.getTOKEN());

            if (con.getResponseCode()==201) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String output;
                StringBuffer response = new StringBuffer();
                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                JSONObject jsonObject = new JSONObject(response.toString());
                return jsonObject.getLong("campaignId");
            }
            else {
                return -1;
            }
        } catch (NullPointerException ex) {
            throw new NoConnectionException("Es ist kein Response vorhanden", ex);
        } catch (IOException e) {
            throw new NoConnectionException("IO Exception", e);
        }
    }

    public boolean deleteEvent(long campaignId) throws NoConnectionException {
        try {
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.DELETE, null,
                            new RequestHelper().BASE_URL + "api/schedule/" + campaignId,
                            AuthentificationHandler.getTOKEN());
            if (con.getResponseCode()==204) {
                return true;
            }
            else {
                return false;
            }
        } catch (NullPointerException ex) {
            throw new NoConnectionException("Es ist kein Response vorhanden", ex);
        } catch (IOException e) {
            throw new NoConnectionException("IO Exception", e);
        }
    }
}
