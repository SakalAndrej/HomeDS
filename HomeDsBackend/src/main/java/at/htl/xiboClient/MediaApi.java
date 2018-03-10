package at.htl.xiboClient;

import at.htl.enums.RequestTypeEnum;
import at.htl.exceptions.NoConnectionException;
import at.htl.model.Media;
import at.htl.utils.AuthentificationHandler;
import at.htl.utils.RequestHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.Stateless;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedList;

@Stateless
public class MediaApi {

    private final String editPlaylistId = "157";

    public LinkedList<Media> getAllMedia(int start, int length, String tags) throws NoConnectionException {

        BufferedReader in;
        LinkedList<Media> medias = new LinkedList<>();
        Media actual = new Media();

        try {
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.GET, null,
                            new RequestHelper().BASE_URL + "api/library?start=" + start + "&length=" + length + "&tags=" + tags,
                            AuthentificationHandler.getTOKEN());
            try {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } catch (NullPointerException ex) {
                throw new NoConnectionException("Es ist kein Response vorhanden", ex);
            }

            String output;
            StringBuilder response = new StringBuilder();
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

    public void uploadMedia() throws NoConnectionException {


        /*File inFile = new File("/Users/andrejsakal/Dropbox/Projects/HomeDS/Documents/HomeDsLogoWhite.png");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(inFile);
            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

            // server back-end URL
            HttpPost httppost = new HttpPost(new RequestHelper().BASE_URL + "api/library");
            httppost.addHeader("Authorization", "Bearer " + AuthentificationHandler.getTOKEN());
            MultipartEntity entity = new MultipartEntity();
            // set the file input stream and file name as arguments
            entity.addPart("files", new InputStreamBody(fis, inFile.getName()));
            httppost.setEntity(entity);
            // execute the request
            HttpResponse response = httpclient.execute(httppost);

            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");

            System.out.println("[" + statusCode + "] " + responseString);

        } catch (ClientProtocolException e) {
            System.err.println("Unable to make connection");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Unable to read file");
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
            }
        }*/

        BufferedReader in;

        File uploadFile = new File("/Users/andrejsakal/Dropbox/Projects/HomeDS/Documents/HomeDsLogoWhite.png");

        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(uploadFile);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            FileBody fileBody = new FileBody(uploadFile); //image should be a String
            builder.addPart(uploadFile.getName(), fileBody);
            HttpEntity entity = builder.build();

            URL obj;
            HttpURLConnection con;

            // Building Connection
            obj = new URL(new RequestHelper().BASE_URL + "api/library");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Authorization", "Bearer " + AuthentificationHandler.getTOKEN());
            con.setRequestProperty("Content-Type", "application/octet-stream;charset=UTF-8");
            con.setRequestProperty("Content-Transfer-Encoding", "binary");
            con.setRequestMethod("POST");

            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);

            OutputStream os = con.getOutputStream();
            os.write(Byte.valueOf("files="));
            entity.writeTo(con.getOutputStream());

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println(readStream(con.getInputStream()));
            }

            // Output for testing purposes
            System.out.println("Response Code : " + con.getResponseCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
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
            StringBuilder response = new StringBuilder();
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
            BufferedReader in;

            try {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } catch (NullPointerException ex) {
                throw new NoConnectionException("Es ist kein Response vorhanden", ex);
            }

            String output;
            StringBuilder response = new StringBuilder();
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
                            new RequestHelper().BASE_URL + "api/playlist/widget?playlistId=" + editPlaylistId,
                            AuthentificationHandler.getTOKEN());
            BufferedReader in;

            try {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } catch (NullPointerException ex) {
                throw new NoConnectionException("Es ist kein Response vorhanden", ex);
            }

            String output;
            StringBuilder response = new StringBuilder();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            if (con.getResponseCode() == 200) {
                JSONArray array = new JSONArray(response.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    if ((mediaId = object.getLong("widgetId")) != 0) {
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
