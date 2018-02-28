package at.htl.xiboClient;

import at.htl.enums.RequestTypeEnum;
import at.htl.exceptions.NoConnectionException;
import at.htl.model.Media;
import at.htl.xiboClient.helper.AuthentificationHandler;
import at.htl.xiboClient.helper.RequestHelper;
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
public class MediaApi{
    public final String editWidgetId = "427";
    public LinkedList<Media> getAllMedia() throws NoConnectionException{


        BufferedReader in;
        LinkedList<Media> medias = new LinkedList<Media>();
        Media actual = new Media();
        HashMap<String,String> params = new HashMap<>();
        params.put("type","image");
        try {
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.GET, null,
                            new RequestHelper().BASE_URL + "api/library?start=0&length=100",
                            AuthentificationHandler.getTOKEN());

            try{
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            }catch (NullPointerException ex){
                throw new NoConnectionException("Es ist kein Response vorhanden", ex);
            }

            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null){
                response.append(output);
            }

            JSONArray jsonArray = new JSONArray(response.toString());
            System.out.println(response.toString());


            for (int i  = 0;i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                actual.setMediaId(jsonObject.getLong("mediaId"));
                actual.setMediaType(jsonObject.getString("mediaType"));
                actual.setName(jsonObject.getString("name"));
                actual.setOwnerId(jsonObject.getLong("ownerId"));
                medias.add(actual);
                actual = new Media();
            }


        }catch(IOException e){
            e.printStackTrace();
        }


        return medias;

    }


    public void eidtWidget(String mediaId) throws NoConnectionException {

        BufferedReader in;





        try {
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.GET, "mediaIds=["+mediaId+"]",
                            new RequestHelper().BASE_URL + "api/playlist/widget/"+editWidgetId,
                            AuthentificationHandler.getTOKEN());

            try{
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            }catch (NullPointerException ex){
                throw new NoConnectionException("Es ist kein Response vorhanden", ex);
            }

            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null){
                response.append(output);
            }

            JSONArray jsonArray = new JSONArray(response.toString());
            System.out.println(response.toString());



        }catch(IOException e){
            e.printStackTrace();
        }


    }

}
