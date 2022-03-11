package CollectionOfFunctionalMethods.BasicMethods;
import macaca.client.MacacaClient;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

/**
 * 查询用例执行过程中出现抛出的异常,并截图
 * @author wzb
 */
public class AbnormalScreenshot {
    //  返回0：异常没有找到
    //  返回1：找到异常了,并展开明细
    private   String AbnormalXpath = "//*[@id=\"detail__\"]";
    private   String AbnormalXpathDetail="//*[@ng-click=\"msg.showMeItem=!msg.showMeItem\"]";
    public static final String AbnormalXpathDetailGetText="//*[@class=\"message-content ng-binding ng-scope\"]";//获取详细内容
    public    String AbnormalDetailContent=null;
    JiraPicturesPath Jira=new JiraPicturesPath();
    public int WhetherCatchAbnormal(MacacaClient Driver,String img) throws Exception {
        int frequency=1;//查找次数
        String AbnormalclassPath="";
        try{
            if (Driver.waitForElementByXPath(AbnormalXpath)==null)
            {
                for (int i=1; i <= frequency; i++)//可以自定义等待区间时长
                {
                    Driver.sleep(1000);
                    if(Driver.waitForElementByXPath(AbnormalXpath)!=null)//隔一秒查找元素
                    {
                        System.out.println("等待了："+i+"秒,找到异常了!");
                        break;
                    }
                    if (i == frequency) {
                        return 0;//没找到
                    }
                }
            }
        }
        catch(NullPointerException e){
            String Abnormal="macaca对象 Driver="+Driver;
            MailDelivery.TCTestCaseMailSending(1);
            Assert.assertEquals(Abnormal,"初始化成功","原因:MacacaClient初始化失败,看下服务是否开启!!");
        }
        Driver.elementByXPath(AbnormalXpath).click();
        Driver.sleep(1000);
        Driver.elementByXPath(AbnormalXpathDetail).click();
        AbnormalDetailContent= Driver.elementByXPath(AbnormalXpathDetailGetText).getText();
        System.out.println("AbnormalXpathDetail 的定位："+AbnormalXpathDetail);
        Driver.sleep(1000);
        Driver.saveScreenshot(img);
        Jira.JiraPicturesSaveAndPath(Driver,img);
        return 1;
    }

}
