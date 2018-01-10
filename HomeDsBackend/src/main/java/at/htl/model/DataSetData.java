package at.htl.model;

import java.util.LinkedList;

public class DataSetData {

    private long dataId;
    private LinkedList<DataSetDataField> fields;

    public DataSetData(long dataId, LinkedList<DataSetDataField> fields) {
        this.dataId = dataId;
        this.fields = fields;
    }

    public DataSetData() { }

    //region Getter & Setter

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    public LinkedList<DataSetDataField> getFields() {
        return fields;
    }

    public void setFields(LinkedList<DataSetDataField> fields) {
        this.fields = fields;
    }


    //endregion
}
