package at.htl.xiboClient;

import at.htl.enums.RequestTypeEnum;
import at.htl.model.DataSet;
import at.htl.xiboClient.helper.AuthentificationHandler;
import at.htl.xiboClient.helper.RequestHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.ws.rs.QueryParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.LinkedList;

//Single Pattern
@Stateless
public class DataSetApi {

    public LinkedList<DataSet> getAllDataSet(int dataSetId, String dataSet, String dataSetCode) {

        LinkedList<DataSet> dataSets = new LinkedList<>();
        DataSet act = new DataSet();

        try {
            //Get all Datasets
            HttpURLConnection con = RequestHelper.get_instance()
                    .executeRequest(RequestTypeEnum.GET,
                            null,
                            RequestHelper.get_instance().BASE_URL + "api/dataset",
                            AuthentificationHandler.getTOKEN());

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            JSONArray jsonarray = new JSONArray(response);

            /* Ansatz for dynamic for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                Iterator keys = jsonobject.keys();

                while(keys.hasNext()) {

                    String col = (String)keys.next();
                    if (col.equals("id")) {
                        act.setDataSetId(jsonobject.getInt(col));
                    }
                    else if (col.){
                        dataSets.add(new Date)
                        String val = jsonobject.getString(col);
                    }


                }
            }*/

            in.close();
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
