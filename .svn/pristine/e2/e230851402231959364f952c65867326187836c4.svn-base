package CollectionOfFunctionalMethods.BasicMethods;

import macaca.client.MacacaClient;
import org.apache.commons.lang3.StringUtils;

public class JiraPicturesPath {
    public   String jiraImg="";//jira图片
    public   String jiraImgFilePath="";//jira图片路径
    public void JiraPicturesSaveAndPath(MacacaClient Driver, String img) throws Exception {
        {
            jiraImg = img.replace("jpg", ".jpg");
            jiraImg = jiraImg.replace(".\\", "");
            String AbnormalclassPath = this.getClass().getResource("").getPath();
            //包含图片的具体路径
            String Subsfilepath = StringUtils.substringBefore(AbnormalclassPath, "/target") + "\\AbnormalPitures" + "\\" + jiraImg;
            jiraImgFilePath = java.net.URLDecoder.decode(StringUtils.substringBefore(AbnormalclassPath, "/target") + "\\AbnormalPitures", "utf-8");
            //保存图片的路径
            Driver.saveScreenshot(java.net.URLDecoder.decode(Subsfilepath, "utf-8"));
        }
    }
}
