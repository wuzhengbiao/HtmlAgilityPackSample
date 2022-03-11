package Echarts;

import Com.ReadExcel;
import Com.TestingCase;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EchartsReadExcel {
    /**
     * Read the Excel 2021
     *@auth wuzb
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    //用来专门读取Echarts表格，不混合自动化表格那块
    public List<ExcelCase> readEchartsExcel(String path) throws IOException, SQLException {
        InputStream is = new FileInputStream(path);
        ReadExcel exc = new ReadExcel();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<ExcelCase> copylist = new ArrayList<ExcelCase>();
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    ExcelCase EchartsCase = new ExcelCase();
                    XSSFCell no = xssfRow.getCell( 0 );
                    XSSFCell ChartTitle = xssfRow.getCell( 1 );
                    XSSFCell ChartType = xssfRow.getCell( 2 );
                    XSSFCell ChartXaxis = xssfRow.getCell( 3 );
                    XSSFCell ChartYaxis = xssfRow.getCell( 4 );
                    XSSFCell ChartYaxisTitle = xssfRow.getCell( 5 );
                    XSSFCell SelectSql = xssfRow.getCell( 6 );
                    XSSFCell IsSameAsTable = xssfRow.getCell( 7 );
                    EchartsCase.setId( exc.getValue( no ).trim() );
                    EchartsCase.setChartTitle( exc.getValue( ChartTitle ).trim() );
                    EchartsCase.setChartType( exc.getValue( ChartType ).trim() );
                    EchartsCase.setChartXaxis(exc.getValue(ChartXaxis).trim());
                    EchartsCase.setChartYaxis( exc.getValue( ChartYaxis ).trim() );
                    EchartsCase.setChartYaxisTitle(exc.getValue(ChartYaxisTitle).trim());
                    EchartsCase.setSelectSql(exc.getValue(SelectSql).trim());
                    EchartsCase.setIsSameAsTable(exc.getValue(IsSameAsTable).trim());
                    copylist.add( EchartsCase );
                }
            }
        }

        return copylist;
    }
}
