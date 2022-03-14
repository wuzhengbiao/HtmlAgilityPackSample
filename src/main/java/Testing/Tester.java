package Testing;
import CollectionOfFunctionalMethods.BasicMethods.*;
import CollectionOfFunctionalMethods.BasicMethods.*;
import CollectionOfFunctionalMethods.DatabaseRelatedMethods.DataBase;
import CollectionOfFunctionalMethods.DatabaseRelatedMethods.DatabaseDataOperation;
import CollectionOfFunctionalMethods.HttpAndHttpsProtocol.HttpRequestMethod;
import CollectionOfFunctionalMethods.HttpAndHttpsProtocol.HttpsRequest;
import CollectionOfFunctionalMethods.HttpAndHttpsProtocol.PostGetGeneralMethod;
import CollectionOfFunctionalMethods.UseCaseReRunCorrelation.OverrideIReTry;
import CollectionOfFunctionalMethods.UseCaseReRunCorrelation.ReTryTimes;
import Com.*;
import DingDingTalk.DingTalkCall;
import Init.InitDriver;
import macaca.client.MacacaClient;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/10/27.
 */
public class Tester  {
    //设置一个延迟时长从testng.xml文件中读取的变量
    public String ProgramPath;
    @Test
    @Parameters({"TestFilePath" ,"PlatformName", "TIME" , "TestName","IfDataPrepare"})
    public void tester(String TestFilePath,String PlatformName,int TIME,String TestName,String IfDataPrepare) throws Exception {
        int    QueryCount=0;//统计没有找到元素的次数
        int    ElementCount=1;//代替excle文本序列号
        int RerunTimes ;//重跑次数
        String sqlinsert="";
        InitDriver Init= new InitDriver();
        MacacaClient driver=Init.MacacaInit();//macaca初始化对象
        Execute Execute = new Execute();
        AbnormalScreenshot Abnormal = new AbnormalScreenshot();
  //      JiraPicturesPath   jira=new JiraPicturesPath();
        DataBase data=new DataBase();
        List<TestingCase> list = new ArrayList<TestingCase>();
        DatabaseDataOperation Operation=new DatabaseDataOperation();
        MailDelivery.TestNgType=PlatformName;//用于邮件类型通知
        DingTalkCall.MessageType=PlatformName;//用于钉钉类型通知
        Map<Integer,Integer> MapListCase = new HashMap<Integer, Integer>();//储存没找到元素的序号
        ProgramPath = URLDecoder.decode(Tester.class.getResource("").toString(),"UTF-8");
        ProgramPath = StringUtils.substringBefore(ProgramPath,"/target");
        ProgramPath = StringUtils.substringAfter(ProgramPath,"file:/");
        ReadExcel reade = new ReadExcel();
        if(IfDataPrepare.equals("是"))//判断是否是数据流程准备文件
        {
            list = reade.readXlsxDataPrepare( ProgramPath + TestFilePath );
        }
        else{
            list = reade.readXlsx( ProgramPath + TestFilePath );//正常用例执行
        }
        driver.maximize();
        if (list != null) {
            for (TestingCase testCase : list) {
                //输出读取对应的用例正行内容
                System.out.println("No." + testCase.getId() + "操作说明" + testCase.getDescription() + ",操作方法:" + testCase.getModel() + ",获取元素方法:" + testCase.getMode() + ",获取元素路径:" + testCase.getModePath() + ",文本内容:" + testCase.getText());
                   //整行表格数据传入，并对获取的内容进行实质性执行用例
                int ResultNum=Execute.execute(driver,testCase,TIME);//开始执行macaca用例
                System.out.println("ResultNum="+ResultNum+"\n");
                String  img=".\\" + TestName + testCase.getDescription() + "jpg";
                String  reportimg="PHOTO" + "..\\..\\" + TestName + testCase.getDescription() + "jpg";
                String Loginstruction="No." + testCase.getId() + " 协议说明: " + testCase.getDescription() + ",传输协议: " + testCase.getModel() + ", 请求方式: " + testCase.getMode()  +" 操作时间:  "+ GetCurrentSystemTime.GetCurrentTime();
                if(ResultNum==1)//查找元素正常
                {
                    driver.sleep(2000);
                    Reporter.log( "No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime());
                    driver.saveScreenshot(img);
                }
                else {
                    //元素没有找到
                    if (ResultNum == 0) {
                        MapListCase.put(0, -1);//设置初始值，不然下面相减会报错
                        QueryCount++;
                        MapListCase.put(QueryCount, ElementCount);//存储没有找到元素的列表信息
                       if(MapListCase.get(QueryCount)-MapListCase.get(QueryCount-1)==1)//判断是不是连续操作2次都报错
                       {
                           Reporter.log("<p  style=\"font-weight: 900;color:red;text-shadow: 2px 2px 5px red;font-size:15px\">" + "No." + testCase.getId() + "操作说明" + testCase.getDescription() + "\n操作时间: " + GetCurrentSystemTime.GetCurrentTime() + "</p>");
                           driver.saveScreenshot(img);
                           Reporter.log(reportimg);//写入报告图片地址
                           //连续2次步骤都报错,会进行断言,并停止程序执行
                           String insertexception1 = Operation.DataToInsertAbnormal(1, 1, list.get(0).getModePath(), PlatformName, 0, GetCurrentSystemTime.GetCurrentTime(), "序号 No." + list.get(MapListCase.get(QueryCount - 1)).getId() + ", 操作说明：" + list.get(MapListCase.get(QueryCount - 1)).getDescription() + "   原因： 连续2个操作找不到元素，有可能是定位错了，或者是流程出现未知的情况！！");
                           data.InsertDatabaseSql(insertexception1);
                           for (RerunTimes = 0; RerunTimes < list.size(); RerunTimes++) {
                               if (list.get(RerunTimes).getText().equals("重跑")) {
                                   ReTryTimes.maxReTryNum = ReTryTimes.maxReTryNum + 1;
                                   System.out.print(" ReTryTimes.maxReTryNum  = " + ReTryTimes.maxReTryNum + "\n");
                                   if (!IfDataPrepare.equals("是"))//判断是否是数据流程准备文件
                                   {
                                       DingTalkCall.DingTalkCallMessageDesign("现网Macaca用例执行告警通知：" + "No." + testCase.getId() + " 操作说明：" + testCase.getDescription() + " 重跑了 " + ReTryTimes.maxReTryNum + " 次，"+"  操作时间: "+ GetCurrentSystemTime.GetCurrentTime()+" ！！", 1);
                                   }
                                   break;
                               }
                           }
                           if (!IfDataPrepare.equals("是"))//判断是否是数据流程准备文件，数据流的报错就继续执行
                           {
                               EventListenerMonitoring.Listenerflag = 2;
                               Runtime.getRuntime().exec("taskkill /f /im chrome.exe");//调用dos命令杀死谷歌进程
                               Assert.assertEquals(ResultNum, 1, "序号 No." + list.get(MapListCase.get(QueryCount - 1)).getId() + ", 操作说明：" + list.get(MapListCase.get(QueryCount - 1)).getDescription() + "   原因： 连续2个操作找不到元素，有可能是定位错了，或者是流程出现未知的情况！！");
                           }
                       }
                            Reporter.log( "<p  style=\"font-weight: 900;color:DarkOrange;text-shadow: 2px 2px 5px DarkOrange;font-size:15px\">"+"No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime()+"</p>");
                            EventListenerMonitoring.Listenerflag = 3;
                            MyAssertion.verifyEquals( ResultNum, 1, "No." + testCase.getId() + " 操作说明：" + testCase.getDescription() + "  没有找到元素！！" + "\n操作时间: " + GetCurrentSystemTime.GetCurrentTime() );//捕获assert断言
                            driver.saveScreenshot( img );
                    }
                    //用于处理报表数据
                    else if (ResultNum == 3) {
                        Reporter.log( "No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime());
                        driver.saveScreenshot(img);
                        String insertexception3=Operation.DataToInsertStatictics(list.get(0).getModePath(), PlatformName,Integer.parseInt(Execute.Returnbody),GetCurrentSystemTime.GetCurrentTime(),"每日数量统计 !!");
                        data.InsertDatabaseSql(insertexception3);
                    }
                    //针对连接超时异常，跳过执行
                    else if(ResultNum==5)
                    {
                        Reporter.log( "<p  style=\"font-weight: 900;color:DarkOrange;text-shadow: 2px 2px 5px DarkOrange;font-size:15px \">"+"No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime()+"</p>");
                        driver.saveScreenshot(img);
                        String insertexception3=Operation.DataToInsertAbnormal(3,1,list.get(0).getModePath(),PlatformName,0, GetCurrentSystemTime.GetCurrentTime(),"连接超时异常！！");
                        data.InsertDatabaseSql(insertexception3);
                        continue;
                    }
                    else if (ResultNum==8)
                    {
                        if(HttpsRequest.HttpSStatusFlag.equals( "0" ))//判断协议请求是否失败（主要还是code状态判断），失败进入css修饰操作
                        {
                            EventListenerMonitoring.Listenerflag = 2;;
                            Reporter.log("<p  style=\"font-weight: 900;color:red;text-align: left;text-shadow: 2px 2px 5px red;font-size:15px\">"+Loginstruction+"</p>");
                            Reporter.log( "<p  style=\"color:red\"> 请求url：" + SpecifyLengthWrap.getStringByEnter(90,testCase.getModePath())+"</p>" );
                            Reporter.log( "<p  style=\"color:red\"> 参数值： " + SpecifyLengthWrap.getStringByEnter(90,testCase.getText()) +"</p>");
                            Reporter.log( "<p  style=\"color:red\"> 返回值： " + SpecifyLengthWrap.getStringByEnter(90,Execute.PostGet.Ymresult.toString())+"</p>");
                     //       DingTalkCall.DingTalkCallMessageDesign("现网Macaca接口告警通知：" + "No." + testCase.getId() + " 操作说明：" + testCase.getDescription() +" 报错了!!", 1);
                        }
                        else {
                            Reporter.log("<p  style=\"font-weight: 900;color:black;text-align: left;text-shadow: 2px 2px 5px black;font-size:15px\">"+Loginstruction+"</p>");
                            Reporter.log( "<p  style=\"color:green\"> 请求url：" + SpecifyLengthWrap.getStringByEnter(90,testCase.getModePath())+"</p>");
                            Reporter.log( "<p  style=\"color:green\"> 参数值： " + SpecifyLengthWrap.getStringByEnter(90,testCase.getText()) +"</p>");
                            Reporter.log( "<p  style=\"color:green\"> 返回值： " +  SpecifyLengthWrap.getStringByEnter(90,Execute.PostGet.Ymresult.toString())+"</p>" );
                        }
                        continue;//用于接口报告别删除
                    }
                    else if (ResultNum==11||ResultNum==10)//用于接口比较结果判断
                    {
                        sqlinsert="INSERT INTO `macaca`.`collec_informations_basic`(`info_key_id`, `info_content_id`, `info_platform_name`, `info_sources_int`, `info_type_int`, `info_mode_int`, `content_body_st`, `content_type_int`," +
                            " `info_time`, `info_status_int`, `info_provider_st`, `info_processor_st`, `info_warehousing_time`) " +
                            "VALUES (null, 'jiekoubaocuo123456', '现网接口返回结果不对', 1, 1, 1, '"+SpecifyLengthWrap.getStringByEnter(90,testCase.getText())+"\n\n"+SpecifyLengthWrap.getStringByEnter(90,Execute.PostGet.Ymresult.toString())
                                +"', 2, '"+GetCurrentSystemTime.GetCurrentTime()+"', 0, '吴正彪', '开发', '"+GetCurrentSystemTime.GetCurrentTime()+"');";
                        if(ResultNum==11)
                        {
                            data.InsertDatabaseSql(sqlinsert);
                            Reporter.log("<p  style=\"font-weight: 900;color:red;text-align: left;text-shadow: 2px 2px 5px red;font-size:15px\">"+Loginstruction+"</p>");
                            Reporter.log( "<p  style=\"color:red\"> 比较值： " + SpecifyLengthWrap.getStringByEnter(90,testCase.getText()) +"</p>");
                            Reporter.log( "<p  style=\"color:red\"> 接口结果要对比的值： " + SpecifyLengthWrap.getStringByEnter(90,testCase.getText()) +"    比较结果：失败! </p>");
                        }
                    else{ Reporter.log("<p  style=\"font-weight: 900;color:green;text-align: left;text-shadow: 2px 2px 5px green;font-size:15px\">"+Loginstruction+"</p>");
                        Reporter.log( "<p  style=\"color:green\"> 接口结果要对比的值： " + SpecifyLengthWrap.getStringByEnter(90,testCase.getText()) +"    比较结果：通过! </p>");
                        }
						continue;//用于接口报告别删除
                    }
                    else {
                        Reporter.log( "No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime());
                        driver.sleep(2000);
                        driver.saveScreenshot(img);
                    }
                    int AbnormalStatus=Abnormal.WhetherCatchAbnormal(driver,img);//查找异常
                    //查询到系统出现异常
                    if(AbnormalStatus==1)
                    {
                        String insertexception2=Operation.DataToInsertAbnormal(2,1,list.get(0).getModePath(), PlatformName, 0,GetCurrentSystemTime.GetCurrentTime(),"macaca检测平台有异常,联系对应项目经理!!!"+"\n"+Abnormal.AbnormalDetailContent);
                        data.InsertDatabaseSql(insertexception2);
                        Reporter.log(reportimg);
                        System.out.print("AbnormalDetailContent= :"+Abnormal.AbnormalDetailContent+"\n");
                        EventListenerMonitoring.Listenerflag = 2;
                        Assert.assertEquals(" do abnormal","no abnormal ","macaca检测平台有异常!!异常信息如下： "+"\n"+InterceptFixedLength.CutString(Abnormal.AbnormalDetailContent));
                    }
                }
                Reporter.log(reportimg);
                ElementCount++;
            }
        }
       Runtime.getRuntime().exec("taskkill /f /im chrome.exe");//调用dos命令杀死谷歌进程
    }
}
