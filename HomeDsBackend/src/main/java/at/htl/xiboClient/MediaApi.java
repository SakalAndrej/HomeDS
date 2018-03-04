package at.htl.xiboClient;

import at.htl.enums.RequestTypeEnum;
import at.htl.exceptions.NoConnectionException;
import at.htl.model.Media;
import at.htl.utils.AuthentificationHandler;
import at.htl.utils.RequestHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedList;

@Stateless
public class MediaApi {

    public final String editPlaylistId = "157";

    public LinkedList<Media> getAllMedia(int start, int length) throws NoConnectionException {

        BufferedReader in;
        LinkedList<Media> medias = new LinkedList<Media>();
        Media actual = new Media();

        HashMap<String, String> params = new HashMap<>();
        params.put("type", "image");

        try {
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.GET, "tags=project",
                            new RequestHelper().BASE_URL + "api/library?start=" + start +"&length="+length,
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

            JSONArray jsonArray = new JSONArray(response.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                actual.setMediaId(jsonObject.getLong("mediaId"));
                actual.setMediaType(jsonObject.getString("mediaType"));
                actual.setName(jsonObject.getString("name"));
                actual.setOwnerId(jsonObject.getLong("ownerId"));
                medias.add(actual);
                actual = new Media();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medias;

    }

    public int editWidget(long mediaId) throws NoConnectionException {

        BufferedReader in;

        try {
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.POST, "media[]=" + mediaId,
                            new RequestHelper().BASE_URL + "api/playlist/library/assign/" + editPlaylistId,
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
            return con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 400;
    }

    public int deleteWidget(long widgetId) throws NoConnectionException {
        try {
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.DELETE, null,
                            new RequestHelper().BASE_URL + "api/playlist/widget/" + widgetId,
                            AuthentificationHandler.getTOKEN());
            BufferedReader in = null;

            try {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            catch (NullPointerException ex) {
                throw new NoConnectionException("Es ist kein Response vorhanden", ex);
            }

            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            System.out.println(response.toString());
            return con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public long findWidgetByPlaylist() throws NoConnectionException {
        long mediaId = -1;
        try {
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.GET, null,
                            new RequestHelper().BASE_URL + "api/playlist/widget?playlistId="+editPlaylistId,
                            AuthentificationHandler.getTOKEN());
            BufferedReader in = null;

            try {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            catch (NullPointerException ex) {
                throw new NoConnectionException("Es ist kein Response vorhanden", ex);
            }

            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            if (con.getResponseCode()==200) {
                JSONArray array = new JSONArray(response.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    if ((mediaId = (long) object.getLong("widgetId")) != 0) {
                        return mediaId;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
