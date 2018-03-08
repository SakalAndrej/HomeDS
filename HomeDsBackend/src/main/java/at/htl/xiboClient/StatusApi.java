package at.htl.xiboClient;

import at.htl.enums.RequestTypeEnum;
import at.htl.exceptions.NoConnectionException;
import at.htl.model.DataSet;
import at.htl.utils.AuthentificationHandler;
import at.htl.utils.RequestHelper;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.LinkedList;

@Stateless
public class StatusApi {

    public boolean getIsOnline() {
        BufferedReader in;

        try {
            //Get all Datasets
            HttpURLConnection con = new RequestHelper()
                    .executeRequest(RequestTypeEnum.GET,
                            null,
                            new RequestHelper().BASE_URL + "api/clock",
                            AuthentificationHandler.getTOKEN());

            try {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } catch (NullPointerException ex) {
                return false;
            }

            String output;
            StringBuilder response = new StringBuilder();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            if (con.getResponseCode() == 200 && !response.toString().isEmpty()) {
                in.close();
                con.disconnect();
                return true;
            }
            else {
                in.close();
                con.disconnect();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
