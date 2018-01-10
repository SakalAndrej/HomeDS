package at.htl.xiboClient;

import at.htl.enums.RequestTypeEnum;
import at.htl.model.DataSet;
import at.htl.model.DataSetData;
import at.htl.model.DataSetDataRow;
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

    public LinkedList<DataSet> getAllDataSet(long dataSetId, String dataSet, String dataSetCode) {

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

            JSONArray jsonarray = new JSONArray(response.toString());
            System.out.println(response.toString());

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonObject = jsonarray.getJSONObject(i);

                // Parse DataSet
                act.setDataSetId(jsonObject.getLong("dataSetId"));
                act.setDataSetName(jsonObject.getString("dataSet"));

                if (jsonObject.isNull("description")) {
                    System.out.println("No Description contained");
                } else {
                    act.setDescription(jsonObject.getString("description"));
                }
                dataSets.add(act);
                act = new DataSet();
            }

            //in.close();
            //con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSets;
    }

    public LinkedList<DataSetData> getAllDataSetData(long dataSetId) {

        LinkedList<DataSetData> dataSetDatas = new LinkedList<>();

        DataSetData act = new DataSetData();

        try {

            //Get all Datasets
            HttpURLConnection con = RequestHelper.get_instance()
                    .executeRequest(RequestTypeEnum.GET,
                            null,
                            RequestHelper.get_instance().BASE_URL + "api/dataset/data/"+dataSetId,
                            AuthentificationHandler.getTOKEN());

            BufferedReader in = null;

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            JSONArray jsonarray = new JSONArray(response.toString());
            System.out.println(response.toString());

            for(int i = 0; i < jsonarray.length(); i++) {
                LinkedList<DataSetDataRow> rows = new LinkedList<>();
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                Iterator keys = jsonobject.keys();

                while(keys.hasNext()) {
                    String col = (String)keys.next();
                    if (col.equals("id")) {
                        act.setDataId(jsonobject.getLong(col));
                    }
                    else {
                        DataSetDataRow row = new DataSetDataRow();
                        row.setColName(col);

                        // Check what type and if not null
                        if (!jsonobject.isNull(col)) {
                            Object value = jsonobject.get(col);
                            if (value instanceof String)
                                row.setValue(String.valueOf(value));
                        }
                        row.setExpireDate(null);
                        rows.add(row);
                        row = new DataSetDataRow();
                    }
                }
                act.setRows(rows);
                dataSetDatas.add(act);
                act = new DataSetData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSetDatas;
    }
}
