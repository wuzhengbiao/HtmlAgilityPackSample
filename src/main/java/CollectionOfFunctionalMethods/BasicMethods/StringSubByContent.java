package CollectionOfFunctionalMethods.BasicMethods;

import java.io.IOException;

public class StringSubByContent {
    /**
     *
     * @param buffer  要切割的字符串
     * @param LB   左边要切割内容，要保证截是独一无二的
     * @param RB   右边要切割内容,要保证截是独一无二的
     * @param skip  是否跳过截取，1是截取 0是不截取
     * @return  返回截取的内容
     * @throws IOException
     */
    public static String SubByContentLBorRB(String buffer,String LB,String RB,int skip) throws IOException {
        String context = "";
        if(skip==1)
        {
            context=buffer.substring(0,buffer.indexOf(LB));
            context=buffer.substring(context.length()+LB.length(),buffer.length());
            context=context.substring(0,context.indexOf(RB));
            System.out.println("截取获得值RB:"+context+"\n");
        }
        else
        {
            context=buffer;
            System.out.println("请求返回值 跳过:"+context+"\n");
        }
        return context;
    }
    public static String ReplaceByContentLBorRB(String buffer,String LB,String RB,String needcontext,int skip) throws IOException {
        String Replacecontext = "";
        String Replacecontextstart = "";
        String ReplacecontextLast = "";
        int startlenth=0;
        int endlenth=0;
        if(skip==1)
        {
            Replacecontextstart=buffer.substring(0, buffer.indexOf(RB));
            endlenth=Replacecontextstart.length();
            Replacecontext=Replacecontextstart.substring(buffer.indexOf(LB)+LB.length());
            startlenth=Replacecontextstart.length()-Replacecontext.length();
            ReplacecontextLast=buffer.replace(buffer.subSequence(startlenth,endlenth),needcontext);
            System.out.println("最终替换:"+ReplacecontextLast+"\n");
        }
        else
        {
            ReplacecontextLast=buffer;
            System.out.println("请求返回值 跳过:"+ReplacecontextLast+"\n");
        }
        return ReplacecontextLast;
    }
}
