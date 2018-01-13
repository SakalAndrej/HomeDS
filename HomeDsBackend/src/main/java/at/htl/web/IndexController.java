package at.htl.web;

import at.htl.model.DataSet;
import at.htl.xiboClient.DataSetApi;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;

@Named
@RequestScoped
public class IndexController implements Serializable{

    @Inject
    DataSetApi dataSetApi;

    public LinkedList<DataSet> getDataSet() {
        return dataSetApi.getAllDataSet(-1,null,null);
    }
}
