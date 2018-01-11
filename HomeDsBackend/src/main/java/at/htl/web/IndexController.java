package at.htl.web;

import at.htl.xiboClient.DataSetApi;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class IndexController implements Serializable{

    @Inject
    DataSetApi dataSetApi;

    public static String andrej = "hi andrej";

    public String getDataSet() {
        return dataSetApi.getAllDataSet(-1,null,null).get(0).getDataSetName();
    }

    public String getAndrej() {
        return andrej;
    }
}
