package CollectionOfFunctionalMethods.BasicMethods;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import CollectionOfFunctionalMethods.ServerPortAndIdRelated.QueryMacacaSeverPort;
import CollectionOfFunctionalMethods.UseCaseReRunCorrelation.OverrideIAnnotationTransformer;
import CollectionOfFunctionalMethods.UseCaseReRunCorrelation.OverrideIReTry;
import CollectionOfFunctionalMethods.UseCaseReRunCorrelation.ReTryTimes;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
/**
 * 重写testng监听器三种测试结果的测试方法
 * @author wzb 2019/08/01
 */
//TestListenerAdapter 已经实现 ITestListener，并且提供了一些有用的方法，比如分别获取所有成功失败跳过三种测试结果的测试方法的方法
public class AssertionListener extends TestListenerAdapter {
    public  static String  port="";
    private int index = 0;
    @Override
    public void onTestStart(ITestResult result) {
        MyAssertion.flag = true;
        EventListenerMonitoring.Listenerflag=1;
        MyAssertion.errors.clear();
        port= QueryMacacaSeverPort.GetMacacaServerPort();
        System.out.println("Testng的监听器onTestStart！！端口号="+port);
    }
    @Override
    public void onTestFailure(ITestResult tr) {
        this.handleAssertion(tr);
        EventListenerMonitoring.EventListenerControl( tr );
        System.out.println("EventListenerMonitoring.Listenerflag= "+EventListenerMonitoring.Listenerflag);
        try {
            MailDelivery.TCTestCaseMailSending(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Testng的监听器onTestFailure！！");
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        this.handleAssertion(tr);
        EventListenerMonitoring.Listenerflag=3;
        EventListenerMonitoring.EventListenerControl( tr );
        System.out.println("Testng的监听器onTestSkipped！！");
    }
    @Override
    //监听器控制用例成功，停止重跑功能
    public void onTestSuccess(ITestResult tr) {
        this.handleAssertion(tr);
        ReTryTimes.maxReTryNum=0;//重置最大重跑次数
        ReTryTimes.initReTryNum=0;//重置初始重跑次数
        EventListenerMonitoring.Listenerflag=0;
        EventListenerMonitoring.EventListenerControl( tr );
        System.out.println("Testng的监听器onTestSuccess！！");
    }
    @Override
    //用于监听器完成测试用例后，过滤失败测试报告结果，只保留成功
    public void onFinish(ITestContext context) {
        System.out.println("Testng的监听器onTestFinish！！");
        Iterator<ITestResult> listOfFailedTests = context.getFailedTests().getAllResults().iterator();
        while (listOfFailedTests.hasNext()) {
            ITestResult failedTest = listOfFailedTests.next();//采集失败结果
            ITestNGMethod method = failedTest.getMethod();
            if (context.getFailedTests().getResults(method).size() > 1) {
                listOfFailedTests.remove();//移除失败的用例报告
            }
            else {
                if (context.getPassedTests().getResults(method).size() > 0) {
                    listOfFailedTests.remove();
                }
            }
        }
    }
    /**
     * 收集断言异常结果并输出，用于执行步骤错误可以继续执行动作
     * @author wzb
     */
    private void handleAssertion(ITestResult tr){
        if(!MyAssertion.flag){ //为假，就是断言出错了就执行下面的
            //获取异常
            Throwable throwable = tr.getThrowable();
            if(throwable==null){
                throwable = new Throwable();
            }
            //获取异常堆栈信息
            StackTraceElement[] traces = throwable.getStackTrace();
            //创建要输出的所有堆栈信息
            StackTraceElement[] alltrace = new StackTraceElement[0];
            //循环获取断言的异常信息，
            for (Error e : MyAssertion.errors) {
                //获取错误的堆栈数组信息
                StackTraceElement[] errorTraces = e.getStackTrace();
                StackTraceElement[] et = this.getKeyStackTrace(tr, errorTraces);
                //设置异常信息堆栈内容
                StackTraceElement[] message = new StackTraceElement[]{new StackTraceElement("Message : "+e.getMessage()+" Class Method : ", tr.getMethod().getMethodName(), tr.getTestClass().getRealClass().getSimpleName(), index)};
                //行号初始化为0
                index = 0;
                //堆栈信息合并
                alltrace = this.merge(alltrace, message);
                alltrace = this.merge(alltrace, et);
            }
            //如果异常信息不为空
            if(traces!=null){
                traces = this.getKeyStackTrace(tr, traces);
                alltrace = this.merge(alltrace, traces);
            }
            //保存异常信息
            throwable.setStackTrace(alltrace);
            tr.setThrowable(throwable);
            //清空错误信息和初始化标志
            MyAssertion.flag = true;
            MyAssertion.errors.clear();
            //输出异常信息
        }
    }

    private StackTraceElement[] getKeyStackTrace(ITestResult tr, StackTraceElement[] stackTraceElements){
        List<StackTraceElement> ets = new ArrayList<StackTraceElement>();
        //循环获取信息
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            //返回测试类的堆栈信息
            if(stackTraceElement.getClassName().equals(tr.getTestClass().getName())){
                ets.add(stackTraceElement);
                index = stackTraceElement.getLineNumber();//错误行号
            }
        }
        StackTraceElement[] et = new StackTraceElement[ets.size()];
        for (int i = 0; i < et.length; i++) {
            et[i] = ets.get(i);
        }
        return et;
    }
    /**
     * 合并两个堆栈信息
     * @param traces1 第一个
     * @param traces2
     * @author wzb
     */
    private StackTraceElement[] merge(StackTraceElement[] traces1, StackTraceElement[] traces2){
        StackTraceElement[] ste = new StackTraceElement[traces1.length+traces2.length];
        for (int i = 0; i < traces1.length; i++) {
            ste[i] = traces1[i];
        }
        for (int i = 0; i < traces2.length; i++) {
            ste[traces1.length+i] = traces2[i];
        }
        return ste;
    }
}
