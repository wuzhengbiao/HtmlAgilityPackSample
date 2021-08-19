package CollectionOfFunctionalMethods.HttpAndHttpsProtocol;

import CollectionOfFunctionalMethods.GraphqlContext.GetReturnContent;
import Com.TestingCase;
import macaca.client.MacacaClient;
import java.util.HashMap;
import java.util.Map;

public class PostGetGeneralMethod {
    public Map<String, String> YmInterceptingVariables = new HashMap<String, String>();
    public Map<String, String> YmTotalVariables = new HashMap<String, String>();
    public Object Ymresult;
    public HttpRequestMethod YmhttpRequest = new HttpRequestMethod();
    public HttpsRequest YmhttpsRequest = new HttpsRequest();
    public String YmContentType = "application/json";
    public String YmContentFormType = "application/x-www-form-urlencoded";
    //请求的http和https调用post基础用法
    public  void  PostBase(TestingCase Testingcase,String getModePath,String getTextPost,String getAppAuthentication ,String Cookie,String getAuthorizationPost)throws Exception {
        if (Testingcase.getModel().equals("http")) {
            if (Testingcase.getAppAuthentication().contains("空") && Testingcase.getAuthorization().contains("空")) {
                Ymresult = YmhttpRequest.SendPostStringSevernVersions(getModePath, getTextPost, YmContentType, Cookie);
            } else {
                Ymresult = YmhttpRequest.SendPostString(getModePath, getTextPost, getAppAuthentication, YmContentType, getAuthorizationPost);
            }
        } else {
            if (getAppAuthentication.contains("空") && getAppAuthentication.contains("空")) {
                Ymresult = YmhttpsRequest.SendPostsSevenVersions(getModePath, getTextPost, YmContentType,Cookie);
            } else {
                if(Testingcase.getMode().equalsIgnoreCase("postform"))
                {
                    Ymresult = YmhttpsRequest.SendPosts(getModePath,getTextPost,getAppAuthentication,YmContentFormType,getAuthorizationPost);
                }
                else{
                    Ymresult = YmhttpsRequest.SendPosts(getModePath, getTextPost, getAppAuthentication, YmContentType, getAuthorizationPost);
                }
            }
        }
    }
    //请求的http和https调用get基础用法
    public  void  GetBase(TestingCase Testingcase,String getModePath,String getAppAuthentication ,String Cookie,String getAuthorizationPost)throws Exception {
        if(Testingcase.getModel().equals("http"))
        {
            if(getAppAuthentication.contains("空")&&getAuthorizationPost.contains("空"))
            {
                Ymresult = YmhttpRequest.SendGetNullBodySevenVersion(getModePath,YmContentType,Cookie);
            }
            else{
                Ymresult = YmhttpRequest.SendGetNullBody(getModePath,getAppAuthentication, YmContentType, getAuthorizationPost);
            }
        }
        else {
            if(getAppAuthentication.contains("空")&&getAuthorizationPost.contains("空"))
            {
                Ymresult = YmhttpsRequest.SendGetSNullBodySevenVersions(getModePath,YmContentType,Cookie);
            }
            else
            {
                Ymresult = YmhttpsRequest.SendGetSNullBody(getModePath, YmContentType, getAppAuthentication,getAuthorizationPost);
            }
        }
    }

    //返回值截取post
    public void PostRetrun(TestingCase Testingcase,String Cookie,String getAuthorizationPost) throws Exception {
        {
                this.PostBase(Testingcase,Testingcase.getModePath(),Testingcase.getText(),Testingcase.getAppAuthentication(),Cookie,getAuthorizationPost);
                YmInterceptingVariables = GetReturnContent.GetReturnContentBySub(Ymresult.toString(), "#", Testingcase.getContextInterfaceReturn());
                YmTotalVariables.putAll(YmInterceptingVariables);
        }
    }

    //替换text和url的参数值post
    public void PostReplace(TestingCase Testingcase,String Cookie,String getAuthorizationPost) throws Exception {
        String getModePath=GetReturnContent.ReplaceContextBySub( Testingcase.getModePath(),YmTotalVariables );
        String getTextPost=Testingcase.getText();
        if(!Testingcase.getText().equals("空"))
        {
            getTextPost=GetReturnContent.ReplaceContextBySub( Testingcase.getText(),YmTotalVariables );
            System.out.println("替换后的text： "+getTextPost+"\n");
        }
        this.PostBase(Testingcase,getModePath,getTextPost, Testingcase.getAppAuthentication(),Cookie,getAuthorizationPost);
    }

    //既替换text和url的参数值，又截取接口请求返回值post
    public void PostBothoperation(TestingCase Testingcase,String Cookie,String getAuthorizationPost) throws Exception {
        String getModePath=GetReturnContent.ReplaceContextBySub( Testingcase.getModePath(),YmTotalVariables );
        String getTextPost=Testingcase.getText();
        if(!Testingcase.getText().equals("空"))
        {
            getTextPost = GetReturnContent.ReplaceContextBySub(Testingcase.getText(), YmTotalVariables);
            System.out.println("替换后的text： "+getTextPost+"\n");
        }
        this.PostBase(Testingcase,getModePath,getTextPost, Testingcase.getAppAuthentication(),Cookie,getAuthorizationPost);
        YmInterceptingVariables= GetReturnContent.GetReturnContentBySub( Ymresult.toString(),"#",Testingcase.getContextInterfaceReturn() );
        YmTotalVariables.putAll( YmInterceptingVariables );
    }

    //返回值截取get
    public void GetRetrun(TestingCase Testingcase,String Cookie,String getAuthorizationPost) throws Exception
    {
        this.GetBase(Testingcase,Testingcase.getModePath(),Testingcase.getAppAuthentication(),Cookie,getAuthorizationPost);
        YmInterceptingVariables= GetReturnContent.GetReturnContentBySub( Ymresult.toString(),"#",Testingcase.getContextInterfaceReturn() );
        YmTotalVariables.putAll( YmInterceptingVariables );
    }

  //替换text和url的参数值get
  public void GetReplace(MacacaClient Driver, TestingCase Testingcase, String Cookie, String getAuthorizationPost) throws Exception
  {
      String getModePath=GetReturnContent.ReplaceContextBySub( Testingcase.getModePath(),YmTotalVariables );
      if(Testingcase.getModel().equals("http"))
      {
          if(Testingcase.getAppAuthentication().contains("空")&&Testingcase.getAuthorization().contains("空"))
          {
              if(Testingcase.getContextInterfaceReturn().contains( "replaceUIvisit" ))
              {
                  Driver.get(getModePath);
                  Ymresult = YmhttpRequest.SendGetNullBodySevenVersion(getModePath,YmContentType,Cookie);
              }
              else{
                  Ymresult = YmhttpRequest.SendGetNullBodySevenVersion(getModePath,YmContentType,Cookie);
              }
          }
          else{
              Ymresult = YmhttpRequest.SendGetNullBody(getModePath, Testingcase.getAppAuthentication(), YmContentType, getAuthorizationPost);
          }
      }
      else {
          if (Testingcase.getAppAuthentication().contains("空") && Testingcase.getAuthorization().contains("空"))
          {
              if(Testingcase.getContextInterfaceReturn().contains( "replaceUIvisit" ))//判断是否要同时接口和UI访问登录
              {
                  Driver.get(getModePath);
                  Ymresult = YmhttpsRequest.SendGetSNullBodySevenVersions(getModePath,YmContentType,Cookie);
              }
              else{
                  Ymresult = YmhttpsRequest.SendGetSNullBodySevenVersions(getModePath,YmContentType,Cookie);
              }
          }
          else {
              Ymresult = YmhttpsRequest.SendGetSNullBody(getModePath, YmContentType, Testingcase.getAppAuthentication(),getAuthorizationPost);
          }
      }
  }
    //既替换text和url的参数值，又截取接口请求返回值get
    public void GetBothoperation(TestingCase Testingcase,String Cookie,String getAuthorizationPost) throws Exception {
        String getModePath=GetReturnContent.ReplaceContextBySub( Testingcase.getModePath(),YmTotalVariables );
        this.GetBase(Testingcase,getModePath,Testingcase.getAppAuthentication(),Cookie,getAuthorizationPost);
        YmInterceptingVariables= GetReturnContent.GetReturnContentBySub( Ymresult.toString(),"#",Testingcase.getContextInterfaceReturn() );
        YmTotalVariables.putAll( YmInterceptingVariables );
    }

    }

