package demo;
  
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;  
import java.net.URL;  
import java.net.URLConnection;  
import java.net.URLDecoder;  

public class BaiduTranslate {  
//      20170921000084428
//    XNX_jedQVp21xH42KcX0

    /**  
     * @param args  
     */  
    public static void main(String[] args) {
        // TODO Auto-generated method stub  
          
         String body="条条道路通罗马";  
         String from="zh";  
         String to="en";  
           
         String result=getResult(body,from,to);  
         System.out.println(result);

    }  
    //百度平台（翻译接口）相关数据  
    public static String getResult(String body,String from ,String to){  
        String result="";  
        //拼接相关参数  
        String params="http://api.fanyi.baidu.com/api/trans/vip/translate?q=apple&from=en&to=zh&appid=2015063000000001&salt=1435660288&sign=f89f9594663708c1605f3d736d01d2d4&q="+body+"&from="+from+"&to="+to;
         try {  
            URL url = new URL(params);  
            URLConnection connection = url.openConnection();    
            //设置连接时间(10*1000)  
            connection.setConnectTimeout(10*1000);  
            //设置输出  
            connection.setDoOutput(true);  
            //设置输出  
            connection.setDoInput(true);  
            //设置缓存  
            connection.setUseCaches(false);           
            //outputstream-----输出流  
            InputStream inputstream=connection.getInputStream();  
            //缓存字符流  
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputstream));   
            //返回相关结果  
            StringBuilder builder=new StringBuilder();  
            while(buffer.read()!=-1){  
                builder.append(buffer.readLine());                
            }  
            //返回相关结果  
            result=builder.toString();  
            //缓存字符流关闭操作  
            buffer.close();  
  
        } catch (MalformedURLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
          
        return result;  
    }  

  
}  