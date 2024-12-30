package com.qingfeng.electronic.modules.back.file.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.qingfeng.electronic.modules.back.file.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/7
 */
@Service
public class FileService {

    /**
     * 上传文件
     * @param file
     * @param avter
     * @return
     */
    public String uploadFile(MultipartFile file, String avter) throws IOException {
        return getImgUrl(file, avter);
    }


    private String getImgUrl(MultipartFile file, String name) throws IOException {
        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        OSS ossClient = getClient();


        //文件名：uuid.扩展名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        String key = name + "/" + fileName + "-" + file.getOriginalFilename();

        //文件上传至阿里云
        ossClient.putObject(ConstantPropertiesUtils.BUCKET, key, file.getInputStream());

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回url地址
        return "https://" + ConstantPropertiesUtils.BUCKET + "." + ConstantPropertiesUtils.ENDPOINT + "/" + key;
    }

    private OSS getClient() {
        OSS ossClient = getOssClient();
        if (!ossClient.doesBucketExist(ConstantPropertiesUtils.BUCKET)) {
            //创建bucket
            ossClient.createBucket(ConstantPropertiesUtils.BUCKET);
            //设置oss实例的访问权限：公共读
            ossClient.setBucketAcl(ConstantPropertiesUtils.BUCKET, CannedAccessControlList.PublicRead);
        }
        return ossClient;
    }

    private OSS getOssClient() {
        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        return new OSSClientBuilder().build(
                ConstantPropertiesUtils.ENDPOINT,
                ConstantPropertiesUtils.ACCESS_KEY_ID,
                ConstantPropertiesUtils.SECRECT);
    }

    /**
     * 删除文件
     * @param fileUrl
     */
    public void removeFile(String fileUrl) {
        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        OSS ossClient = getOssClient();
        String host = "https://" + ConstantPropertiesUtils.BUCKET + "." + ConstantPropertiesUtils.ENDPOINT + "/";

        // 删除文件。
        ossClient.deleteObject(ConstantPropertiesUtils.BUCKET,
                fileUrl.substring(host.length()));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
