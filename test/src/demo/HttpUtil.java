package demo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.io.DefaultHttpResponseParserFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.IOUtils;

import com.daren.common.utils.log.LogManager;

/**
 * HTTP 请求工具类 
 * Class Description:
 * Copyright:   Copyright (c)
 * Company:     Daren Capital. Co. Ltd.
 * @author:     caozhicong
 * @version:    1.0
 *
 * Modification History:
 * Date:        2017年6月26日
 * Author:   	caozhicong
 * Version:     1.0
 * Description: Initialize
 *
 */
public class HttpUtil {
	
    private static PoolingHttpClientConnectionManager connMgr; 
    //private static final int MAX_TIMEOUT = 7000;
    private static final int TIMEOUT_MIN_UNIT = 1000;
    // 设置连接池大小
    private static final int MAX_TOTAL = 300;
    // 设置每个路由的默认最大连接数
    private static final int DEFAULT_MAX_PER_ROUTE = 200;
    // 设置从连接池获取连接实例的超时 2s
    private static final int CONNECTION_REQUEST_TIMEOUT = 2;
    
    private static final String CHARSET = "utf-8";
    
    private static HttpClient httpClient = null;
  
    static {
    	
    	//注册访问协议相关的Socket工厂
    	Registry<ConnectionSocketFactory> connectionSocketFactory = 
    			RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
    			.register("https", SSLConnectionSocketFactory.getSystemSocketFactory()).build();
    	
    	// HttpConnection 工厂:配置写请求/解析响应处理器
    	HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory = 
    			new ManagedHttpClientConnectionFactory(DefaultHttpRequestWriterFactory.INSTANCE,
    					DefaultHttpResponseParserFactory.INSTANCE);
    	
    	// DNS解析器
    	DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;
    	
        // 设置连接池  
        connMgr = new PoolingHttpClientConnectionManager(
        					 connectionSocketFactory, connectionFactory, dnsResolver);
        
        // 默认为Socket配置
        SocketConfig defaultSocketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        connMgr.setDefaultSocketConfig(defaultSocketConfig);
        
        // 设置连接池大小  
        connMgr.setMaxTotal(MAX_TOTAL);
        // 每个路由的默认最大连接
        connMgr.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
  
        RequestConfig.Builder configBuilder = RequestConfig.custom();  
        // 设置连接超时  
      //  configBuilder.setConnectTimeout(TIMEOUT_MIN_UNIT * SystemConfig.INSTANCE.getInteger("xib", "connection_timeout_seconds"));  
        // 设置读取超时  
      //  configBuilder.setSocketTimeout(TIMEOUT_MIN_UNIT * SystemConfig.INSTANCE.getInteger("xib", "response_timeout_seconds"));  
        // 设置从连接池获取连接实例的超时  
        configBuilder.setConnectionRequestTimeout(TIMEOUT_MIN_UNIT * CONNECTION_REQUEST_TIMEOUT);  
        // 在提交请求之前 测试连接是否可用  
      //  configBuilder.setStaleConnectionCheckEnabled(true);  
        RequestConfig requestConfig = configBuilder.build();
        
        httpClient = getNewHttpClient();
        
        // JVM停掉或重启时，关闭连接池释放掉连接
        Runtime.getRuntime().addShutdownHook(new Thread() {
        	
        	@Override
        	public void run() {
        		
        		try {
        			((InputStream) httpClient).close();
        		} catch (IOException e) {
        			LogManager.getRuntimeLog().error("关闭厦门国际httpclient连接池异常!", e); 
        		}
        	}
        });
    }
  
    /** 
     * 发送 GET 请求（HTTP），不带输入数据 
     * @param url 
     * @return 
     * @throws Exception 
     */  
    public static String doGet(String url) throws Exception {  
        return doGet(url, new HashMap<String, Object>());  
    }  
  
    
    /** 
     * 发送 GET 请求（HTTP），K-V形式 
     * @param url 
     * @param params 
     * @return 
     * @throws Exception 
     */  
    public static String doGet(String url, Map<String, Object> params) throws Exception {  
        String apiUrl = url;  
        StringBuffer param = new StringBuffer();  
        int i = 0;  
        for (String key : params.keySet()) {  
            if (i == 0)  
                param.append("?");  
            else  
                param.append("&");  
            param.append(key).append("=").append(params.get(key));  
            i++;  
        }  
        apiUrl += param;  
        String result = null;  
         
        try {  
            HttpGet httpPost = new HttpGet(apiUrl);  
            HttpResponse response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
  
            LogManager.getRuntimeLog().debug("执行状态码 : " + statusCode);  
  
            HttpEntity entity = response.getEntity();  
            if (entity != null) {  
                InputStream instream = entity.getContent();  
               // result = IOUtils.toString(instream, CHARSET);  
            }  
        } catch (IOException e) {
            LogManager.getRuntimeLog().error("调用厦门国际银行接口网络异常", e);
            throw new Exception("调用厦门国际银行接口网络异常", e);
        } catch (Exception e) {
        	LogManager.getRuntimeLog().error("调用厦门国际银行接口失败，请查看具体原因！", e);
        	throw e;
        }
        return result;  
    }  
  
    /** 
     * 发送 POST 请求（HTTP），不带输入数据 
     * @param apiUrl 
     * @return 
     * @throws Exception 
     */  
    public static String doPost(String apiUrl) throws Exception {  
        return doPost(apiUrl, new HashMap<String, Object>());  
    }  
  
    /** 
     * 发送 POST 请求（HTTP），K-V形式 
     * @param apiUrl API接口URL 
     * @param params 参数map 
     * @return 
     * @throws Exception 
     */  
    public static String doPost(String apiUrl, Map<String, Object> params) throws Exception {  

    	String httpStr = null;  
        HttpPost httpPost = new HttpPost(apiUrl);  
        HttpResponse response = null;  
  
        try {  
            List<NameValuePair> pairList = new ArrayList<>(params.size());  
            for (Map.Entry<String, Object> entry : params.entrySet()) {
            	if (entry.getValue() == null)
            		continue;
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
                        .getValue().toString());  
                pairList.add(pair);  
            }  
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(CHARSET)));  
            response = httpClient.execute(httpPost);  
            LogManager.getRuntimeLog().debug(response.toString());  
            HttpEntity entity = response.getEntity();  
            httpStr = EntityUtils.toString(entity, CHARSET);  
        } catch (IOException e) {
            LogManager.getRuntimeLog().error("调用厦门国际银行接口网络异常", e);
            throw new Exception("调用厦门国际银行接口网络异常", e);
        } catch (Exception e) {
        	LogManager.getRuntimeLog().error("调用厦门国际银行接口失败，请查看具体原因！", e);
        	throw e;
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                	LogManager.getRuntimeLog().error("调用厦门国际银行接口关闭连接异常!", e); 
                }  
            }  
        }  
        return httpStr;  
    }  
    private static HttpClient getNewHttpClient() {  
        try {  
            KeyStore trustStore = KeyStore.getInstance(KeyStore  
                    .getDefaultType());  
            trustStore.load(null, null);  
            SSLSocketFactory sf = new SSLSocketFactory(trustStore);  
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
            HttpParams params = new BasicHttpParams();  
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);  
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);  
            SchemeRegistry registry = new SchemeRegistry();  
            registry.register(new Scheme("http", PlainSocketFactory  
                    .getSocketFactory(), 80));  
            registry.register(new Scheme("https", sf, 443));  
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(  
                    params, registry);  
            return new DefaultHttpClient(ccm, params);  
        } catch (Exception e) {  
            return new DefaultHttpClient();  
        }  
    }  
    /** 
     * 发送 POST 请求（HTTP），JSON形式 
     * @param apiUrl 
     * @param json json对象 
     * @return 
     * @throws Exception 
     */  
    public static String doPost(String apiUrl, Object json) throws Exception {  

    	String httpStr = null;  
        HttpPost httpPost = new HttpPost(apiUrl);  
        HttpResponse response = null;  
  
        try {  
            StringEntity stringEntity = new StringEntity(json.toString(),CHARSET);//解决中文乱码问题  
            stringEntity.setContentEncoding(CHARSET);  
            stringEntity.setContentType("application/json");  
            httpPost.setEntity(stringEntity);  
            response = httpClient.execute(httpPost);  
            HttpEntity entity = response.getEntity();
            LogManager.getRuntimeLog().debug(String.valueOf(response.getStatusLine().getStatusCode()));  
            httpStr = EntityUtils.toString(entity, CHARSET);  
        } catch (IOException e) {
            LogManager.getRuntimeLog().error("调用厦门国际银行接口网络异常", e);
            throw new Exception("调用厦门国际银行接口网络异常", e);
        } catch (Exception e) {
        	LogManager.getRuntimeLog().error("调用厦门国际银行接口失败，请查看具体原因！", e);
        	throw e;
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                	LogManager.getRuntimeLog().error("调用厦门国际银行接口关闭连接异常!", e);
                }  
            }  
        }  
        return httpStr;  
    }  
  
    /** 
     * 发送 SSL POST 请求（HTTPS），K-V形式 
     * @param apiUrl API接口URL 
     * @param params 参数map 
     * @return 
     * @throws Exception 
     */  
    public static String doPostSSL(String apiUrl, Map<String, Object> params) throws Exception {  
         
        HttpPost httpPost = new HttpPost(apiUrl);  
        HttpResponse response = null;  
        String httpStr = null;  
  
        try {
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
            for (Map.Entry<String, Object> entry : params.entrySet()) {
            	if (entry.getValue() == null)
            		continue;
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
                        .getValue().toString());  
                pairList.add(pair);  
            }  
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(CHARSET)));  
            response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            if (statusCode != HttpStatus.SC_OK) {
                return null;  
            }  
            HttpEntity entity = response.getEntity();  
            if (entity == null) {  
                return null;  
            }  
            httpStr = EntityUtils.toString(entity, CHARSET);  
        }  catch (Exception e) {
        	LogManager.getRuntimeLog().error("调用厦门国际银行接口失败，请查看具体原因！", e);
        	throw e;
        } finally {
            if (response != null) {
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                	 LogManager.getRuntimeLog().error("调用厦门国际银行接口关闭连接异常!", e);
                }  
            }  
        }  
        return httpStr;  
    }  
    /** 
     * 模拟请求 
     *  
     * @param url       资源地址 
     * @param map   参数列表 
     * @param encoding  编码 
     * @return 
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     * @throws IOException  
     * @throws ClientProtocolException  
     */  
    public static String sendPostJson1(String url, Map<String, Object> params) { 
    	
    	String body = "";  
    	try {
	        //采用绕过验证的方式处理https请求  
	        SSLContext sslcontext = createIgnoreVerifySSL();  
	       
	      //  trustAllHosts();
	      //  和

	       // httpPost.setHostnameVerifier(DO_NOT_VERIFY);
	      
           // 设置协议http和https对应的处理socket链接工厂的对象  
           Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
               .register("http", PlainConnectionSocketFactory.INSTANCE)  
               .register("https", new SSLConnectionSocketFactory(sslcontext))  
               .build();
           
           PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
           HttpClients.custom().setConnectionManager(connManager);  
	      
	           //创建自定义的httpclient对象  
           HttpClient client = getNewHttpClient();
	    //    getNewHttpClient
	        //创建post方式请求对象  
	        HttpPost httpPost = new HttpPost(url);  
	        
	          
	        //装填参数  
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
	        if(params!=null){  
	            for (Entry<String, Object> entry : params.entrySet()) {
	            	if (entry.getValue() == null || "".equals(entry.getValue()))
	            		continue;
	                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));  
	            }  
	        }  
	        //设置参数到请求对象中  
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));  
	      
	        System.out.println("请求地址："+url);  
	        System.out.println("请求参数："+nvps.toString());  
	          
	        //设置header信息  
	        //指定报文头【Content-type】、【User-Agent】  
	        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
	        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	          
	        //执行请求操作，并拿到结果（同步阻塞）  
	        HttpResponse response = client.execute(httpPost);  
	        //获取结果实体  
	        HttpEntity entity = response.getEntity();  
	        if (entity != null) {  
	            //按指定编码转换结果实体为String类型  
	            body = EntityUtils.toString(entity, "utf-8");  
	        }  
	        EntityUtils.consume(entity);  
	        //释放链接  
	        ((InputStream) response).close();
    	} catch (Exception ex) {
            LogManager.getRuntimeLog().error("调用厦门国际银行接口网络异常", ex);
            try {
				throw new Exception("调用厦门国际银行接口网络异常", ex);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        return body;  
    }  
    
    /** 
     * 绕过验证 
     *   
     * @return 
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     */  
    public static SSLContext createIgnoreVerifySSL() {
    	
    	SSLContext sc = null;
    	try {
	        sc = SSLContext.getInstance("SSLv3");  
	      
	        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法  
	        X509TrustManager trustManager = new X509TrustManager() {  
	            @Override  
	            public void checkClientTrusted(  
	                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                    String paramString) throws CertificateException {  
	            }  
	      
	            @Override  
	            public void checkServerTrusted(  
	                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                    String paramString) throws CertificateException {  
	            }  
	      
	            @Override  
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
	                return null;  
	            }  
	        };  
	      
	        sc.init(null, new TrustManager[] { trustManager }, null);
    	} catch (Exception ex) {
    		 ex.printStackTrace();
    		 LogManager.getRuntimeLog().error("调用厦门国际银行接口网络异常", ex);
    	}
    	
        return sc;  
    }  

    /** 
     * 发送 SSL POST 请求（HTTPS），JSON形式 
     * @param apiUrl API接口URL 
     * @param json JSON对象 
     * @return 
     * @throws Exception 
     */  
    public static String doPostSSL(String apiUrl, Object json) throws Exception {  

    	HttpPost httpPost = new HttpPost(apiUrl);  
        HttpResponse response = null;  
        String httpStr = null;
  
        try {
            StringEntity stringEntity = new StringEntity(json.toString(),CHARSET);//解决中文乱码问题  
            stringEntity.setContentEncoding(CHARSET);  
            stringEntity.setContentType("application/json");  
            httpPost.setEntity(stringEntity);  
            response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            if (statusCode != HttpStatus.SC_OK) {  
                return null;  
            }  
            HttpEntity entity = response.getEntity();  
            if (entity == null) {  
                return null;  
            }  
            httpStr = EntityUtils.toString(entity, CHARSET);  
        }  catch (Exception e) {
        	LogManager.getRuntimeLog().error("调用厦门国际银行接口失败，请查看具体原因！", e);
        	throw e;
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                	LogManager.getRuntimeLog().error("调用厦门国际银行接口关闭连接异常!", e);
                }  
            }  
        }  
        return httpStr;  
    }
}