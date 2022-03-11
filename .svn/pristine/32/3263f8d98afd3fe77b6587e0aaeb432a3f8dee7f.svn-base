package Echarts;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EchartsWriteExcel {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void main(String[] args) throws IOException, SQLException {

        EchartsReadExcel Echarts=new EchartsReadExcel();
        List<ExcelCase> EchartsCase=new ArrayList<ExcelCase>();
        EchartsCase=Echarts.readEchartsExcel("D:\\PyReportChart\\GSZJ.xlsx");
        writeExcel(EchartsCase, 8, "D:\\PyReportChart\\test.xlsx");
    }

    public static void writeExcel(List<ExcelCase> dataList, int totalColumnNumCount,String finalXlsxPath){
        OutputStream out = null;
        try {
            ExcelCase EchartsCaseEvery = new ExcelCase();
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            for (int rowCount = 0; rowCount < dataList.size(); rowCount++) {
                // 创建一行：从第二行开始，跳过标题属性行
                Row row = sheet.createRow(rowCount + 1);
                // 获取每一行的内容值
                EchartsCaseEvery = dataList.get(rowCount);
                for (int ColumnNum = 0; ColumnNum <= totalColumnNumCount; ColumnNum++) {
                    // 在一行内循环赋值，现在一行有8个数值
                    Cell first = row.createCell(0);
                    first.setCellValue(EchartsCaseEvery.getId());
                    Cell second = row.createCell(1);
                    second.setCellValue(EchartsCaseEvery.getChartTitle());
                    Cell third = row.createCell(2);
                    third.setCellValue(EchartsCaseEvery.getChartType());
                    Cell four = row.createCell(3);
                    four.setCellValue(EchartsCaseEvery.getChartChartXaxis());
                    Cell five = row.createCell(4);
                    five.setCellValue(EchartsCaseEvery.getChartYaxis());
                    Cell six = row.createCell(5);
                    six.setCellValue(EchartsCaseEvery.getChartYaxisTitle());
                    Cell seven = row.createCell(6);
                    seven.setCellValue(EchartsCaseEvery.getSelectSql());
                    Cell eight = row.createCell(7);
                    eight.setCellValue(EchartsCaseEvery.getIsSameAsTable());
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException{
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if(file.getName().endsWith(EXCEL_XLS)){     //Excel 2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
}
