package demo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.TreeMap;

/**
 * 主要包含签名、验签功能。
 * <p>
 * Created by wuxinw on 2017/5/3.
 */

public class SignUtil {
    public static final String PRIVATE_KEY_PATH = "demo/ifsp.pfx";
    public static final String PUBLIC_KEY_PATH = "demo/libifsp.cer";
    public static final String PRIVATE_KEY_PASSWD = "abc110";

    /**
     * 使用示例
     *
     * @param args
     * @throws FileNotFoundException
     * @throws KeyStoreException
     */
    public static void main(String[] args) throws FileNotFoundException, KeyStoreException {
     
    	String body = "{\"data\":{\"chargeOff_auto\":\"自动出账\",\"plat_no\":\"XIB-ABD-B-20170510\",\"prod_account\":\"F2017051619054097110027508\",\"remain_limit\":9000,\"prod_state\":\"产品发布\",\"saled_limit\":1000,\"prod_id\":\"1212\",\"prod_name\":\"新标的\",\"total_limit\":10000,\"ist_year\":0.08},\"recode\":\"10000\",\"remsg\":\"成功\",\"signdata\":\"2f8wyZBnUXkVRBn86cjdgsvDbJw2rRPa32OSdA0m9TOWjm1YvSPiNWYKq1xr/t/6Bb0vYacUZC41WuY07as5g76s5ohRQBgdcq0Ll3519cDG7o7W6JGp+5fB9uiMKkxgYH8eSTtqKH0IV1q0SyM8/XlsEl8/Z0YodxDX+WinhjGjEr8Qb/JOQyc+Vjfvc3TVk1UQtxxnbGV4iMHqfnf7nkjYg95yAXrCPYbjS/65hL8HOusHxr1eWj5R++TNQzXvKwM/cbPHNTSiXglWOEctIDvJrY0gNGjcYxgzcZpmp1G6atf+ZkFWuREmSQ46wgRVyIZcbd2qEytmtMB59yKtbw==\"}";
        Map<String, Object> parse = JSONObject.parseObject(body, TreeMap.class);
        String signdata0 = (String) parse.remove("signdata");
        
        System.out.println("signdata0:" + signdata0);
        String s = params2PlainText(parse);
        System.out.println("plain:" + s);
        System.out.println("signdata1:" + sign(parse));
        System.out.println("signdata1:" + sign(s));
        boolean b = verifySign(s, signdata0);
        System.out.println(b);
    
    }

    /**
     * 签名
     *
     * @return
     */
    public static String sign(Map<String, Object> params) {
        return sign(params2PlainText(params));
    }

    /**
     * 签名
     *
     * @param plainText
     * @return
     */
    public static String sign(String plainText) {
        try {
            Signature sig = Signature.getInstance("SHA1WithRSA");
            sig.initSign(getPrivateKey());
            sig.update(plainText.getBytes());
            byte[] b = sig.sign();
            return new String(Base64.encodeBase64(b));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifySign(Map<String, Object> params, String signedText) {
        return verifySign(params2PlainText(params), signedText);
    }

    /**
     * 使用公钥验签
     *
     * @param plainText
     * @param signedText
     * @return
     */
    public static boolean verifySign(String plainText, String signedText) {
        try {
            signedText = signedText.replaceAll(" ", "+");
            Signature sig = Signature.getInstance("SHA1WithRSA");
            X509Certificate certificate = loadCertificate();
            sig.initVerify(certificate);
            sig.update(plainText.getBytes());
            byte[] b = Base64.decodeBase64(signedText.getBytes());
            return sig.verify(b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取私钥
     *
     * @return
     */
    private static PrivateKey getPrivateKey() {
        String path = SignUtil.class.getClassLoader().getResource(PRIVATE_KEY_PATH).getPath();
        KeyStore ks = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, PRIVATE_KEY_PASSWD.toCharArray());
            fis.close();
            String keyAlias = null;
            if (ks.aliases().hasMoreElements()) {
                keyAlias = ks.aliases().nextElement();
            }
            return (PrivateKey) ks.getKey(keyAlias, PRIVATE_KEY_PASSWD.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(fis);
        }
        return null;
    }

    /**
     * 参数转为签名原文
     *
     * @param params
     * @return
     */
    private static String params2PlainText(Map<String, Object> params) {
        TreeMap<String, Object> sortedParams = new TreeMap<>();
        sortedParams.putAll(params);
        StringBuilder plainText = new StringBuilder();
        for (String key : sortedParams.keySet()) {
            if (sortedParams.get(key) instanceof String || sortedParams.get(key) instanceof Number) {
                plainText.append("|").append(sortedParams.get(key));
            } else {
                plainText.append("|").append(JSONObject.toJSONString(sortedParams.get(key)));
            }
        }
        plainText.deleteCharAt(0);
        return plainText.toString();
    }
    /**
     * 获取公钥
     *
     * @return
     * @throws Exception
     */
    private static X509Certificate loadCertificate() throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        URL path = SignUtil.class.getClassLoader().getResource(PUBLIC_KEY_PATH);
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        InputStream is = null;
        if (path != null)    
            try {
                is = new FileInputStream(path.getFile());
            } catch (FileNotFoundException e) {
                is = classLoader.getResourceAsStream(PUBLIC_KEY_PATH);
            }
        else {
            is = new FileInputStream(PUBLIC_KEY_PATH);
        }
//        ByteArrayInputStream bis = new ByteArrayInputStream(("-----BEGIN CERTIFICATE-----\n" +
//                "MIIDazCCAlOgAwIBAgIJAK5832wV7o2XMA0GCSqGSIb3DQEBCwUAMEwxCzAJBgNV" +
//                "BAYTAkNOMQ8wDQYDVQQIDAZGdUppYW4xDzANBgNVBAcMBlhpYU1lbjEMMAoGA1UE" +
//                "CgwDWElCMQ0wCwYDVQQDDARpZnNwMB4XDTE3MDEyMzA4NDkzM1oXDTIwMDEyMzA4" +
//                "NDkzM1owTDELMAkGA1UEBhMCQ04xDzANBgNVBAgMBkZ1SmlhbjEPMA0GA1UEBwwG" +
//                "WGlhTWVuMQwwCgYDVQQKDANYSUIxDTALBgNVBAMMBGlmc3AwggEiMA0GCSqGSIb3" +
//                "DQEBAQUAA4IBDwAwggEKAoIBAQDndP4f+tuaC+66rHtlmTBollIqd96XIMeV76BR" +
//                "HLbbzorpXEb4GylcIJgXCaqEHMzWpfWSVMKykdfr/3JQVzabFRcqoKHxrziW79G/" +
//                "ZouGiB+uj5vOA5gT8uY+D7An9yrPxZdx2s+jMuGDv/lEi/LqZzAqlPatXADinUA9" +
//                "yPrc/FTxR3FSXDqE59X1ooVC+Fartm9kl5HDCxEWmVLjB61oeib1VYOMkrUXW3OD" +
//                "C2rQ+2N96yALWZ//a/mtDocf9fUrpjjvwshdxo7veY93bg3tWSEzsc49VMFxUp1k" +
//                "p3cLu/9OHdW2Z7fK+WD/SV65ZeGBG6EvBsDigyQSyFpxyV7ZAgMBAAGjUDBOMB0G" +
//                "A1UdDgQWBBSvuzuf1P3VdILS14VC5VufHNOuQTAfBgNVHSMEGDAWgBSvuzuf1P3V" +
//                "dILS14VC5VufHNOuQTAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQAB" +
//                "P7nOhefVaMxpUMakNDU8PCTQ/mJukA2HXL+13di+jZWNmk/RrVz977qTQU2LXPNY" +
//                "gnhLw1azvGJ0HcheeglsdUB1X/xjPaO/VDusjWKt80gD5U6BfFqMIFDouaDZsclc" +
//                "NU6jFBwrd//Gpcb5rZZBhhLoMIHkcys1U0ayyVzB/jhVgWhATlYG7/O36uYLToaD" +
//                "RGjzvve5gjHXwVJpFzgIgXJf76zdHDwPRjSIFYfWMmuUfY0SMxqaQBylU2W7+NwJ" +
//                "eFfgOM0jQkRoSQox1xFzA4bNNC9C3EMcHVO5gc8UhzK9rgy+0aK1p/fR5zxV3uUi" +
//                "5FPY5TyfmVTHD8htDXSB\n" +
//                "-----END CERTIFICATE-----").getBytes());
        X509Certificate certificate = (X509Certificate) factory.generateCertificate(is);
        is.close();
        return certificate;
    }


}
