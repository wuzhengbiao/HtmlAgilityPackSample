package CollectionOfFunctionalMethods.GraphqlContext;

import CollectionOfFunctionalMethods.BasicMethods.StringSubByContent;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class GetReturnContent {
    /**
     * auth  wzb
     * @param ReturnInterface  返回结果
     * @param flag  分割标志
     * @param context  按分割标志填写的变量名和分割标志
     * @return 返回被切割后的map值
     * @throws IOException
     */
    public static Map<String,String> GetReturnContentBySub(String ReturnInterface,String flag,String context) throws IOException {
        String NeedContext="";
        Map<String,String> map = new HashMap<String,String>();
        String[] strKeyValueSub=context.split(flag);
        for(int num=1;num<strKeyValueSub.length;num+=3)
        {
            NeedContext=StringSubByContent.SubByContentLBorRB(ReturnInterface,strKeyValueSub[num+1].trim(),strKeyValueSub[num+2].trim(),1);
            map.put( strKeyValueSub[num], ""+NeedContext+"");
        }
        return  map;
    }
    /**
     * auth  wzb
     * @param flag  切割标志
     * @param context   切割的标志和变量
     * @return 返回切割后的基础变量map值
     * @throws IOException
     * Create 2020.8.28 11:56
     * auth wuzb
     */
    public static Map<String,String> GetCommonVariable(String flag,String context) throws IOException {
        Map<String,String> map = new HashMap<String,String>();
        String[] strKeyValueSub=context.split(flag);
        for(int num=1;num<strKeyValueSub.length;num+=2)
        {
              map.put( strKeyValueSub[num], strKeyValueSub[num+1]);
            System.out.print( "切割的key=： "+ strKeyValueSub[num] +"    value="+strKeyValueSub[num+1]+"\n");
        }
        return  map;
    }
    /**
     *
     * @param NeedReplaceContext  需要被替换的内容
     * @param ReplaceMap   替换的值
     * @return  返回被替换后的内容
     * Create 2020.2.28 11:56
     * auth wuzb
     */
    public static String ReplaceContextBySub(String NeedReplaceContext,Map<String,String> ReplaceMap)
    {
        String ReturnContent="";
        for(String key : ReplaceMap.keySet())
        {
            ReturnContent=NeedReplaceContext.replace( key,ReplaceMap.get( key ) ).trim();
            NeedReplaceContext=ReturnContent;
            System.out.print( "替换内容地址是： "+ ReturnContent+"\n");
      //      System.out.print( "当前替换的key值="+ key+"    替换的value值= "+ReplaceMap.get( key )+"\n");
        }
        return ReturnContent;
    }

}
