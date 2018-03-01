package homeds.htl.at.homedsjee.entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Felix on 07.02.2018.
 */

public class DataSetDataField implements Serializable {
    private Long id;
    private Long dataSetId;
    private Long dataRowId;
    //private String colName;
    private String value;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String title;

    public DataSetDataField(Long id, Long dataSetId, Long dataRowId, String value, LocalDate fromDate, LocalDate toDate, String title) {
        this.id = id;
        this.dataSetId = dataSetId;
        this.dataRowId = dataRowId;
        this.value = value;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.title = title;
    }

    public DataSetDataField() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(Long dataSetId) {
        this.dataSetId = dataSetId;
    }

    public Long getDataRowId() {
        return dataRowId;
    }

    public void setDataRowId(Long dataRowId) {
        this.dataRowId = dataRowId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

