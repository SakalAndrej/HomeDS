package homeds.htl.at.homedsjee.entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Felix on 07.02.2018.
 */

public class DataSetDataField implements Serializable {
    private Long dataSetColumnId;
    private Long dataId;
    //private String colName;
    private String value;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String Title;

    public DataSetDataField(long dataSetColumnId, long dataId, String value, LocalDate fromDate, LocalDate toDate, String title) {
        this.dataSetColumnId = dataSetColumnId;
        this.dataId = dataId;
        //this.colName = colName;
        this.value = value;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.Title = title;
    }

    public DataSetDataField() { }

    //region Getter & Setter
    public Long getDataSetColumnId() {
        return dataSetColumnId;
    }

    public void setDataSetColumnId(Long dataSetColumnId) {
        this.dataSetColumnId = dataSetColumnId;
    }





    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }
}
