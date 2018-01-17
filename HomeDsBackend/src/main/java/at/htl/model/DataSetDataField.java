package at.htl.model;

import java.util.Date;

public class DataSetDataField {

    private long dataSetColumnId;
    private String colName;
    public String value;
    private Date expireDate;

    public DataSetDataField() { }

    //region Getter & Setter
    public long getDataSetColumnId() {
        return dataSetColumnId;
    }

    public void setDataSetColumnId(long dataSetColumnId) {
        this.dataSetColumnId = dataSetColumnId;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }


    //endregion
}
