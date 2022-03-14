package CollectionOfFunctionalMethods.BasicMethods;

import DingDingTalk.DingTalkCall;
import org.testng.ITestResult;

/**
 * @author 吴正彪本地
 */
public class EventListenerMonitoring {
    public static int Listenerflag ;
    public static void EventListenerControl(ITestResult tr){
        if(Listenerflag==2)//标志为2代表，监听器置为失败
        {
            tr.setStatus(ITestResult.FAILURE);
            try {
                MailDelivery.TCTestCaseMailSending(1);
                DingTalkCall.DingTalkCallMessageDesign("失败告警通知 ，"+" 操作时间: "+ GetCurrentSystemTime.GetCurrentTime()+" ！"+"\n"+UnifiedDomain.ReturnReportDomain(), 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(Listenerflag==3) //标志为3代表，监听器置为跳过
        {
            tr.setStatus(ITestResult.SKIP);
        }
        else
        {
            tr.setStatus(ITestResult.SUCCESS);
        }
        System.out.print( "标志位： "+Listenerflag+"\n" );
    }
}
