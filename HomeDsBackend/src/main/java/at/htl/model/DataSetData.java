package at.htl.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

public class DataSetData {

    private long dataId;

    public DataSetData() { }

    //region Getter & Setter

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    //endregion
}
