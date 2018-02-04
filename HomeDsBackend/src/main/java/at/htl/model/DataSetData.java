package at.htl.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Table
@Entity
public class DataSetData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dataId;

    @OneToMany(fetch = FetchType.EAGER)
    private List<DataSetDataField> fields;

    public DataSetData() { }

    //region Getter & Setter

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    public List<DataSetDataField> getFields() {
        return fields;
    }

    public void setFields(LinkedList<DataSetDataField> fields) {
        this.fields = fields;
    }


    //endregion
}
