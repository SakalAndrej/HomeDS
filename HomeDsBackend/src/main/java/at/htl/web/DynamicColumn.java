package at.htl.web;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import at.htl.model.DataSet;
import at.htl.model.DataSetData;
import at.htl.model.DataSetDataField;
import at.htl.web.DataSetController;
import at.htl.xiboClient.DataSetApi;
import javafx.scene.chart.XYChart;
import net.bootsfaces.component.dataTableColumn.DataTableColumn;
import org.primefaces.component.column.Column;
import org.primefaces.component.columngroup.ColumnGroup;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.row.Row;

import java.util.LinkedList;


@ManagedBean
@ViewScoped
public class DynamicColumn {

    @Inject
    DataSetApi dataSetApi;

    private DataTable myDataTable;

    public DataTable getMyDataTable() {
        return myDataTable;
    }

    public void setMyDataTable(DataTable myDataTable) {
        this.myDataTable = myDataTable;
    }

    @PostConstruct
    public void init() {
        LinkedList<DataSetData> dataSets = dataSetApi.getAllDataSetData(2);
        myDataTable = new DataTable();
        myDataTable.setValue(dataSets);
        myDataTable.setVar("mydata");

        Row headerRow = new Row();
        myDataTable.getChildren().add(headerRow);
        Column column = new Column();
        column.setColspan(1);
        headerRow.getChildren().add(column);
        column.setHeaderText("Test");

        FacesContext fc = FacesContext.getCurrentInstance();
        Application application = fc.getApplication();
        ExpressionFactory ef = application.getExpressionFactory();
        ELContext elc = fc.getELContext();


        LinkedList<DataTableColumn> dataTableColumns = new LinkedList<>();
        for (int i = 0; i < dataSets.size(); i++) {
            for(DataSetDataField field : dataSets.get(i).getFields()) {
                DataTableColumn dataTableColumn = new DataTableColumn();
                dataTableColumn.setLabel(field.getColName());

                ValueExpression indexValueExp = ef.createValueExpression(elc, "#{field.value}", Object.class);
                HtmlOutputText indexOutput = (HtmlOutputText)application.createComponent( HtmlOutputText.COMPONENT_TYPE );
                dataTableColumn.setValueExpression("value",indexValueExp);
                myDataTable.getChildren().add(dataTableColumn);
                dataTableColumns.add(dataTableColumn);
            }
        }
        //myDataTable.setC(dataTableColumns);
    }
}