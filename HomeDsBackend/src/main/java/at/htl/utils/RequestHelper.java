package at.htl.utils;

import at.htl.enums.RequestTypeEnum;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestHelper {

    private RequestHelper() { }

    private static RequestHelper _instance;

    public static RequestHelper get_instance() {
        if (_instance==null)
            _instance = new RequestHelper();
        return _instance;
    }

    public HttpURLConnection executeRequest(RequestTypeEnum executeType, String paramsBody, String url, String TOKEN) {

        URL obj = null;
        try {
            obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

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
                    con.setRequestProperty("Authorization","Bearer "+TOKEN);
                    break;
                case POST:
                    con.setDoOutput(true);
                    DataOutputStream write = new DataOutputStream(con.getOutputStream());
                    write.writeBytes(paramsBody);
                    write.flush();
                    write.close();
                    con.setRequestMethod("POST");
                    break;
                case DELETE:
                    con.setRequestMethod("DELETE");
                    break;
            }

        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + paramsBody);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //printing result from response
        System.out.println(response.toString());

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

