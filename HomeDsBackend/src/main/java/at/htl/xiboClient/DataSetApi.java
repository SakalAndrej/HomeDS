package at.htl.xiboClient;

import at.htl.enums.RequestTypeEnum;
import at.htl.model.DataSet;
import at.htl.model.DataSetData;
import at.htl.model.DataSetDataField;
import at.htl.xiboClient.helper.AuthentificationHandler;
import at.htl.xiboClient.helper.RequestHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.Stateless;
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
                            RequestHelper.get_instance().BASE_URL + "api/dataset/data/" + dataSetId,
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

            for (int i = 0; i < jsonarray.length(); i++) {
                LinkedList<DataSetDataField> rows = new LinkedList<>();
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                Iterator keys = jsonobject.keys();

                while (keys.hasNext()) {
                    String col = (String) keys.next();
                    if (col.equals("id")) {
                        act.setDataId(jsonobject.getLong(col));
                    } else {
                        DataSetDataField row = new DataSetDataField();
                        row.setColName(col);
                        long columnId = -1;
                        if ((columnId = getIdForColumnName(dataSetId,row.getColName()))!=-1)
                            row.setDataSetColumnId(columnId);

                        // Check what type and if not null
                        if (!jsonobject.isNull(col)) {
                            Object value = jsonobject.get(col);
                            if (value instanceof String)
                                row.setValue(String.valueOf(value));
                        }
                        row.setExpireDate(null);
                        rows.add(row);
                        row = new DataSetDataField();
                    }
                }
                act.setFields(rows);
                dataSetDatas.add(act);
                act = new DataSetData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSetDatas;
    }

    public long getIdForColumnName(long dataSetId, String columnName) {

        try {
            //Get all columns from this datasetid
            HttpURLConnection con = RequestHelper.get_instance()
                    .executeRequest(RequestTypeEnum.GET,
                            null,
                            RequestHelper.get_instance().BASE_URL + "api/dataset/" + dataSetId + "/column",
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

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonObject = jsonarray.getJSONObject(i);
                if (jsonObject.getString("heading").equals(columnName)) {
                    return jsonObject.getLong("dataSetColumnId");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public long editDataSetField(long dataSetId, long dataSetDataId, long dataSetColumnId, String dataSetFieldValue) {

        try {

            //Get all Datasets
            HttpURLConnection con = RequestHelper.get_instance()
                    .executeRequest(RequestTypeEnum.PUT,
                            "dataSetColumnId_"+dataSetColumnId+"="+dataSetFieldValue,
                            RequestHelper.get_instance().BASE_URL + "api/dataset/data/" + dataSetId+"/"+dataSetDataId,
                            AuthentificationHandler.getTOKEN());

            BufferedReader in = null;

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            System.out.println(response.toString());
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
