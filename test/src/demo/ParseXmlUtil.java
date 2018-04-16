package demo;

import java.util.ArrayList;
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Set;

import baidufanyi.com.baidu.translate.demo.TransApi;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.dom4j.Document;  
import org.dom4j.DocumentException;  
import org.dom4j.DocumentHelper;  
import org.dom4j.Element;  
import org.dom4j.Namespace;  
import org.dom4j.QName;  
/** 
 * 解析xml的工具类 
 * 1、将多层级xml解析为Map 
 * 2、将多层级xml解析为Json 
 * 
 * @author lmb 
 * 
 */  
public class ParseXmlUtil {  
        
      public static Logger logger = Logger.getLogger(ParseXmlUtil.class);  
      public static void main(String[] args) {
          //      20170921000084428
//    XNX_jedQVp21xH42KcX0
          TransApi api = new TransApi("20170921000084428", "XNX_jedQVp21xH42KcX0");
          // 获取一个xml文件
              String textFromFile = MyXmlUtil.XmlToString();
                String[] strings =  textFromFile.split("key=\"description\">");
//              for(String s:strings){
////                  System.out.println(s.substring(0,s.indexOf("</value>")));
//                  System.out.println(s.substring(s.indexOf("</value>")));
//              }
              for(int i = 1 ; i<strings.length;i++){
                  String s = strings[i];
                  String a = s.substring(0,s.indexOf("</value>"));
                  a = a.replace("_","_ ");
                  JSONObject jsonObject = JSONObject.parseObject(api.getTransResult(a, "auto", "zh"));
                  JSONArray ja = jsonObject.getJSONArray("trans_result");
                  Map m =(Map) ja.get(0);
                  System.out.print(m.get("dst") + s.substring(s.indexOf("</value>"))+"key=\"description\">");
              }
              //将xml解析为Map
//              Map resultMap = xml2map(textFromFile);
              //将xml解析为Json
      }
}  