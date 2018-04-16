package decrypt;

import baidufanyi.com.baidu.translate.demo.HttpGet;
import java.util.HashMap;
import java.util.Map;

public class HTML爬取 {
    private static final String TRANS_API_HOST = "http://qq.ip138.com/idsearch/index.asp";

    public String getTransResult(String userid) {
        Map<String, String> params = buildParams(userid);
        return HttpGet.get(TRANS_API_HOST, params,"gb2312");
    }

    private Map<String, String> buildParams(String userid) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "idcard");
        params.put("userid", userid);
        params.put("B1", "查 询");
        return params;
    }
}
