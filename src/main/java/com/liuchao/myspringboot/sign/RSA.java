package com.liuchao.myspringboot.sign;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * RSA的签名及验签 可以保证接口的参数中间不被篡改
 * @author zhouyy
 *
 */
public class RSA {

    private static final String SIGN_TYPE_RSA = "RSA";

    private static final String SIGN_TYPE_RSA2 = "RSA2";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private static final String privateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCK2AbFld+rEwYUIBqqpaE6Ezk2+cCJ1tab+Pcg+MdSZuJ0gmNFsbcivLYvP1LfnaCLqO8liSiVhzQAuDTDSoIH9HaaUUJs/OXN89TOMHCn4ZdBNkhTKvIREKMsaqBPT9xADElNGmfFxiQEIeuOBPQhZIhijbUCMepY0j89CoGuH91cBxKsfzJ8fTpjbHU1rfK15cqaiWqzwRszNRMV5Ds0bggYneLJiGFLX07QCxO3ue8B1Zlkdzi52c2i0tnA8g0qu+LHzuY4UcWFy2nDZextopUL2vCtHIQOQU8IKcj4G6vjOVmy9zcdfpD6pjbOTfPUb/v0Wao8oGPI6TO6aoi3AgMBAAECggEAas6aDv8nK6QbxfmYafO2HkXRerAP5DlHqH6SJWQbeRE6XWQ8V83JUsiW4au15d2NNkKDjX2Aod57K56IwD6d3t65KicekVbOtWtAJklvMEZDI+BNLOVZh7UjwTRZFAocJY0OCmkosSSmaiNp4DuBTfrXS/E08HZTj3ZVPXDKp5YmbNgvnvvhV7uvATubBah2a6eTfUFTKioxOyHKPStt3y/zqsgVo9qdPinEogwlCd8N8fIcTBFYeU2INf2PYLcPwCICaciuS5VADBD0TwC+P+Cv8FBuzy3LeYveFzL9WL+Mi4OeNS//9Ug9rhQOLkd17RqAPn38X78CbUyUgreeYQKBgQDjke2221jZl8AZ9IERdduQ7U9WC1v6XbEtwazzG6a5ggIM7NcTb/F7Uw88Z81hOHIoGa7pmS2ZSblmNmZnwTmCqG1KAusQ0cF2mai4UaPOy62zDsTDTQJ4paX0N1gA/i0WlJHScbL2HwZ1Yj+5AKe7CP1XDyhSp5aOf0fObQV28QKBgQCcMHxdQhvzgtPUw3Zwt4B3M4rluvMU/Pl7PF8f2xgp9B62URCF5N+p2WyUe4CfeGtzKGVWBZVKTKXICDk2hUa+daThx495jROSm8x9Ve0hBDQFkGX67H8KLZSER/c/dFbYZo4W9trNJXyD+/VGNt/ZcJ47DCYcT0wWjolxxvAKJwKBgEs8fK3euPI/ZGhwWJeQMoYonvkgikks3p31ERvSwX1FSS5CwxyO/vQ50qiy3MjOo5c0XoqmdNff8uajB5o33K6gxnkYm+SUpHR0er5tZ03AMu4m7NBwzmdaxJIIFhVF81wqxEciBfuu6DC4yb59Q/bnEvLMdRVWiMUVuYS6sHrRAoGBAI+/vCYkuw7yYL3YK8Dv0odi47otxp2RepemZ7Is9l9ec4m+sCNbE1MCbgF2bVnX/aSeEC4Ms2sPgiDJGMcKAKM4KUk3XuAzLElppNnR5I3XjX11ebZoRT/71U2t4mv/ShKeyLVv+s6GasQad5Jo3LsGAEs+EjFJ9jBofS0/OBtPAoGBAIO5Nfih0Rf/4W1s1o8cavUzzqmycKoSsN0qmsGNsGSnqqaKKhYTj3QfNn/DgodSHm2PiAy9ehyRky1YAshjUSAkzrB2pyMGj33LhStdpjrzyjwL0xsFkrcw067ZeQdUbpbRJZk8vxkWji0tJEg8ziUE8wOdqBORb9YKKsjScATD";

    private static final String publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAitgGxZXfqxMGFCAaqqWhOhM5NvnAidbWm/j3IPjHUmbidIJjRbG3Iry2Lz9S352gi6jvJYkolYc0ALg0w0qCB/R2mlFCbPzlzfPUzjBwp+GXQTZIUyryERCjLGqgT0/cQAxJTRpnxcYkBCHrjgT0IWSIYo21AjHqWNI/PQqBrh/dXAcSrH8yfH06Y2x1Na3yteXKmolqs8EbMzUTFeQ7NG4IGJ3iyYhhS19O0AsTt7nvAdWZZHc4udnNotLZwPINKrvix87mOFHFhctpw2XsbaKVC9rwrRyEDkFPCCnI+Bur4zlZsvc3HX6Q+qY2zk3z1G/79FmqPKBjyOkzumqItwIDAQAB";

    /**
     * RSA/RSA2 生成签名
     *
     * @param map
     *            包含 sign_type、privateKey、charset
     * @return
     * @throws Exception
     */
    public static String rsaSign(Map map) throws Exception {
        PrivateKey priKey = null;
        java.security.Signature signature = null;
        String signType = map.get("sign_type").toString();
        String privateKey = map.get("privateKey").toString();
        String charset = map.get("charset").toString();
        String content = getSignContent(map);
        map.put("content", content);
        System.out.println("请求参数生成的字符串为:" + content);
        if (SIGN_TYPE_RSA.equals(signType)) {
            priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));
            signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
        } else if (SIGN_TYPE_RSA2.equals(signType)) {
            priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));
            signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
        } else {
            throw new Exception("不是支持的签名类型 : : signType=" + signType);
        }
        signature.initSign(priKey);

        if (StringUtils.isEmpty(charset)) {
            signature.update(content.getBytes());
        } else {
            signature.update(content.getBytes(charset));
        }

        byte[] signed = signature.sign();

        return new String(Base64.encodeBase64(signed));

    }

    /**
     * 验签方法
     *
     *  content
     *            参数的合成字符串格式: key1=value1&key2=value2&key3=value3...
     * @param sign
     *  publicKey
     *  charset
     *  signType
     * @return
     */
    public static boolean rsaCheck(Map map, String sign) throws Exception {
        java.security.Signature signature = null;
        String signType = map.get("sign_type").toString();
        String privateKey = map.get("privateKey").toString();
        String charset = map.get("charset").toString();
        String content = map.get("content").toString();
        String publicKey = map.get("publicKey").toString();
        System.out.println(">>验证的签名为:" + sign);
        System.out.println(">>生成签名的参数为:" + content);
        PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
        if (SIGN_TYPE_RSA.equals(signType)) {
            signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
        } else if (SIGN_TYPE_RSA2.equals(signType)) {
            signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
        } else {
            throw new Exception("不是支持的签名类型 : signType=" + signType);
        }
        signature.initVerify(pubKey);

        if (StringUtils.isEmpty(charset)) {
            signature.update(content.getBytes());
        } else {
            signature.update(content.getBytes(charset));
        }

        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        byte[] encodedKey = readText(ins).getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        StringWriter writer = new StringWriter();
        io(new InputStreamReader(ins), writer, -1);

        byte[] encodedKey = writer.toString().getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    /**
     * 把参数合成成字符串
     *
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuffer content = new StringBuffer();
        // app_id,method,charset,sign_type,version,bill_type,timestamp,bill_date
        String[] sign_param = sortedParams.get("sign_param").split(",");// 生成签名所需的参数
        List<String> keys = new ArrayList<String>();
        for (int i = 0; i < sign_param.length; i++) {
            keys.add(sign_param[i]);
        }
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            /*if ("biz_content".equals(key)) {
                content.append(
                        (index == 0 ? "" : "&") + key + "={\"bill_date\":\"" + sortedParams.get("bill_date") + "\",")
                        .append("\"bill_type\":\"" + sortedParams.get("bill_type") + "\"}");
                index++;
            } else {*/
            String value = sortedParams.get(key);
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
//            }
        }
        return content.toString();
    }

    private static String readText(InputStream ins) throws IOException {
        Reader reader = new InputStreamReader(ins);
        StringWriter writer = new StringWriter();

        io(reader, writer, -1);
        return writer.toString();
    }

    private static void io(Reader in, Writer out, int bufferSize) throws IOException {
        if (bufferSize == -1) {
            bufferSize = DEFAULT_BUFFER_SIZE >> 1;
        }

        char[] buffer = new char[bufferSize];
        int amount;

        while ((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String,String> map=new HashMap<>();
        //配置的参数
        map.put("sign_type",RSA.SIGN_TYPE_RSA2);
        map.put("privateKey",RSA.privateKey);
        map.put("charset","utf-8");
        //生成签名用的参数 说明id name password 是需要传递 的
        map.put("sign_param","password,name,id");
        map.put("id","123");
        map.put("name","张三");
        map.put("password","2344453");

        String s = RSA.rsaSign(map);
        System.out.println(s);

        Map<String,String> pubmap=new HashMap<>();
        pubmap.put("sign_type",RSA.SIGN_TYPE_RSA2);
        pubmap.put("privateKey",RSA.privateKey);
        pubmap.put("charset","utf-8");

        pubmap.put("publicKey",RSA.publicKey);
        pubmap.put("content", map.get("content"));
        boolean b = RSA.rsaCheck(pubmap, s);
        System.out.println(b);


    }

}