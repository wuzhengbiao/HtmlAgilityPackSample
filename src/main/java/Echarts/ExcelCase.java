package Echarts;
/**
 * Created by admin on 2021/12/10.
 * wuzb
 */
public class ExcelCase {
    /**
     * 序号
     */
    private String id;
    /**
     * 图表名称
     */
    private String ChartTitle;
    /**
     * 图表类型
     */
    private String ChartType;
    /**
     * 图表x轴
     */
    private String ChartXaxis;
    /**
     * 图表Y轴
     */
    private String ChartYaxis;
    /**
     * 图表Y轴名称
     */
    private String ChartYaxisTitle;
    /**
     * 要查询的数据语句
     */
    private String SelectSql;
    /**
     * 是否在同一张表格内显示
     */
    private String IsSameAsTable;

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getChartTitle() { return ChartTitle; }

    public void setChartTitle(String ChartTitle) {
        this.ChartTitle = ChartTitle;
    }

    public String getChartType() { return ChartType; }

    public void setChartType(String ChartType) {
        this.ChartType = ChartType;
    }

    public String getChartChartXaxis() { return ChartXaxis; }

    public void setChartXaxis(String ChartXaxis) {
        this.ChartXaxis = ChartXaxis;
    }

    public String getChartYaxis() { return ChartYaxis; }

    public void setChartYaxis(String ChartYaxis) {
        this.ChartYaxis = ChartYaxis;
    }

    public String getChartYaxisTitle() { return ChartYaxisTitle; }

    public void setChartYaxisTitle(String ChartYaxisTitle) {
        this.ChartYaxisTitle = ChartYaxisTitle;
    }

    public String getSelectSql() { return SelectSql; }

    public void setSelectSql(String SelectSql) {
        this.SelectSql = SelectSql;
    }

    public String getIsSameAsTable() { return IsSameAsTable; }

    public void setIsSameAsTable(String IsSameAsTable) {
        this.IsSameAsTable = IsSameAsTable;
    }


}
