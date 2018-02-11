package at.htl.model;

import javax.persistence.*;
import java.util.Date;
@NamedQueries({ @NamedQuery(name = "DataSetDataField.GetAll", query = "select d from DataSetDataField d")})

@Table
@Entity
public class DataSetDataField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dataSetColumnId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dataId;

    // Column Header
    private String colName;

    // News Content
    private String value;

    // Title of the News
    private String Title;

    // Expiration Properties
    private Date fromDate;
    private Date toDate;

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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    //endregion
}
