package demo;

import com.alibaba.fastjson.JSON;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by linqian on 2017/4/28.
 */
public abstract class ServiceDemo {

    //private static final String REQ_URL = "http://58.23.16.244:2080/fd-client/";
	private static final String REQ_URL = "https://59.61.76.215/fd-client/";
	
    private static final String SEND_MSG_CODE_METHOD = "smsAction_sendMsgCode";
    public static final String MOBILE_TAG = "mobile";
    public static final String SIGNDATA_TAG = "signdata";

    public static Map<String, Object> sendMsgCode(Map<String,Object> params){
        HttpClient client = new HttpClient();
//        String mobile = (String)params.get(MOBILE_TAG);

//        if(mobile ==null || mobile.isEmpty()){
//            System.out.println("mobile is null");
//            return null;
//        }

        PostMethod method = new PostMethod(REQ_URL + SEND_MSG_CODE_METHOD);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            method.setParameter(entry.getKey(),String.valueOf(entry.getValue()));
        }
//        method.setParameter(MOBILE_TAG,mobile);
        //签名
        
        
        String signdata = SignUtil.sign(params);
        method.setParameter(SIGNDATA_TAG,signdata);
        try {
            client.executeMethod(method);
            byte[] responseByte		= method.getResponseBody();
            String responseBody		= new String(responseByte, "UTF-8");
            return  JSON.parseObject(responseBody);
            
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Map<String, Object> sendMsg(Map<String,Object> params,String urlMethod) throws UnsupportedEncodingException{
        HttpClient client = new HttpClient();
//        String mobile = (String)params.get(MOBILE_TAG);

//        if(mobile ==null || mobile.isEmpty()){
//            System.out.println("mobile is null");
//            return null;
//        }
      
        PostMethod method = new PostMethod(REQ_URL + urlMethod);
        System.out.println("REQ_URL + urlMethod==="+REQ_URL + urlMethod);
        method.setRequestHeader("ContentType", "text/html;charset=utf-8");
       
        
        for (Map.Entry<String, Object> entry : params.entrySet()) {
        	System.out.println("key=="+entry.getKey() +"   value="+String.valueOf(entry.getValue()));
        	//if("name".equals(entry.getKey() +"")){
        		
        	//	method.setParameter(entry.getKey(),java.net.URLEncoder.encode(String.valueOf(entry.getValue()),"utf-8"));
    			
        //	}
        	//else{
        		
					method.setParameter(entry.getKey(),String.valueOf(entry.getValue()).getBytes("utf-8")+"");
			
        	//}
           
        }
//        method.setParameter(MOBILE_TAG,mobile);
        //签名
        String signdata = SignUtil.sign(params);
        method.setParameter(SIGNDATA_TAG,signdata);
        
        System.out.println("key=="+SIGNDATA_TAG +"   value="+signdata);
        try {
        	client.executeMethod(method);
             byte[] responseByte		= method.getResponseBody();
             String responseBody		= new String(responseByte, "UTF-8");
             return  JSON.parseObject(responseBody);
             
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    
    
    public static String sendMsg2(Map<String,Object> params ,String urlMethod) throws UnsupportedEncodingException{
        String returnString="";
//        String mobile = (String)params.get(MOBILE_TAG);

//        if(mobile ==null || mobile.isEmpty()){
//            System.out.println("mobile is null");
//            return null;
//        }
      try {
    	  String signdata = SignUtil.sign(params);
    	  params.put(SIGNDATA_TAG,signdata);
          
    //
    	  returnString= HttpClientUtil.doPost(REQ_URL + urlMethod,params,"utf-8");
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      return returnString;
      

    }
    
    

}
