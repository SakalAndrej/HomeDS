package at.htl.utils;

import at.htl.enums.RequestTypeEnum;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

// Singleton Pattern
public class RequestHelper {

    public String BASE_URL = "http://localhost:9090/";

    public HttpURLConnection executeRequest(RequestTypeEnum executeType, String paramsBody, String url, String TOKEN) {

        URL obj = null;
        HttpURLConnection con;

        try {
            // Building Connection
            obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Authorization", "Bearer " + TOKEN);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            switch (executeType) {
                case GET:
                    con.setRequestMethod("GET");
                    break;

                case PUT:
                    con.setRequestMethod("PUT");
                    con.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(paramsBody);
                    wr.flush();
                    wr.close();
                    break;

                case POST:
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    DataOutputStream write = new DataOutputStream(con.getOutputStream());
                    write.writeBytes(paramsBody);
                    write.flush();
                    write.close();
                    break;

                case DELETE:
                    con.setRequestMethod("DELETE");
                    break;
            }

            // Output for testing purposes
            System.out.println("nSending " + executeType.name() + " request to URL : " + url);
            if (paramsBody!=null && (executeType==RequestTypeEnum.POST || RequestTypeEnum.PUT == executeType))
                System.out.println("Send-Body : " + paramsBody);
            System.out.println("Response Code : " + con.getResponseCode());

            return con;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

