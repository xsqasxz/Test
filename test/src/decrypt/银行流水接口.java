package decrypt;

import baidufanyi.com.baidu.translate.demo.HttpGet;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * @Authoc xueshiqi
 * @Date 2018/3/16 16:17
 */
public class 银行流水接口 {
    private static final String TRANS_API_HOST = "https://api.fudata.cn/tarantula/crawler_callback";
    private static final String FUSHU_URL = "https://api.fudata.cn/tarantula/get_token";

    public static String getTransResult() {
        Map<String, String> params = buildParams();
        return HttpGet.get(TRANS_API_HOST, params,"utf-8");
    }

    private static Map<String, String> buildParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("token", getToken());
        params.put("target", "http://daren8.vicp.cc:9001/app/fuShu/responseCallBack");
        return params;
    }

    private static String getToken() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("application_id", "00201802061454030002");
        params.put("application_secret", "fde87633e785e7fcbd4d73f69d2625cb8906414d");
        String token =  HttpGet.get(FUSHU_URL, params,"utf-8");
        Gson gson = new Gson();
        Map m = gson.fromJson(token,Map.class);
        System.out.println((String)m.get("token"));
        return (String)m.get("token");
    }
    //308bd72739a04a51a89723a1c7d56f50ac2d0cb49ba79a3b45712ac61f5e73ac
    //308bd72739a04a51a89723a1c7d56f50ac2d0cb49ba79a3b45712ac61f5e73ac
    public static void main(String[] args){
        System.out.println(getTransResult());
    }
}
