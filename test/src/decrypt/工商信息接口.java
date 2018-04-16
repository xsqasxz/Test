package decrypt;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * @Authoc xueshiqi
 * @Date 2018/3/16 16:17
 */
public class 工商信息接口 {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        String url = "http://apidoc.chinecredit.com/productservice/productOutInterface/product_companyInfoOut";
//        String url = "http://apidoc.chinecredit.com/productservice/productOutInterface/getBusiness";
        String companyName = "深圳市宝安区沙井杨家祖粉店";//企业名称
        String productpara = "companyName";//参数格式
        String userKey = "LNFE76USYH5XPT7MSZE7C1XMGZWWXONE67EFKURMV5W6QKON8HZKK8UR8ICML1LL";//用户Key(宸信提供)
        JSONObject para = new JSONObject();
        para.put("companyName", companyName);
        para.put("productpara", productpara);
        para.put("userKey", userKey);
        System.out.println(doJsonPost(url, para));
    }
    public static String doJsonPost(String url, JSONObject params)
            throws ClientProtocolException, IOException {
        HttpClient httpclient = HttpClients.createDefault();
        // HttpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        StringEntity entity = new StringEntity(params.toString(), "utf-8");// 解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        return httpclient.execute(httpPost, new MyResponseHandler<Object>())
                .toString();
    }
    static class MyResponseHandler<T> implements ResponseHandler<Object> {
        @Override
        public String handleResponse(HttpResponse response)
                throws ClientProtocolException, IOException {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                if (entity == null) {
                    throw new ClientProtocolException(
                            "Response contains no content");
                }
                ContentType contentType = ContentType.getOrDefault(entity);
                Charset charset = contentType.getCharset();
                String str = EntityUtils.toString(entity, charset);
                return str;
            } else {
                ContentType contentType = ContentType.getOrDefault(entity);
                Charset charset = contentType.getCharset();
                throw new HttpResponseException(statusLine.getStatusCode(),
                        statusLine.getReasonPhrase());
            }
        }
    }

}
