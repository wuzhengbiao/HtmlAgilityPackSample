package Com;

import CollectionOfFunctionalMethods.BasicMethods.*;
import CollectionOfFunctionalMethods.DatabaseRelatedMethods.DataBase;
import CollectionOfFunctionalMethods.GraphqlContext.GetReturnContent;
import CollectionOfFunctionalMethods.HttpAndHttpsProtocol.HttpRequestMethod;
import CollectionOfFunctionalMethods.HttpAndHttpsProtocol.HttpsRequest;
import CollectionOfFunctionalMethods.HttpAndHttpsProtocol.PostGetGeneralMethod;
import CollectionOfFunctionalMethods.UseCaseReRunCorrelation.OverrideIReTry;
import macaca.client.MacacaClient;
import macaca.client.commands.Element;
import macaca.client.common.GetElementWay;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/10/31.
 */
public class Execute {
//  返回0：元素未找寻到
//  返回1：正常
//  返回2：未进入合理判断的分支
//  返回3: 特殊处理报表数据过大截图时间
//  返回4: 未知的异常
//  返回5：超时
//  返回7：数据库执行成功
//  返回8：接口数据返回
//  返回9：列表数据返回
//  返回10；接口数据比较结果符合
//  返回11；接口比较结果不符合
//  返回12；返回纯文本内容
    private String Aftertime="";
    private String beforetime="0";
    private String GetClickText="";
    private List<String> JiraValue=new ArrayList<String>();
    public String   Returnbody="";
    private DataBase DatabaseExecute=new DataBase();
    public PostGetGeneralMethod PostGet=new PostGetGeneralMethod();
    private String Cookie="";
    public int execute(MacacaClient Driver, TestingCase Testingcase, int T) throws Exception {
        if (Testingcase.getModel().equals("访问")){
            try {
                Driver.sleep(7000);
                Driver.get(Testingcase.getModePath());
                return 1;
            }
            catch (Exception e) {
                return 5;
            }
        }
    else if (Testingcase.getModel().equals("输入")){
        //当前绑死使用Xpath方法
        try {
            if (DriverWaitingForGeneral.DriverWaite(Driver,Testingcase,T)>0) {
                {
                    Driver.elementByXPath(Testingcase.getModePath()).clearText();
                    Driver.elementByXPath(Testingcase.getModePath()).sendKeys(Testingcase.getText());
                    return 1;
                }
            }else
                {
                    return  DriverWaitingForGeneral.DriverWaite(Driver,Testingcase,T);
                }

    }catch (Exception e) {
            return 4;
        }
            }
    else if (Testingcase.getModel().equals("点击")) {
        try {
            if (DriverWaitingForGeneral.DriverWaite(Driver,Testingcase,T)>0) {
            Driver.elementByXPath(Testingcase.getModePath()).click();
            if (Testingcase.getText().contains("文本")) {
                GetClickText = Driver.elementByXPath(Testingcase.getModePath()).getText();
                Aftertime=GetClickText;//当前点击元素获取到的文本值赋值给被比较的成员
            }
            return 1;
            }
            else
            {
                return  DriverWaitingForGeneral.DriverWaite(Driver,Testingcase,T);
            }
        } catch (Exception e) {
            return 4;
        }
    }
    else if (Testingcase.getModel().equals("返回"))
    {
        String regEx="[^0-9]";
        Pattern Mypattern = Pattern.compile(regEx);//创建正则法则匹配的表达式
        Matcher matcherstring = Mypattern.matcher(Testingcase.getText());//Pattern调用matcher返回Matcher 类的实例,提供了对正则表达式支持,按正则匹配
        String  GetNumString=matcherstring.replaceAll("").trim();//不匹配的字符全部设置成空格,并去掉
        int intmatcher=Integer.parseInt(GetNumString);
        System.out.println(intmatcher+"字符:"+GetNumString);
        for(int backnum=1;backnum<=intmatcher;backnum++)
        {
            Driver.back();
            Driver.sleep(500);
        }
    }
    else if (Testingcase.getModel().equals("JavaScript"))
    {
        try{
        String javascriptcode=Testingcase.getModePath();
        //"var alpha = 30;var x=document.getElementsByClassName(\"hover-block\")[0];x.style.opacity = alpha ; alert(document.body.contains(document.getElementsByClassName(\"hover-block\")[0]));alert(x.style.opacity );"
        Driver.execute(javascriptcode);
        return 1;
    }catch (Exception e) {
            return 4;
        }

    }
    else if (Testingcase.getModel().equals("iframe切换"))
        {
         try{
            Driver.frame(Testingcase.getModePath());//切换到新iframe页面
            return 1;
        }catch (Exception e) {
            return 4;
        }
        }
    else if (Testingcase.getModel().equals("iframe切回"))
        {
            Driver.frame("");//切换回旧的页面
            return 1;
        }
    else if (Testingcase.getModel().equals("上传"))
        {
            if (DriverWaitingForGeneral.DriverWaite(Driver,Testingcase,T)>0) {
            Driver.elementByXPath(Testingcase.getModePath()).sendKeys(Testingcase.getText());
            return 1;
            }
            else{
                return DriverWaitingForGeneral.DriverWaite(Driver,Testingcase,T);
            }
        }
    else if (Testingcase.getModel().equals("上传文件夹"))
        {
            LookForphoto lookpath=new LookForphoto();
            File file = new File(Testingcase.getText());
            List<String> imgpath=new ArrayList<String>();
            imgpath=lookpath.printDirectory(file,lookpath.depth);
            if (DriverWaitingForGeneral.DriverWaite(Driver,Testingcase,T)>0) {
                for(int filesNum=0;filesNum<imgpath.size();filesNum++)
                {
                    Driver.elementByXPath(Testingcase.getModePath()).sendKeys(imgpath.get(filesNum));
                }
                return 1;
            }
            else{
                return DriverWaitingForGeneral.DriverWaite(Driver,Testingcase,T);
            }
        }
    else if (Testingcase.getModel().equals("数据库"))
        {
            int reasultInt=DatabaseExecute.InsertDatabaseSql(Testingcase.getModePath());
            System.out.print("Database Execute Return ：" + reasultInt+"\n");
            if(reasultInt>0)
            {
                return 7;
            }
            else{
                return 0;
            }
        }
    else if (Testingcase.getModel().equals("比较")) {
        if (!Aftertime.equals(beforetime)) {
            if (Testingcase.getText().equals("相等")) {
                EventListenerMonitoring.Listenerflag = 2;
                Assert.assertEquals("不相等", "相等", "前后值比较失败啦!初始值="+beforetime+" 比较值= "+Aftertime);
            }
            else{
                Reporter.log("前后比较成功 ,新获取文本值=" + Aftertime);//写入报告图片地址
            }
        }
        else {
            if (Testingcase.getText().equals("相等")) {
                Reporter.log("前后比较成功 ,新获取文本值=" + Aftertime);//写入报告图片地址
            }
            else{
                EventListenerMonitoring.Listenerflag = 2;
				Assert.assertEquals("相等", "不相等", "前后值比较失败啦!初始值="+beforetime+" 比较值= "+Aftertime);
            }
        }
        beforetime=Aftertime;
        return 1;
    }
    //该方法适用于页面输入get的输入url，并获取其页面上的返回值替换
    else if (Testingcase.getModel().equals("接口比较"))
        {
                String compare[] = Testingcase.getText().split("#");
                System.out.println(PostGet.YmTotalVariables.get(compare[0].trim())+compare[1].trim());
        try {
            if (compare[2].trim().equals("相等")) {
                if (!PostGet.YmTotalVariables.get(compare[0].trim()).equalsIgnoreCase(compare[1].trim())) {
                    EventListenerMonitoring.Listenerflag = 2;
                    return 11;
                }
            }
            else{
                if (PostGet.YmTotalVariables.get(compare[0].trim()).equalsIgnoreCase(compare[1].trim()) ) {
                    EventListenerMonitoring.Listenerflag = 2;
                    return 11;
                }
            }
        }catch (Exception e) {
            if (PostGet.YmTotalVariables.get(compare[0].trim()).equalsIgnoreCase(compare[1].trim())) {
                EventListenerMonitoring.Listenerflag = 2;
                return 11;
                }
            }
        return 10;
            }
        else if (Testingcase.getModel().contains("列表"))//针对元素定位会返回多个元素情况，指定操作某个元素
        {
            try{
                int count= Driver.countOfElements(GetElementWay.XPATH,Testingcase.getModePath());
                Element ElementOperations=Driver.getElement(GetElementWay.XPATH,Testingcase.getModePath(),Integer.parseInt(Testingcase.getText()));
                if(Testingcase.getModel().equals("点击列表"))
                {
                    ElementOperations.click();
                }
                else if(Testingcase.getModel().equals("输入列表"))
                {
                    ElementOperations.clearText();
                    ElementOperations.sendKeys(Testingcase.getText());
                }
            }catch (Exception e) {
                return 4;
            }
            return  9;
        }
    //用于ui执行自动化过程中，插入接口请求的步骤
    else if (Testingcase.getModel().equals("http")||Testingcase.getModel().equals("https"))
        {
            if(Testingcase.getContextInterfaceReturn().contains( "GetCookie" ))//获取cookie避免UI和接口同时登录互踢
            {
                String[] CookieContent= PostGet.YmhttpsRequest.GetCookiesSevenVersions(Testingcase.getModePath(),PostGet.YmContentType).split(";");
                Cookie=CookieContent[0];
            }
            String getAuthorizationPost=Testingcase.getAuthorization();
            if(!Testingcase.getAuthorization().equals("空"))
            {
                getAuthorizationPost = GetReturnContent.ReplaceContextBySub(Testingcase.getAuthorization(), PostGet.YmTotalVariables);
            }
            if(Testingcase.getMode().equalsIgnoreCase("post")|| Testingcase.getMode().equalsIgnoreCase("postform"))
            {
                //截取请求返回值
                if(Testingcase.getContextInterfaceReturn().contains( "return" ))
                {
                    PostGet.PostRetrun(Testingcase,Cookie,getAuthorizationPost);
                }
                else  if (Testingcase.getContextInterfaceReturn().contains( "replace" ))
                {
                    PostGet.PostReplace(Testingcase,Cookie,getAuthorizationPost);
                }
                else if(Testingcase.getContextInterfaceReturn().contains( "bothoperation" ))
                {
                    PostGet.PostBothoperation(Testingcase,Cookie,getAuthorizationPost);
                }
                //没有任何操作
                else
                {
                   PostGet.PostBase(Testingcase,Testingcase.getModePath(),Testingcase.getText(),Testingcase.getAppAuthentication(),Cookie,getAuthorizationPost);
                }
              }
            else if(Testingcase.getMode().equalsIgnoreCase("get"))
            {
                if(Testingcase.getContextInterfaceReturn().contains( "return" ))
                {
                    PostGet.GetRetrun(Testingcase,Cookie,getAuthorizationPost);
                }
                else  if (Testingcase.getContextInterfaceReturn().contains( "replace" )||Testingcase.getContextInterfaceReturn().contains( "replaceUIvisit" ))
                {
                    PostGet.GetReplace(Driver,Testingcase,Cookie,getAuthorizationPost);
                }
                else if(Testingcase.getContextInterfaceReturn().contains( "bothoperation" ))
                {
                    PostGet.GetBothoperation(Testingcase,Cookie,getAuthorizationPost);
                }
                //没有任何操作
                else
                    {
                        PostGet.GetBase(Testingcase,Testingcase.getModePath(),Testingcase.getAppAuthentication(),Cookie,getAuthorizationPost);
                    }
            }
            return 8;
        }
    //获取元素文本值
    else if (Testingcase.getModel().equals("查验")) {
        try{
            if (DriverWaitingForGeneral.DriverWaite(Driver,Testingcase,T)>0) {
                Returnbody = Driver.elementByXPath(Testingcase.getModePath()).getText();
            }
        if(Returnbody==null)
        {
            EventListenerMonitoring.Listenerflag=2;
        }
        if(Returnbody.contains("未完成")||Returnbody.contains("正在")||Returnbody.contains("未搜索")||Returnbody.contains("暂无"))
        {
            EventListenerMonitoring.Listenerflag=2;
            Assert.assertEquals("  "+ Returnbody +"  !  ","");
        }
        else if(Returnbody.contains("共"))//用于统计数量
        {
            Returnbody=Returnbody.substring( Returnbody.indexOf( "共" )+1,Returnbody.length()).trim();
            if(Returnbody.contains("条"))
            {
                Returnbody=Returnbody.substring(0,Returnbody.indexOf( "条" )).trim();
                System.out.println("数量= "+Returnbody);
            }
            return 3;
        }
        else
        {
            return 12;
        }
    }catch (Exception e) {
            return 4;
        }
    }
    else if(Testingcase.getModel().equals("多选"))
        {
            String[] RangeAndNum=null;
            RangeAndNum=Testingcase.getText().split( "/" );
            List random=new ArrayList();
            random= GetRandom.randomArr(RangeAndNum[0]);
            if(Testingcase.getModePath().contains("x"))
            {
                for(int n=0;n<Integer.parseInt(RangeAndNum[1]);n++) {
                    String NewPath = Testingcase.getModePath().replaceAll( "x", random.get( n ).toString() );
                    if (DriverWaitingForGeneral.DriverWaite(Driver,Testingcase,T)>0)
                    {
                        Driver.elementByXPath( NewPath ).click();
                    }
                }
            }
            return 1;
        }
        else if(Testingcase.getModel().equals("采集"))
        {
            try {
                if (DriverWaitingForGeneral.DriverWaite(Driver, Testingcase, T) > 0) {
                    JiraValue.add(Driver.elementByXPath(Testingcase.getModePath()).getText());
                }
            }
            catch (Exception e)
            {
                return 4;
            }
            return 13;
        }
        else if(Testingcase.getModel().equals("入库"))
        {
            String insertsql="";
            try {
                for(int num=0;num<=JiraValue.size();num=num+Integer.parseInt(Testingcase.getText()))
                {

                }
            }
            catch (Exception e)
            {
                return 4;
            }
            return 14;
        }
    else if (Testingcase.getModel().equals("等待")){
        int i = 0;
        do {
            Driver.sleep(1000);
            i++;
        }while (i <= T);
        return 1;
    }
        return 2;
    }
}


