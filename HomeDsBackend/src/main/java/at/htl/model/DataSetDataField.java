package at.htl.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
@NamedQueries({ @NamedQuery(name = "DataSetDataField.GetAll", query = "select d from DataSetDataField d")})

@Table
@Entity
public class DataSetDataField {

    private long dataSetId = 5;

    @Id
    private long dataRowId;

    // News Content
    private String value;

    // Title of the News
    private String Title;

    // Expiration Properties
    private LocalDate fromDate;
    private LocalDate toDate;

    public DataSetDataField() { }

    //region Getter & Setter
    public long getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(long dataSetColumnId) {
        this.dataSetId = dataSetColumnId;
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
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public long getDataRowId() {
        return dataRowId;
    }

    public void setDataRowId(long dataId) {
        this.dataRowId = dataId;
    }

    public Date getDateFromDate() {
        if (fromDate != null)
            return Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        else
            return null;
    }

    public void setDateFromDate(Date fromDate) {
        if (fromDate != null)
            this.fromDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        else
            this.fromDate = null;
    }

    public Date getDateToDate() {
        if (toDate!=null)
            return Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        else
            return null;
    }

    public void setDateToDate(Date toDate) {
        if (toDate!=null)
            this.toDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        else
            this.toDate = null;
    }
    //endregion
}
