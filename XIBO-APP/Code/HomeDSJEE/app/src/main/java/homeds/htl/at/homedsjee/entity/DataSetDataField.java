package homeds.htl.at.homedsjee.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Felix on 07.02.2018.
 */

public class DataSetDataField implements Serializable {
    private long dataSetColumnId;
    private String colName;
    private String value;
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


}
