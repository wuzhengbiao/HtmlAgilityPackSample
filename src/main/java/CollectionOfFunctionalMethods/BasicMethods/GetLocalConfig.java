package CollectionOfFunctionalMethods.BasicMethods;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class GetLocalConfig {
    /**
     * 读取文件内容
     **/
    public String ReadConfigFile(String filename) throws UnsupportedEncodingException {
        BufferedReader br = null;
        String line = null;
        String filePath = null;
        String packagePath = null;
        //List<String> list_file = new ArrayList<String>();
        StringBuffer buf = new StringBuffer();
        packagePath = this.getClass().getResource("").getPath();
        packagePath = StringUtils.substringBefore(packagePath, "/target") + "/" + filename;
        filePath = java.net.URLDecoder.decode(packagePath, "utf-8");
        System.out.println("获取目录地址:" + filePath + "\n");
        try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new FileReader(filePath));
            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null) {
                // 如果不用修改, 则按原来的内容回写
                //System.out.println("list_file :"+ line+"\n");
                buf.append(line);
                buf.append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
        return buf.toString();
    }

    /**
     * @param ConfigFileContexts 文件名
     * @return
     */
    public String[] GetBySemicolonFromConfigFile(String ConfigFileContexts) {
        String[] ConfigFileContextsList = null;
        try {
            ConfigFileContextsList = ConfigFileContexts.split(";");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("config file:" + ConfigFileContextsList + "\n");
        return ConfigFileContextsList;
    }

    /**
     * 清空文本内容，用于动态用例
     */
    public void clearInfoForFile() throws UnsupportedEncodingException {
        String filePath = null;
        String packagePath = null;
        packagePath = this.getClass().getResource("").getPath();
        packagePath = StringUtils.substringBefore(packagePath, "/target") + "/config.txt" ;
        filePath = java.net.URLDecoder.decode(packagePath, "utf-8");
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除文件夹
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除指定文件夹下的所有文件
    public static boolean delAllFile(String path) throws UnsupportedEncodingException {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

}

