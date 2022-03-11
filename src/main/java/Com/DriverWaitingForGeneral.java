package Com;

import macaca.client.MacacaClient;

public class DriverWaitingForGeneral {
    public static int  DriverWaite(MacacaClient Driver, TestingCase Testingcase, int T)throws Exception {
        if (Driver.waitForElementByXPath(Testingcase.getModePath()) == null) {
            System.out.println("找了四遍没有找到，开始等待！\n");
            for (int i = 1; i <= T; i++)//可以自定义等待区间时长
            {
                Driver.sleep(1000);
                if (Driver.waitForElementByXPath(Testingcase.getModePath()) != null)//隔一秒查找元素
                {
                    System.out.println("等待了：" + i + "秒,找到元素了");
                    break;
                }
                if (i == T) {
                    return 0;
                }
            }
        }
        return 1;
    }
}
