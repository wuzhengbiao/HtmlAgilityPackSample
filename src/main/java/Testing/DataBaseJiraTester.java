package Testing;
import CollectionOfFunctionalMethods.BasicMethods.GetCurrentSystemTime;
import CollectionOfFunctionalMethods.BasicMethods.GetLocalConfig;
import CollectionOfFunctionalMethods.DatabaseRelatedMethods.DataBase;
import Com.ReadExcel;
import org.apache.commons.lang3.StringUtils;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseJiraTester {
    @Test
    @Parameters({"TestFilePath" ,"PlatformName", "TIME" , "TestName","IfDataPrepare"})
    public void DataBaseJiratest(String TestFilePath,String PlatformName,int TIME,String TestName,String IfDataPrepare) throws Exception {
        DataBase data=new DataBase();
        Tester   test=new Tester();
        List<String> datalist = new ArrayList<String>();
        List<String> informations = new ArrayList<String>();
        //查询数据当天是否有出现异常情况数据
        informations=data.QueryDatabaseSql("SELECT * from collec_informations_basic WHERE info_warehousing_time>=CURDATE() and  content_type_int='2'");
        datalist=data.QueryDatabaseSql("SELECT * from platform_exception_informatin_table WHERE CREATEIME>=CURDATE() and PLATFORM_EXCEPTION_TYPE='2'");
        //出现异常情况数据，那么就会启动创建相应jira问题的动作
        if(datalist.size()>0||informations.size()>0)
        {
            test.tester(TestFilePath,PlatformName,TIME, TestName,IfDataPrepare);
            String folderpath =java.net.URLDecoder.decode(ReadExcel.Jirafilepath,"utf-8");
            //jira问题创建完成后，删除存储图片
            GetLocalConfig.delAllFile(folderpath);
        }
        else {
            Reporter.log( "\n<p  style=\"font-weight: 900px;color:LimeGreen;text-shadow: 2px 2px 5px Green;font-size:22px;font-style: oblique;\">"+"No Errors , No Jira !! 操作时间: "+ GetCurrentSystemTime.GetCurrentTime()+"</p>");
            System.out.println("NO Abnormal !!"+"\n");
        }
    }
}
