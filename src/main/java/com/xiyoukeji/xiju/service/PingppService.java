package com.xiyoukeji.xiju.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import com.xiyoukeji.xiju.domain.PingppSuccess;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqy on 2016/4/26.
 */
@Service
public class PingppService {
	
	Logger logger =Logger.getLogger(PingppService.class);
	
	//public static final String APIKEY="sk_test_rD4Su99Gq5q9rHeLW10qzvXT";//注册ping++以后获得的Secret key,测试阶段可以使用Test Secret Key
    public static final String APIKEY= "sk_live_8G0CCSfPif9GurzLGCPe1qPG";
	public static final String APPID="app_CqnLOO9m9SKCL4Cu";//你在ping++创建的应用的ID
    public static String PRIVATE_KEY_PATH="WEB-INF/rsa_private_key.pem";//放在ping++个人信息里面商户公钥所对应的密钥路径,注意是路径,用来发出请求时ping++验签,注意要在商户公钥里开启验签功能才有效
    public static String PUBKEYPATH = "WEB-INF/pingpp_public_key.pem";//ping++公钥文件所在路径,注意是路径,ping++公钥文件内容从ping++用户信息里的ping++公钥复制获取,使用该公钥可以验证ping++对服务器的回调是否从ping++服务器发出
    @Resource
    HttpServletRequest request;
    @Resource
    ServletContext servletContext;

    //支付渠道,如果你喜欢的话可以使用枚举类将以下的常量枚举
    public static final String WX_PUB="wx_pub";//微信公众号支付,我们的微信支付使用这个
    public static final String WX_PUB_QR="wx_pub_qr";//微信公众号扫码支付
    public static final String ALIPAY_PC="alipay_pc_direct";//支付宝电脑网页支付,我们的网页版支付宝支付使用这个
    public static final String ALIPAY_WAP="alipay_wap";//支付宝手机网页支付
    public static final String ALIPAY="alipay";//支付宝APP支付，使用这个将会在智能手机里跳转到支付宝的APP
    public static final String WX="wx";//微信APP支付,将会跳转到微信APP
    public static final String UPACP_PC="upacp_pc";//银联电脑端网页支付,我们使用这个银联支付
    public static final String UPACP_WAP="upacp_wap";//银联手机端网页支付

    @PostConstruct
    public void init() {
        PRIVATE_KEY_PATH=servletContext.getRealPath("/")+PRIVATE_KEY_PATH;
        PUBKEYPATH=servletContext.getRealPath("/")+PUBKEYPATH;
        Pingpp.apiKey = APIKEY;
        Pingpp.privateKeyPath = PRIVATE_KEY_PATH;
    }

    /*
        创建订单Charge类
        参数 String order_no 订单ID,订单最重要的属性,通过这个属性进行唯一区别订单,我们可以使用自己订单表的ID或者别的唯一性的值,ping++一个应用中order_no不可重复,其中alipay: 1-64 位， wx: 1-32 位 ,upacp: 8-40 位,所以推荐使用 8-20 位，要求数字或字母，不允许特殊字符
             int order_no 需要支付的订单金额,注意单位为分,假设要付款1元,则要传入100
             String channel 付款渠道，使用上述常量中的一个的值
             String subject 商品标题,订单出售的商品标题,随意填,可相同
             String body 商品内容,订单出售的商品内容,随意填,可相同
             String success_url 成功支付以后的回调地址，注意是客户端支付成功后跳转,跟服务器端无关,支付渠道为alipay_pc_direct,alipay_wap,upacp_pc,upacp_wap需要填写
             String open_id 用户在后台的唯一标识,只有支付渠道为wx_pub才需要填写
        返回 Charge对象，得到Charge对象后,只需要将Charge对象的json字符串返回客户端即可，客户端通过调用ping++的SDK来进行各种跳转
     */
    public Charge CreateOrder(String order_no,int amount,String channel,String subject,String body,String success_url,String open_id,Integer time_expire) throws Exception{
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("order_no",  order_no);
        chargeParams.put("amount", amount);
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", APPID);
        chargeParams.put("app",app);
        chargeParams.put("channel",channel);
        chargeParams.put("currency","cny");
        chargeParams.put("client_ip",getIp());
        chargeParams.put("subject",subject);
        chargeParams.put("body",body);
        chargeParams.put("time_expire", time_expire);
        if(channel.equals(ALIPAY_PC)||channel.equals(ALIPAY_WAP)) {
            Map<String, String> extra = new HashMap<String, String>();
            extra.put("success_url",success_url);
            chargeParams.put("extra", extra);
        }
        else if(channel.equals(UPACP_PC)||channel.equals(UPACP_WAP)){
            Map<String, String> extra = new HashMap<String, String>();
            extra.put("result_url",success_url);
            chargeParams.put("extra", extra);
        }
        else if(channel.equals(WX_PUB)){
            Map<String, String> extra = new HashMap<String, String>();
            extra.put("open_id",open_id);
            chargeParams.put("extra", extra);
        }
        else if(channel.equals(WX_PUB_QR)){
            Map<String, String> extra = new HashMap<String, String>();
            extra.put("product_id",order_no);
            chargeParams.put("extra", extra);
        }
        return Charge.create(chargeParams);
    }

    private String getIp(){//获得客户端的IP,如果有更好的方法可以直接代替
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
                || "null".equalsIgnoreCase(ip))    {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
                || "null".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
                || "null".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    //ping++服务器回调函数,确认支付成功
    public PingppSuccess Webhooks() throws Exception {
        String signatureString=request.getHeader("x-pingplusplus-signature");
        BufferedReader reader = request.getReader();
        StringBuffer buffer = new StringBuffer();
        String string;
        while ((string = reader.readLine()) != null) {
            buffer.append(string);
        }
        reader.close();
        if(verifyData(buffer.toString(),signatureString,getPubKey())){//如果验证签名成功,证明确实是ping++服务器回调的信息
            Event event = Webhooks.eventParse(buffer.toString());
            JSONObject jv =JSONObject.parseObject(event.getData().getObject().toString());
            logger.info("the response json is "+event.getData().getObject().toString());
            PingppSuccess pingSuccess =new PingppSuccess();
            pingSuccess.setAmount(Integer.parseInt(jv.getString("amount")));
            pingSuccess.setOrder_no(jv.getString("order_no"));
            return pingSuccess;
        }
        else throw new Exception("verify failed");
    }


    public boolean verifyData(String dataString, String signatureString, PublicKey publicKey)
            throws NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException, InvalidKeyException {
        byte[] signatureBytes = Base64.decodeBase64(signatureString);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(dataString.getBytes("UTF-8"));
        return signature.verify(signatureBytes);
    }

    //函数根据ping++公钥地址获得ping++公钥内容,,来自ping++demo
    private PublicKey getPubKey() throws Exception {
        String pubKeyString = getStringFromFile(PUBKEYPATH);
        pubKeyString = pubKeyString.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
        byte[] keyBytes = Base64.decodeBase64(pubKeyString);

        // generate public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);
        return publicKey;
    }

    //函数从文件中获得内容,来自ping++demo
    private String getStringFromFile(String filePath) throws Exception {
        FileInputStream in = new FileInputStream(filePath);
        InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
        BufferedReader bf = new BufferedReader(inReader);
        StringBuilder sb = new StringBuilder();
        String line;
        do {
            line = bf.readLine();
            if (line != null) {
                if (sb.length() != 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }
        } while (line != null);

        return sb.toString();
    }
}
