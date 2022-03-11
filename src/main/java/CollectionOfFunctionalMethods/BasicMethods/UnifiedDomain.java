package CollectionOfFunctionalMethods.BasicMethods;

import DingDingTalk.DingTalkCall;

public class UnifiedDomain {
    public static String reportDomain="";
    public static String ReturnReportDomain()
    {
        if(DingTalkCall.MessageType.contains("重点项目"))
        {
            reportDomain="http://macaca.fjchjlan.59iedu.com:1457/History/TheReportXWDataPrepare/surefire-reports/html/index.html";
        }
        else if(DingTalkCall.MessageType.contains("核心用例"))
        {
            reportDomain= "http://macaca.fjchjlan.59iedu.com:1457/History/TheReport/surefire-reports/html/index.html";
        }
        else if(DingTalkCall.MessageType.contains("准备项目"))
        {
            reportDomain= "http://macaca.fjchjlan.59iedu.com:1457/History/TheReportDataPrepare/surefire-reports/html/index.html";
        }
        else if(DingTalkCall.MessageType.contains("值班"))
        {
            reportDomain= "http://macaca.fjchjlan.59iedu.com:1457/History/TheReport_chelist/surefire-reports/html/index.html";
        }
        else if(DingTalkCall.MessageType.contains("报表"))
        {
            reportDomain= "http://macaca.fjchjlan.59iedu.com:1457/History/TheReport_pt/surefire-reports/html/index.html";
        }
        else if(DingTalkCall.MessageType.contains("动态"))
        {
            reportDomain= "http://macaca.fjchjlan.59iedu.com:1457/History/TheReportTMP/surefire-reports/html/index.html";
        }
        else
        {
            reportDomain= "http://macaca.fjchjlan.59iedu.com:1457/History/TheReportJira/surefire-reports/html/index.html";
        }
        return reportDomain;
    }
}
