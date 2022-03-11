package DingDingTalk;
import java.util.HashMap;
import java.util.Map;
public class DingTalkCall {
    public static String MessageType = "";//用例区分
    public static void DingTalkCallMessageDesign(String message,int isNeedSendDing ) {
        // 钉钉的webhook
        String dingDingToken = "https://oapi.dingtalk.com/robot/send?access_token=5db7b83405a2b6850bd8bdd9749b77884b82828c9d7d48ada6fb6379b6a89cb1";
        // 请求的JSON数据，这里我用map在工具类里转成json格式
        Map<String, Object> json = new HashMap();
        Map<String, Object> text = new HashMap();
        json.put("msgtype", "text");
        if(MessageType.contains( "值班" ))
        {
            text.put("content", "《"+MessageType+"》"+message);
        }
        else if(MessageType.contains( "报表" ))
        {
            text.put("content", "《"+MessageType+"》"+message);
        }
        else if(MessageType.contains( "动态" ))
        {
            text.put("content","《"+MessageType+"》"+message);
        }
        else
        {
            text.put("content", "《"+MessageType+"》"+message);
     }
        json.put("text", text);
        // 发送post请求
        DingDingSendMessage.sendPostByMap(dingDingToken, json,isNeedSendDing);
    }
}
