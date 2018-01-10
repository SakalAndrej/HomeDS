package at.htl.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DataSetData {

    private long dataId;
    private LinkedList<DataSetDataRow> rows;

    public DataSetData(long dataId, LinkedList<DataSetDataRow> rows) {
        this.dataId = dataId;
        this.rows = rows;
    }

    //region Getter & Setter

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    public LinkedList<DataSetDataRow> getRows() {
        return rows;
    }

    public void setRows(LinkedList<DataSetDataRow> rows) {
        this.rows = rows;
    }


    //endregion
}
