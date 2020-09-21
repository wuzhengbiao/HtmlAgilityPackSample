package Com;

import CollectionOfFunctionalMethods.GraphqlContext.GetReturnContent;
import CollectionOfFunctionalMethods.HttpAndHttpsProtocol.HttpRequestMethod;
import CollectionOfFunctionalMethods.HttpAndHttpsProtocol.HttpsRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzb on 2020/04/21.
 */
public class InterfaceExecute {
 private String ContentType="application/json";
    public Map<String,String> InterceptingVariables= new HashMap<String,String>();
    public Map<String,String> TotalVariables= new HashMap<String,String>();
    public  Object result;
    public  HttpRequestMethod httpRequest = new HttpRequestMethod();
    public  HttpsRequest httpsRequest = new HttpsRequest();
    public Object Interfaceexecute( TestingCase Testingcase) throws Exception {
        if (Testingcase.getModel().equalsIgnoreCase("http")){
            if(Testingcase.getMode().equalsIgnoreCase("post"))
            {
                //截取请求返回值
                if(Testingcase.getContextInterfaceReturn().contains( "return" ))
                {
                    result=httpRequest.SendPostString( Testingcase.getModePath(),Testingcase.getText(), Testingcase.getAppAuthentication(),ContentType,Testingcase.getAuthorization());
                    InterceptingVariables= GetReturnContent.GetReturnContentBySub( result.toString(),"#",Testingcase.getContextInterfaceReturn() );
                    TotalVariables.putAll( InterceptingVariables );
                }
                //替换请求参数变量值
                else  if (Testingcase.getContextInterfaceReturn().contains( "replace" ))
                {
                    String getModePath=GetReturnContent.ReplaceContextBySub( Testingcase.getModePath(),TotalVariables );
                    String getTextPost=GetReturnContent.ReplaceContextBySub( Testingcase.getText(),TotalVariables );
                    result=httpRequest.SendPostString(getModePath,getTextPost, Testingcase.getAppAuthentication(),ContentType,Testingcase.getAuthorization());
                }
                //截取请求返回值且替换请求参数值
                else if(Testingcase.getContextInterfaceReturn().contains( "double" ))
                {
                    String getModePath=GetReturnContent.ReplaceContextBySub( Testingcase.getModePath(),TotalVariables );
                    String getTextPost=GetReturnContent.ReplaceContextBySub( Testingcase.getText(),TotalVariables );
                    result=httpRequest.SendPostString( getModePath,getTextPost, Testingcase.getAppAuthentication(),ContentType,Testingcase.getAuthorization());
                    InterceptingVariables= GetReturnContent.GetReturnContentBySub( result.toString(),"#",Testingcase.getContextInterfaceReturn() );
                    TotalVariables.putAll( InterceptingVariables );
                }
                //没有任何操作
                else {
                    result=httpRequest.SendPostString( Testingcase.getModePath(),Testingcase.getText(), Testingcase.getAppAuthentication(),ContentType,Testingcase.getAuthorization());
                }
                //设置基础变量值
                if (!Testingcase.getCommonVariable().contains( "空" ))
            {
                InterceptingVariables=GetReturnContent.GetCommonVariable( "#",Testingcase.getCommonVariable() );
                TotalVariables.putAll( InterceptingVariables );
            }
            }
            else if(Testingcase.getMode().equalsIgnoreCase("get"))
            {
                if(Testingcase.getContextInterfaceReturn().contains( "return" ))
                {
                    result=httpRequest.SendGetNullBody( Testingcase.getModePath(), Testingcase.getAppAuthentication(), ContentType,Testingcase.getAuthorization());
                    InterceptingVariables= GetReturnContent.GetReturnContentBySub( result.toString(),"#",Testingcase.getContextInterfaceReturn() );
                    TotalVariables.putAll( InterceptingVariables );
                }
                else {
                    result=httpRequest.SendGetNullBody( Testingcase.getModePath(), Testingcase.getAppAuthentication(), ContentType,Testingcase.getAuthorization());
                }
            }
        }
        else {
            if(Testingcase.getMode().equalsIgnoreCase("post"))
            {
                if(Testingcase.getContextInterfaceReturn().contains( "return" ))
                {
                    result=httpsRequest.SendPosts( Testingcase.getModePath(),Testingcase.getText(), Testingcase.getAppAuthentication(),ContentType,Testingcase.getAuthorization());
                    InterceptingVariables= GetReturnContent.GetReturnContentBySub( result.toString(),"#",Testingcase.getContextInterfaceReturn() );
                    TotalVariables.putAll( InterceptingVariables );
                }
                else  if (Testingcase.getContextInterfaceReturn().contains( "replace" ))
                {
                    String getModePath=GetReturnContent.ReplaceContextBySub( Testingcase.getModePath(),TotalVariables );
                    String getTextPost=GetReturnContent.ReplaceContextBySub( Testingcase.getText(),TotalVariables );
                    result=httpsRequest.SendPosts(getModePath,getTextPost, Testingcase.getAppAuthentication(),ContentType,Testingcase.getAuthorization());
                }
                else if(Testingcase.getContextInterfaceReturn().contains( "double" ))
                {
                    String getModePath=GetReturnContent.ReplaceContextBySub( Testingcase.getModePath(),TotalVariables );
                    String getTextPost=GetReturnContent.ReplaceContextBySub( Testingcase.getText(),TotalVariables );
                    result=httpsRequest.SendPosts( getModePath,getTextPost, Testingcase.getAppAuthentication(),ContentType,Testingcase.getAuthorization());
                    InterceptingVariables= GetReturnContent.GetReturnContentBySub( result.toString(),"#",Testingcase.getContextInterfaceReturn() );
                    TotalVariables.putAll( InterceptingVariables );
                }
                else {
                    result=httpsRequest.SendPosts( Testingcase.getModePath(),Testingcase.getText(), Testingcase.getAppAuthentication(),ContentType,Testingcase.getAuthorization());
                }
            }
        }
        return result;
    }
}
