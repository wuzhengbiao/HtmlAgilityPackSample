package Com;

import CollectionOfFunctionalMethods.BasicMethods.GetCurrentSystemTime;
import CollectionOfFunctionalMethods.BasicMethods.GetLocalConfig;
import CollectionOfFunctionalMethods.BasicMethods.GetRandom;
import CollectionOfFunctionalMethods.BasicMethods.InterceptFixedLength;
import CollectionOfFunctionalMethods.DatabaseRelatedMethods.DataBase;
import CollectionOfFunctionalMethods.DomainNameBasicConfiguration.UrlBasicConfigtions;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.InputStream;


/**
 * Created by win7 on 2016/1/3.
 * 本类都是对.xlsx的操作
 */
public class ReadExcel {
    public  static String Jirafilepath="";
    DataBase ExcelData=new DataBase();
    List<String> title = new ArrayList<String>();
    /**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws java.io.IOException
     */
    public List<TestingCase> readExcel(String path) throws IOException {
        if (path == null || Common.EMPTY.equals(path)) {
            return null;
        } else {
//            String postfix = Util.getPostfix(path);
            String postfix = Util.getPostfix("xls/readXLSX.xlsx");
            if (!Common.EMPTY.equals(postfix)) {
                if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path);
                } else if (Common.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path);
                }
            } else {
                System.out.println(path + Common.NOT_EXCEL_FILE);
            }
        }
        return null;
    }
    /**
     * Read the Excel 2021
     *@auth wuzb
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public List<TestingCase> readXlsxDataPrepare(String path) throws IOException, SQLException {
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        TestingCase testingCase = null;
        List<TestingCase> copylist = new ArrayList<TestingCase>();
        String[] PrepareContext = null;
        GetLocalConfig Config=new GetLocalConfig();
        String Prepareconfig = Config.ReadConfigFile("PlatformBasicInformation.txt");
        PrepareContext = Config.GetBySemicolonFromConfigFile(Prepareconfig);

        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            for(int prepareNum=0;prepareNum<PrepareContext.length;prepareNum++)
            {
                String getAppAuthentor="";
                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    String FirstValue = getValue(xssfRow.getCell(0));
                    if (FirstValue.equals("#")) {
                        System.out.println(FirstValue + "序号：" + rowNum + "我被注释了！\n");
                    } else {
                        if (xssfRow != null) {
                            testingCase = new TestingCase();
                            XSSFCell no = xssfRow.getCell(0);
                            XSSFCell description = xssfRow.getCell(1);
                            XSSFCell model = xssfRow.getCell(2);
                            XSSFCell mode = xssfRow.getCell(3);
                            XSSFCell modepath = xssfRow.getCell(4);
                            XSSFCell text = xssfRow.getCell(5);
                            XSSFCell AppAuthentication = xssfRow.getCell(6);
                            XSSFCell Authorization = xssfRow.getCell(7);
                            XSSFCell ContextInterfaceReturn = xssfRow.getCell(8);
                            XSSFCell CommonVariable = xssfRow.getCell(9);
                            XSSFCell whereskip = xssfRow.getCell(10);
                            String ReturnForm = ProcessingExcelDataTypes(text, getValue(text));//调用数据处理方法
                            testingCase.setId(getValue(no));
                            testingCase.setDescription(getValue(description)+String.valueOf(prepareNum+1));
                            testingCase.setModel(getValue(model));
                            testingCase.setMode(getValue(mode));
                            if (getValue(modepath).contains("Domain")) {
                                getAppAuthentor=getValue(modepath).replace("Domain", PrepareContext[prepareNum].trim());
                            } /*else if (getValue(modepath).contains("xxx")) {
                                testingCase.setModePath(getValue(modepath).replace("xxx", PrepareContext[PrepareContext.length / 2 + prepareNum]));
                            } */else {
                                getAppAuthentor=getValue(modepath);
                            }
                            testingCase.setModePath(getAppAuthentor);
                            if(getValue(text).contains("Random"))
                            {
                                testingCase.setText(ReturnForm.replace("Random", GetRandom.ReturnGetRandomChar(3)));
                                if(getValue(text).contains("MysqlRandom"))
                                {
                                    title=ExcelData.QueryDatabaseSql("SELECT content_body_st  from collec_informations_basic WHERE info_warehousing_time>=CURDATE() and  content_type_int='2'");
                                    if(title.size()==0)
                                    {
                                        title=ExcelData.QueryDatabaseSql("SELECT PLATFORM_EXCEPTION_INFORMATIN from platform_exception_informatin_table WHERE CREATEIME>=CURDATE() and PLATFORM_EXCEPTION_TYPE='2'");
                                    }
                                    System.out.println("title" +title);
                                    testingCase.setText(InterceptFixedLength.CutString(title+""));
                                }
                            }
                            else if(getValue(text).contains("Domain"))
                            {
                                testingCase.setText(ReturnForm.replace("Domain",PrepareContext[prepareNum].trim()));
                            }
                            else if(getValue(text).contains("Date"))
                            {
                                testingCase.setText(ReturnForm.replace("Date", GetCurrentSystemTime.GetCurrentTime()));
                                if(getValue(text).contains("MysqlDate"))
                                {
                                    title=ExcelData.QueryDatabaseSql("SELECT info_platform_name  from collec_informations_basic WHERE info_warehousing_time>=CURDATE() and  content_type_int='2'");
                                    System.out.println("title" +title);
                                    if(title.size()==0)
                                    {
                                        title=ExcelData.QueryDatabaseSql("SELECT PLATFORM_NAME from platform_exception_informatin_table WHERE CREATEIME>=CURDATE() and PLATFORM_EXCEPTION_TYPE='2'");
                                    }
                                    testingCase.setText(title+GetCurrentSystemTime.GetCurrentTime());
                                }
                            }
                            else if(getValue(text).contains("JiraPath"))
                            {
                                Jirafilepath= StringUtils.substringBefore(this.getClass().getResource("").getPath(),"/target")+"\\"+getValue(text).split("/")[1];
                                System.out.println("路径："+Jirafilepath);
                                testingCase.setText(ReturnForm.replace(getValue(text),java.net.URLDecoder.decode(Jirafilepath,"utf-8")));
                            }
                            else {
                                testingCase.setText(ReturnForm);
                            }
                            try {
                                testingCase.setAppAuthentication(UrlBasicConfigtions.GetUrlAppAuthentication(PrepareContext[prepareNum].trim()));
                         //       System.out.println("AppAuthentication=  "+UrlBasicConfigtions.GetUrlAppAuthentication(PrepareContext[prepareNum].trim()));
                            } catch (Exception e) {
                                testingCase.setAppAuthentication("空");
                            }
                            try {
                                testingCase.setAuthorization(getValue(Authorization));
                            } catch (Exception e) {
                                testingCase.setAuthorization("空");
                            }
                            try {
                                testingCase.setContextInterfaceReturn(getValue(ContextInterfaceReturn));
                            } catch (Exception e) {
                                testingCase.setContextInterfaceReturn("空");
                            }
                            try {
                                testingCase.setCommonVariable(getValue(CommonVariable));
                            } catch (Exception e) {
                                testingCase.setCommonVariable("空");
                            }
                            try {
                                testingCase.setWhetherskip(getValue(whereskip));
                            } catch (Exception e) {
                                testingCase.setWhetherskip("否");
                            }
                            if (testingCase.getWhetherskip().equals("否"))//判断是否跳过执行步骤
                            {
                                copylist.add(testingCase);
                                if (testingCase.getText().equals("单例重跑")) {
                                    copylist.add(testingCase);
                                }
                            }
                        }
                    }
                }
            }
        }

        return copylist;
    }
    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public List<TestingCase> readXlsx(String path) throws IOException{
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        TestingCase testingCase = null;
        List<TestingCase> list = new ArrayList<TestingCase>();
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum=1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                String FirstValue=getValue(xssfRow.getCell(0));
                if(FirstValue.equals("#"))
                {
                    System.out.println(FirstValue+"序号："+rowNum+"我被注释了！\n");
                }
                else {
                    if (xssfRow != null) {
                        testingCase = new TestingCase();
                        XSSFCell no = xssfRow.getCell( 0 );
                        XSSFCell description = xssfRow.getCell( 1 );
                        XSSFCell model = xssfRow.getCell( 2 );
                        XSSFCell mode = xssfRow.getCell( 3 );
                        XSSFCell modepath = xssfRow.getCell( 4 );
                        XSSFCell text = xssfRow.getCell( 5 );
                        XSSFCell AppAuthentication = xssfRow.getCell( 6 );
                        XSSFCell Authorization = xssfRow.getCell( 7 );
                        XSSFCell ContextInterfaceReturn = xssfRow.getCell( 8 );
                        XSSFCell CommonVariable = xssfRow.getCell( 9 );
                        XSSFCell whereskip = xssfRow.getCell( 10 );
                        String ReturnForm = ProcessingExcelDataTypes( text, getValue( text ).trim() );//调用数据处理方法
                        testingCase.setId( getValue( no ).trim() );
                        testingCase.setDescription( getValue( description ).trim() );
                        testingCase.setModel( getValue( model ).trim() );
                        testingCase.setMode( getValue( mode ).trim() );
                        testingCase.setModePath(getValue(modepath).trim());
                        testingCase.setText( ReturnForm );
                        try {
                            testingCase.setAppAuthentication( getValue( AppAuthentication ) );
                        }
                        catch (Exception e) {
                            testingCase.setAppAuthentication( "空" );
                        }
                        try {
                            testingCase.setAuthorization(getValue(Authorization));
                        }
                        catch (Exception e) {
                            testingCase.setAuthorization( "空" );
                        }
                        try {
                            testingCase.setContextInterfaceReturn(getValue(ContextInterfaceReturn));
                        }
                        catch (Exception e)
                        {
                            testingCase.setContextInterfaceReturn( "空" );
                        }
                        try {
                            testingCase.setCommonVariable(getValue(CommonVariable));
                        }
                        catch (Exception e)
                        {
                            testingCase.setCommonVariable( "空" );
                        }
                        try {
                            testingCase.setWhetherskip( getValue( whereskip ) );
                        }
                        catch (Exception e)
                        {
                            testingCase.setWhetherskip( "否" );
                        }
                        if (testingCase.getWhetherskip().equals( "否" ))//判断是否跳过执行步骤
                        {
                            list.add( testingCase );
                            if (testingCase.getText().equals( "单例重跑" )) {
                                list.add( testingCase );
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
    /**
     **处理读取excel数据类型，时间和数值等
     */
    public static String ProcessingExcelDataTypes(XSSFCell cell,String result) {
        switch(cell.getCellType()){
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                if (DateUtil.isCellDateFormatted(cell)) //是否是时间
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    result = dateFormat.format(date);
                }
                else {
                    DecimalFormat data = new DecimalFormat("0");//设置阿拉伯数字
                    result = data.format(cell.getNumericCellValue());
                }
                break;
            case HSSFCell.CELL_TYPE_STRING://字符串
                result=cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN://布尔值
                Boolean val1 = cell.getBooleanCellValue();
                result=val1.toString();
                break;
            case HSSFCell.CELL_TYPE_BLANK: // 空值
                System.out.println("BLANK \n");
                break;
            case HSSFCell.CELL_TYPE_FORMULA: // 公式
                System.out.println(cell.getCellFormula()+ "公式后续有用到在处理\n");
                break;
            case HSSFCell.CELL_TYPE_ERROR: // 故障
                System.out.println(cell.getErrorCellValue()+"\n");
                break;
            default:
                System.out.print("未知类型 \n");
                break;
        }
        return result;
    }

    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public List<TestingCase> readXls(String path) throws IOException {
        System.out.println(Common.PROCESSING + path);
        InputStream is = new FileInputStream(path);
//        Workbook workbook = WorkbookFactory.create(is);
//        Sheet hssSheet = (Sheet) workbook.getSheetAt(0);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        TestingCase testingCase = null;
        List<TestingCase> list = new ArrayList<TestingCase>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    testingCase = new TestingCase();
                    HSSFCell no = hssfRow.getCell(0);
                    HSSFCell description = hssfRow.getCell(1);
                    HSSFCell model = hssfRow.getCell(2);
                    HSSFCell mode = hssfRow.getCell(3);
                    HSSFCell modepath = hssfRow.getCell(4);
                    HSSFCell text = hssfRow.getCell(5);
                    testingCase.setId(getValue(no));
                    testingCase.setDescription(getValue(description));
                    testingCase.setModel(getValue(model));
                    testingCase.setMode(getValue(mode));
                    testingCase.setModePath(getValue(modepath));
                    testingCase.setText(getValue(text));
                    list.add(testingCase);
                }
            }
        }
        return list;
    }

    @SuppressWarnings("static-access")
    public String getValue(XSSFCell xssfRow) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
}