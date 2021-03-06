package com.atguigu.gmall.pms.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.atguigu.core.bean.Resp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by shkstart on 2019/10/30.
 */
@RequestMapping("pms/oss")
@RestController
public class OSSController {

    @GetMapping("policy")
    public Resp<Object> policy() {

        //用户登录名称 caining@1966869242392253.onaliyun.com
        //AccessKey ID LTAI4FkA2x5wQw7FJuo3yGRg
        //AccessKeySecret 9e3a4GkQ3ar5hjuXPBs1hNN5Vk9qnP

        // 请填写您的AccessKeyId。
        String accessId = "LTAI4FkA2x5wQw7FJuo3yGRg";

        // 请填写您的AccessKeySecret。
        String accessKey = "9e3a4GkQ3ar5hjuXPBs1hNN5Vk9qnP";

        // 请填写您的 endpoint。
        String endpoint = "oss-cn-shanghai.aliyuncs.com";

        // 请填写您的 bucketname 。
        String bucket = "scw-20190615-cn1";

        // host的格式为 bucketname.endpoint
        String host = "https://" + bucket + "." + endpoint;

        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        // 用户上传文件时指定的前缀。
        String dir = format.format(date);

        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));

            return Resp.ok(respMap);

        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        }

        return null;
    }
}
