package demo;

import baidufanyi.com.baidu.translate.demo.TransApi;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.security.auth.callback.TextOutputCallback;
import java.io.*;
import java.util.Map;

/** 
 * 解析xml的工具类 
 * 1、将多层级xml解析为Map 
 * 2、将多层级xml解析为Json 
 * 
 * @author lmb 
 * 
 */  
public class BaiduFangYiUtil {
      public static void main(String[] args) {
          TransApi api = new TransApi("20170921000084428", "XNX_jedQVp21xH42KcX0");
          File file = new File("C:\\Users\\darendai\\Desktop\\english.text");
          System.out.println(txt2String(file));


          String a ="to restore database";
          JSONObject jsonObject = JSONObject.parseObject(api.getTransResult(a, "auto", "zh"));
          JSONArray ja = jsonObject.getJSONArray("trans_result");
          Map m =(Map) ja.get(0);
          System.out.print(m.get("dst"));
      }

    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                System.out.println(System.lineSeparator()+s);
//                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}  