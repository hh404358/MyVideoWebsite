package com.hahaha.utils;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.google.gson.Gson;
import com.hahaha.exception.SystemException;
import com.hahaha.propertites.AliOSSProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import com.hahaha.enums.AppHttpCodeEnum;
/**
 * 阿里云 OSS 工具类
 */
@Component
@Slf4j
public class AliOSSUtils {

   @Autowired
   private AliOSSProperties aliOSSProperties;

    public AliOSSProperties getAliOSSProperties() {
        return aliOSSProperties;
    }

    public void setAliOSSProperties(AliOSSProperties aliOSSProperties) {
        this.aliOSSProperties = aliOSSProperties;
    }

    /**
     * 实现上传图片到OSS
     */
    public String uploadImgae(MultipartFile file) throws IOException {
        String endpoint = aliOSSProperties.getEndpoint();
        String accessKeyId = aliOSSProperties.getAccessKeyId();
        String accessKeySecret = aliOSSProperties.getAccessKeySecret();
        String bucketName = aliOSSProperties.getBucketName();

         //log.info("endpoint:{},accesskeyId:{},accessKeySecret:{},bucketName:{}",endpoint,accessKeyId,accessKeySecret,bucketName);
        // 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();

        //获取原始文件名
        String originalFilename = file.getOriginalFilename();

        // 获取文件大小
        long fileSize = file.getSize();

        // 判断文件大小是否超过2MB（2MB=2*1024*1024 bytes）
        if (fileSize > 2 * 1024 * 1024) {
            // 抛出文件大小超过限制的异常
            throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }

        //对原始文件名进行判断大小。只能上传png或jpg文件
        if(!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")){
            //FILE_TYPE_ERROR代表文件类型错误的提示
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        //PathUtils.generateFilePath(originalFilename)表示把原始文件名转换成指定文件名
        String filePath = PathUtils.generateFilePath(originalFilename);


        //上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, filePath, inputStream);

        //文件访问路径
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + filePath;
        // 关闭ossClient
        ossClient.shutdown();
        return url;// 把上传到oss的路径返回


    }

    /**
     * 实现上传视频到OSS
     */
    public String uploadVideo(MultipartFile file) throws IOException {
        String endpoint = aliOSSProperties.getEndpoint();
        String accessKeyId = aliOSSProperties.getAccessKeyId();
        String accessKeySecret = aliOSSProperties.getAccessKeySecret();
        String bucketName = aliOSSProperties.getBucketName();

        //log.info("endpoint:{},accesskeyId:{},accessKeySecret:{},bucketName:{}",endpoint,accessKeyId,accessKeySecret,bucketName);
        // 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();

        //获取原始文件名
        String originalFilename = file.getOriginalFilename();


        //PathUtils.generateFilePath(originalFilename)表示把原始文件名转换成指定文件名
        String filePath = PathUtils.generateFilePath(originalFilename);


        //上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, filePath, inputStream);

        //文件访问路径
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + filePath;
        // 关闭ossClient
        ossClient.shutdown();
        return url;// 把上传到oss的路径返回


    }







}
