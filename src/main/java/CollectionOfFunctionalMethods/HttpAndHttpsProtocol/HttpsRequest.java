package CollectionOfFunctionalMethods.HttpAndHttpsProtocol;
import CollectionOfFunctionalMethods.BasicMethods.EventListenerMonitoring;
import CollectionOfFunctionalMethods.BasicMethods.StringSubByContent;
import CollectionOfFunctionalMethods.GraphqlContext.GetReturnContent;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * [author]:Wuzb  <br/>
 * [Email]:942189908@qq.com <br/>
 * [Date]:2020/3/04  <br/>
 * [Description]:  <br/>
 *
 * @author wuzb
 */
public class HttpsRequest {
    public static String  HttpSStatusFlag="";
    private static final int SUCCESS_CODE = 200;
    public static CloseableHttpClient createHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial( null, (chain, authType) -> true )
                .build();
        SSLConnectionSocketFactory sslSf = new SSLConnectionSocketFactory( sslcontext, null, null,
                new NoopHostnameVerifier() );
        return HttpClients.custom().setSSLSocketFactory( sslSf ).build();
    }
    /**
     * 发送GET请求body是字符串
     * @param url
     * @return JSON或者字符串
     * @throws Exception
     */
    public  String GetCookiesSevenVersions(String url,String Conten_type) throws Exception {
        HttpSStatusFlag="";
        CloseableHttpClient client = null;
        CloseableHttpResponse Getresponse = null;
        try {
            /**
             *  创建一个httpclient对象
             */
            client = createHttpClient();
            /**
             * 创建一个get对象
             */
            HttpGet get = new HttpGet( url.trim() );
            /**
             * 设置请求的报文头部的编码
             */
            get.setHeader( new BasicHeader( "Content-Type",  Conten_type+";charset=utf-8" ) );
            /**
             * 设置请求的报文头部授权和格式
             */
            get.setHeader( new BasicHeader( "accept", "application/json, text/plain, */*" ) );
            /**
             * 执行post请求
             */
            Getresponse = client.execute( get );
              //获取response中的cookie值
                String cookie = "";
                String value = null;
                Header[] headers = Getresponse.getHeaders("Set-Cookie");
                for (int i = 0; i < headers.length; i++) {//取出所有的cookie值
                    value = headers[i].getValue();
                    System.out.println("第"+i+"次的值为："+value);
                    cookie+=value;
                }
                return headers[0].getValue();

        } catch (Exception e) {
            HttpSStatusFlag="0";
            EventListenerMonitoring.Listenerflag=2;
            return "get请求异常,检查一下请求参数！";
        } finally {
            Getresponse.close();
            client.close();
        }
    }
    /**
     * 发送GET请求body是字符串
     * @param url
     * @return JSON或者字符串
     * @throws Exception
     */
    public  Object SendGetSNullBody(String url,String Conten_type,String AppAuthentication,String Authorization) throws Exception {
        HttpSStatusFlag="";
        CloseableHttpClient client = null;
        CloseableHttpResponse Getresponse = null;
        try {
            /**
             *  创建一个httpclient对象
             */
            client = createHttpClient();
            /**
             * 创建一个get对象
             */
            HttpGet get = new HttpGet( url.trim() );
            /**
             * 设置请求的报文头部的编码
             */
            get.setHeader( new BasicHeader( "Content-Type",  Conten_type+";charset=utf-8" ) );
            get.setHeader( new BasicHeader("App-Authentication",AppAuthentication.trim()) );
            get.setHeader( new BasicHeader("Authorization",Authorization.trim()) );
            /**
             * 设置请求的报文头部授权和格式
             */
            get.setHeader( new BasicHeader( "accept", "application/json, text/plain, */*" ) );
            /**
             * 执行post请求
             */
            Getresponse = client.execute( get );
            /**
             * 获取响应码
             */
            int statusCode = Getresponse.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode||statusCode ==400) {//临时重定向状态码
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString( Getresponse.getEntity(), "UTF-8" );
                    HttpSStatusFlag="1";//成功标记
                    System.out.print( "Get请求的响应结果: " + result + "\n" );
                    return result;
            } else {
                System.out.print( "get请求失败！" + "\n" );
                EventListenerMonitoring.Listenerflag=2;
                HttpSStatusFlag="0";//失败标记
                return  Getresponse;
            }
        } catch (Exception e) {
            System.out.print( "get请求异常！" + "\n" );
            HttpSStatusFlag="0";
            EventListenerMonitoring.Listenerflag=2;
            return Getresponse;
        } finally {
            Getresponse.close();
            client.close();
        }
    }
    /**
     * 发送GET请求body是字符串
     * @param url
     * @return JSON或者字符串
     * @throws Exception
     */
    public  Object SendGetSNullBodySevenVersions(String url,String Conten_type,String Cookie) throws Exception {
        HttpSStatusFlag="";
        CloseableHttpClient client = null;
        CloseableHttpResponse Getresponse = null;
        try {
            /**
             *  创建一个httpclient对象
             */
            client = createHttpClient();
            /**
             * 创建一个get对象
             */
            HttpGet get = new HttpGet( url.trim() );
            /**
             * 设置请求的报文头部的编码
             */
            get.setHeader( new BasicHeader( "Content-Type",  Conten_type+";charset=utf-8" ) );
            get.setHeader( new BasicHeader( "Cookie",  Cookie ) );
            /**
             * 设置请求的报文头部授权和格式
             */
            get.setHeader( new BasicHeader( "accept", "application/json, text/plain, */*" ) );
            /**
             * 执行post请求
             */
            Getresponse = client.execute( get );
            /**
             * 获取响应码
             */
            int statusCode = Getresponse.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode||statusCode ==400) {//临时重定向状态码
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString(Getresponse.getEntity(), "UTF-8");
                System.out.print("Get请求的成功响应结果: " + result + "\n");
                HttpSStatusFlag = "1";//成功标记
                return result;
            } else {
                System.out.print( "get请求失败！" + "\n" );
                EventListenerMonitoring.Listenerflag=2;
                HttpSStatusFlag="0";//失败标记
                System.out.print("Get请求的失败响应结果: " + Getresponse + "\n");
                return  Getresponse;
            }
        } catch (Exception e) {
            System.out.print( "get请求异常！" + "\n" );
            HttpSStatusFlag="0";
            EventListenerMonitoring.Listenerflag=2;
            return Getresponse;
        } finally {
            Getresponse.close();
            client.close();
        }
    }
    /**
     * 发送GET请求
     * @throws Exception
     */
    public  Object SendGetS(String url,List<NameValuePair> nameValuePairList,String Conten_type) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpSStatusFlag="";
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            /**
             * 创建HttpClient对象
             */
            client =createHttpClient();
            /**
             * 创建URIBuilder
             */
            URIBuilder uriBuilder = new URIBuilder( url );
            /**
             * 设置参数
             */
            uriBuilder.addParameters( nameValuePairList );
            /**
             * 创建HttpGet
             */
            HttpGet httpGet = new HttpGet( uriBuilder.build() );
            /**
             * 设置请求头部编码
             */
            httpGet.setHeader( new BasicHeader( "Content-Type", Conten_type+"; charset=utf-8" ) );
            /**
             * 设置返回编码
             */
            httpGet.setHeader( new BasicHeader( "Accept", "text/plain;charset=utf-8" ) );
            /**
             * 请求服务
             */
            response = client.execute( httpGet );
            /**
             * 获取响应吗
             */
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode||statusCode ==400) {//临时重定向状态码
                /**
                 * 获取返回对象
                 */
                HttpEntity entity = response.getEntity();
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString( entity, "UTF-8" );
                System.out.print( "GETS请求结果响应:" + result + "\n" );
                /**
                 * 转换成json,根据合法性返回json或者字符串
                 */
                try {
                    jsonObject = JSONObject.parseObject( result );
                    return jsonObject;
                } catch (Exception e) {
                    return result;
                }
            } else {
                System.out.print( "GETS请求失败！" + "\n" );
            }
        } catch (Exception e) {
            System.out.print( "GETS请求异常！" + "\n" );
        } finally {
            response.close();
            client.close();
        }
        return null;
    }
    /**
     * 发送POST请求，除表单外
     *
     * @param url
     * @param json
     * @return JSON或者字符串
     * @throws Exception
     */
    //请求头为非josn的
    public  Object SendPosts(String url, String json,String AppAuthentication,String Conten_type,String Authorization) throws Exception {
        HttpSStatusFlag="";
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            /**
             *  创建一个httpclient对象
             */
            client =createHttpClient();
            /**
             * 创建一个post对象
             */
            HttpPost post = new HttpPost( url );
            /**
             * 包装成一个Entity对象
             */
                StringEntity entity = new StringEntity( json, "UTF-8" );
                post.setEntity( entity );
            /**
             * 设置请求的报文头部的编码
             */
            post.setHeader( new BasicHeader( "Content-Type",  Conten_type+";charset=utf-8" ) );
            /**
             * 设置请求的报文头部授权和格式
             */
            post.setHeader( new BasicHeader("App-Authentication",AppAuthentication.trim()) );
            post.setHeader( new BasicHeader("Authorization",Authorization.trim()) );
            post.setHeader( new BasicHeader( "accept", "application/json, text/plain, */*" ) );
          //  post.setHeader( new BasicHeader( "Referer", json.trim()) );
            /**
             ** 执行post请求
             */
            response = client.execute( post );
            /**
             * 获取响应码
             */
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.print( "POSTS的code:" + statusCode + "\n" );
            if (SUCCESS_CODE == statusCode||statusCode ==400) {//临时重定向状态码
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString( response.getEntity(), "UTF-8" );
                System.out.print( "POSTS请求结果响应: " + result + "\n" );
                if(StringSubByContent.SubByContentLBorRB(result,"\"code\":",",",1).equals("200")||StringSubByContent.SubByContentLBorRB(result,"\"code\":",",",1).equals("0")||StringSubByContent.SubByContentLBorRB(result,"\"code\":",",",1).equals("603"))
                {
                    HttpSStatusFlag="1";//成功标记
                }
                else{
                    HttpSStatusFlag="0";//失败标记
                }
                return result;
            }
            else{
                System.out.print( "POSTS请求失败！" + "\n" );
                HttpSStatusFlag="0";//失败标记
                EventListenerMonitoring.Listenerflag=2;
                return  response;
            }
        } catch (Exception e) {
            System.out.print( "POSTS请求异常！" + "\n" );
            HttpSStatusFlag="0";
            EventListenerMonitoring.Listenerflag=2;
            return response;
        } finally {
            response.close();
            client.close();
        }
    }
    /**
     * 发送POST请求，除表单外
     *
     * @param url
     * @param json
     * @return JSON或者字符串
     * @throws Exception
     */
    //请求头为非josn的
    public  Object SendPostsSevenVersions(String url, String json,String Conten_type,String Cookie) throws Exception {
        HttpSStatusFlag="";
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            /**
             *  创建一个httpclient对象
             */
            client =createHttpClient();
            /**
             * 创建一个post对象
             */
            HttpPost post = new HttpPost( url );
            /**
             * 包装成一个Entity对象
             */
            StringEntity entity = new StringEntity( json, "UTF-8" );
            post.setEntity( entity );
            /**
             * 设置请求的报文头部的编码
             */
            post.setHeader( new BasicHeader( "Content-Type",  Conten_type+";charset=utf-8" ) );
            post.setHeader( new BasicHeader( "Cookie",  Cookie ) );
            /**
             * 设置请求的报文头部授权和格式
             */
            post.setHeader( new BasicHeader( "accept", "application/json, text/plain, */*" ) );
            //  post.setHeader( new BasicHeader( "Referer", json.trim()) );
            /**
             ** 执行post请求
             */
            response = client.execute( post );
            /**
             * 获取响应码
             */
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.print( "POSTS的code:" + statusCode + "\n" );
            if (SUCCESS_CODE == statusCode||statusCode ==400) {//临时重定向状态码
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString( response.getEntity(), "UTF-8" );
                System.out.print( "POSTS请求结果响应: " + StringSubByContent.SubByContentLBorRB(result,"\"code\":","\"",1) + "\n" );
                if(StringSubByContent.SubByContentLBorRB(result,"\"code\":",",",1).equals("200")||StringSubByContent.SubByContentLBorRB(result,"\"code\":",",",1).equals("0")||StringSubByContent.SubByContentLBorRB(result,"\"code\":",",",1).equals("603"))
                {
                    HttpSStatusFlag="1";//成功标记
                }
                else{
                    HttpSStatusFlag="0";//失败标记
                }
                return result;
            }
            else{
                System.out.print( "POSTS请求失败！" + "\n" );
                HttpSStatusFlag="0";//失败标记
                EventListenerMonitoring.Listenerflag=2;
                return  response;
            }
        } catch (Exception e) {
            System.out.print( "POSTS请求异常！" + "\n" );
            HttpSStatusFlag="0";
            EventListenerMonitoring.Listenerflag=2;
            return response;
        } finally {
            response.close();
            client.close();
        }
    }
}
