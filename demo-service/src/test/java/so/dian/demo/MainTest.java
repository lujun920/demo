/*
 * Dian.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package so.dian.demo;

import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * TODO
 * @author ${baizhang}
 * @version $Id: MainTest.java, v 0.1 2018-04-23 上午9:09 Exp $
 */
public class MainTest {

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        //System.out.println("0.05 + 0.01= "+(0.05 + 0.01));
        //System.out.println("1.0 - 0.42= "+(1.0 - 0.42));
        //System.out.println("4.015 * 100= "+(4.015 * 100));
        //System.out.println("123.3 / 100= "+(123.3 / 100));


        //long startTime= System.currentTimeMillis();
        //for(int i = 0; i< 1000000; i++){
        //    RadomCodeUtil.generateTextCode(RadomCodeUtil.TYPE_NUM_LOWER, 6, null);
        //}
        //long endTime= System.currentTimeMillis();
        //System.out.println((endTime- startTime)+"ms");

//        List<String> list= new ArrayList<>();
//        list.add("1111");
//        list.add("2222");
//        list.add("3333");
//        list.add("4444");
//        //System.out.println(JSON.toJSONString(list));
//
//        RateLimiter r = RateLimiter.create(10);
//        while (true) {
//            System.out.println(r.acquire());
//        }

//        String uaStr= "Mozilla/5.0 (iPhone; CPU iPhone OS 12_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16B92; SNYifubao/6.5.20";
//        UserAgentAnalyzer uaa = UserAgentAnalyzer
//                .newBuilder()
//                .hideMatcherLoadStats()
//                .withCache(10000)
//                .build();
//
//        UserAgent agent = uaa.parse(uaStr);
//
//        for (String fieldName : agent.getAvailableFieldNamesSorted()) {
//            System.out.println(fieldName + " = " + agent.getValue(fieldName));
//        }
//        System.out.println(RandomCodeUtil.generateTextCode(RandomCodeUtil.TYPE_ALL_MIXED, 22, null));


//        String sert= "qo5q2rEGlbmefKfU";
//        String base64= "1yAzY6FuFnZw3wCJDMT%2Blw%3D%3D";
//
//        String aaa = URLDecoder.decode(base64, "UTF-8");
//
////        SecretKeySpec secretKeySpec = new SecretKeySpec("qo5q2rEGlbmefKfU"
////                .getBytes(), Constants.AES);
//
//        Cipher c = Cipher.getInstance(null);
//        c.init(Cipher.DECRYPT_MODE, secretKeySpec);
//
//        String val= AESUtil.decryptData(secretKeySpec, c, aaa);
//        System.out.println(val);
    }
}