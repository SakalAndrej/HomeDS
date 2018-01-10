package at.htl.model;

import java.util.Date;

public class DataSetDataRow {

    private String colName;
    private String value;
    private Date expireDate;

    public DataSetDataRow(String colName, String value, Date expireDate) {
        this.colName = colName;
        this.value = value;
        this.expireDate = expireDate;
    }

    public DataSetDataRow() { }

    //region Getter & Setter

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
