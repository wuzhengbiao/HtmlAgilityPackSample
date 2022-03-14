package CollectionOfFunctionalMethods.BasicMethods;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class LookForphoto {
    public int depth = 0;
    String path = null;
    List<String> list_all = new ArrayList<String>();
    /**
     * @version 1.0
     * 已经增加目录遍历功能，即文件夹及子文件中全部图片全部会遍历得到
     */
    public List<String> printDirectory(File f, int depth) {
        if (!f.isDirectory()) {
            // 如果不是目录，则打印输出
            //System.out.println("不是目录"+f);
        } else {
            File[] fs = f.listFiles();
            depth++;
            for (int i = 0; i < fs.length; ++i) {
                File file = fs[i];
                path = file.getPath();
                list_all.add(path);
            }
        }
        return list_all;
    }
    /**
     * 将得到的list_all按照结尾是.及其他条件筛选一下是图片的信息
     * @param strPath    文件的绝对路径字符串
     * @param format     后缀名
     * @return list_last 符合format后缀名称的全部路径
     */
    public List<String> getPictures(final String strPath, String[] format) {

        List<String> list_last = new ArrayList<String>();
        List<String> list = new ArrayList<String>();
        File file = new File(strPath);
        list = printDirectory(file, depth);
        int formatlength;
        /**
         * 在循环判断之前，就必须完成树的遍历
         */
        for(formatlength=0;formatlength<format.length;formatlength++)
        {
            for (int k = 0; k < list.size(); k++) {

                int idx = list.get(k).lastIndexOf(".");
                // Log.v("idx:", String.valueOf(idx));
                if (idx <= 0) {
                    continue;
                }
                String suffix = list.get(k).substring(idx);
              /*
             * format可以是".jpg"、".jpeg"等等，例如suffix.toLowerCase().equals(".jpeg")
             */
                if (suffix.equalsIgnoreCase(format[formatlength]))//忽略大小写比较
                {
                    list_last.add(list.get(k));
                }
            }
        }
        /**
         * 如果没有这个，因为List<String> list_all = new
         * ArrayList<String>();作为GetEachDir类构造函数的成员变量
         * ，可以不被清楚，再再次使用GetEachDir的printDirectory,之前的list_all依然存在
         */
        list_all.clear();
        return list_last;
    }
}