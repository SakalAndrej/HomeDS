package at.htl.web;

import at.htl.model.DataSet;
import at.htl.xiboClient.DataSetApi;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Model
public class IndexController implements Serializable {

    @Inject
    DataSetApi dataSetApi;

    public List<DataSet> getDataSet() {
        return dataSetApi.getAllDataSet(-1, null, null);
    }
}
