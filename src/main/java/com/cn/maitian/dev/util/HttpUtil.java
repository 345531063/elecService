package com.cn.maitian.dev.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
    private static OkHttpClient getHttpClient() throws Exception {
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

        };
        ctx.init(null, new TrustManager[]{tm}, new SecureRandom());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        OkHttpClient client;
        SSLSocketFactory sslSocketFactory = ctx.getSocketFactory();
        builder.sslSocketFactory(sslSocketFactory, tm);
        builder.hostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        client = builder.build();
        return client;
    }
    public static Map<String, String> request(String url, Map<String, String> param) {
        Map<String, String> maps = new HashMap<>();
        // 创建客户端
        OkHttpClient okHttpClient = new OkHttpClient();
        String reqeustBodyStr = createXml(param);
        log.info("??>>>>>微信请求前.reqeustBodyStr>>>>>"+reqeustBodyStr);
        // 创建请求体对象
        RequestBody body = RequestBody.create(MediaType.parse("application/xml"), reqeustBodyStr);
        // 创建请求参数
        Request request = new Request.Builder().url(url).post(body).build();
        // 创建请求对象
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                log.info(">>>>result>>>" + result);
                maps = new XmlFormatter().wxParseXml(result, "xml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maps;
    }
    public static Map<String, String> requestHttps(String url, Map<String, String> param)throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        String   mch_id   = param.get("mch_id");
        String   data     = createXml(param);
        Map<String, String> resultMap = new HashMap<>();
        InputStream instream = HttpUtil.class.getResourceAsStream(("/cert/apiclient_cert.p12"));
        String result = "";
        try {
            keyStore.load(instream, mch_id.toCharArray());
        }
        finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httppost = new HttpPost(url);
            StringEntity entitys = new StringEntity(data);
            httppost.setEntity((HttpEntity) entitys);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text = "";
                    String t = "";
                    while ((text = bufferedReader.readLine()) != null) {
                        t += text;
                    }
                    byte[] temp = t.getBytes("utf-8");
                    //这里写原编码方式
                    String newStr = new String(temp, "utf-8");
                    // 这里写转换后的编码方式
                    result = newStr;
                }
                EntityUtils.consume(entity);

                resultMap = new XmlFormatter().wxParseXml(result, "xml");
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return resultMap;
    }

    public static String createXml(Map<String, String> param) {
        StringWriter writer = new StringWriter();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("xml");
            for (String key : param.keySet()) {
                Element element = document.createElement(key);
                element.setTextContent(param.get(key));
                root.appendChild(element);
            }
            document.appendChild(root);
            TransformerFactory transformfactory = TransformerFactory.newInstance();
            Transformer transformer = transformfactory.newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            log.info("解析后:::::" + writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.trim().equals("0:0:0:0:0:0:0:1")) {
            return "171.217.52.197";
        }
        return ip;
    }

    public static String getRandom() {
        int times = new Random().nextInt(10000000) + 10000000;
        String result = String.valueOf(System.currentTimeMillis()) + times;
        return result;
    }

    public static String wxPaySign(Map<String, String> param, String key) {
        String sign = wxPaySignTogather(param, key);

        return sign;
    }

    public static String wxPaySignTogather(Map<String, String> info, String key) {
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(info.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> arg0, Map.Entry<String, String> arg1) {
                return (arg0.getKey()).compareTo(arg1.getKey());
            }
        });
        String ret = "";

        for (Map.Entry<String, String> entry : infoIds) {
            ret += entry.getKey();
            ret += "=";
            ret += entry.getValue();
            ret += "&";
        }
        ret = ret.substring(0, ret.length() - 1) + "&key=" + key;
        log.info("排序前ret:" + ret);
        String md5 = StrUtils.md5(ret);
        md5 = md5.toUpperCase();
        return md5;
    }

    public static String wxPaySignTogather1(Map<String, String> info) {
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(info.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> arg0, Map.Entry<String, String> arg1) {
                return (arg0.getKey()).compareTo(arg1.getKey());
            }
        });
        String ret = "";

        for (Map.Entry<String, String> entry : infoIds) {
            ret += entry.getKey();
            ret += "=";
            ret += entry.getValue();
            ret += "&";
        }
        ret = ret.substring(0, ret.length() - 1);
        log.info("排序前ret:" + ret);
        String md5 = StrUtils.md5(ret);
        md5 = md5.toUpperCase();
        return md5;
    }

    public static void main(String args[]) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        log.info(uuid);
    }

    public static String wxPayBack(String url,String mch_id,  String data) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File("/cert/apiclient_cert.p12"));
        String result = "";
        try {
            keyStore.load(instream, mch_id.toCharArray());
        }
        finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
            StringEntity entitys = new StringEntity(data);
            httppost.setEntity((HttpEntity) entitys);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text = "";
                    String t = "";
                    while ((text = bufferedReader.readLine()) != null) {
                        t += text;
                    }
                    byte[] temp = t.getBytes("gbk");
                    //这里写原编码方式
                    String newStr = new String(temp, "utf-8");
                    // 这里写转换后的编码方式
                    result = newStr;
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
    }
}
