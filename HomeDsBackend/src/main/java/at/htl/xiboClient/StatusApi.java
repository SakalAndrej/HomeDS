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

    public boolean getIsOnline() throws NoConnectionException {
        BufferedReader in;
        LinkedList<DataSet> dataSets = new LinkedList<>();
        DataSet act = new DataSet();

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
                throw new NoConnectionException("Es ist kein Response vorhanden", ex);
            }

            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            if (con.getResponseCode() == 200 && response.toString().isEmpty() == false) {
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
