package CollectionOfFunctionalMethods.BasicMethods;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GetRandom {
    /**
     * 生成1-Range不重复的随机数
     * @Range  设置随机数的范围
     * @return 将不重复的随机数赋给数组中的各个元素并返回
     */
    public static List<String> randomArr(String Range) {
        int[] intRandom = new int[Integer.parseInt(Range)];
        List mylist = new ArrayList(); //生成数据集，用来保存随即生成数，并用于判断
        Random rd = new Random();
        while(mylist.size() < Integer.parseInt(Range)) {
            int num = rd.nextInt(Integer.parseInt(Range))+1;
            if(!mylist.contains(num)) {
                mylist.add(num); //往集合里面添加数据。
            }
        }
        return  mylist;
    }
    public static String ReturnGetRandomChar(int charNum) {
        String reChar="";
        for(int i=0;i<charNum;i++)
        {
            reChar=reChar+getRandomChar();
        }
        return reChar;
    }
    public static char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }

        return str.charAt(0);
    }

}
