package com.blog.service.impl;

import com.blog.domain.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.service.UploadService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 上传文件到七牛云
 */
@ConfigurationProperties(prefix = "oss")
@Service
@Data
public class UploadServiceImpl implements UploadService {

    //todo 实现文件的上传
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件的大小
        //获取原始文件名进行判断
        String originalFilename = img.getOriginalFilename();
        if(!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")){
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //如果通过，上传文件到oss
        String url = uploadOSS(img);

        return ResponseResult.okResult(url);
    }

    private String accessKey;
    private String secretKey;
    private String bucket;


    private String uploadOSS(MultipartFile imgFile){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名

        //images目录下的文件
        String originalFilename = imgFile.getOriginalFilename();

        String key = "images/"+originalFilename;
        try {
            //将前端传过来的imgFile文件转换成一个inputStream，然后
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        //文件地址
        return "http://rrpanx30j.hd-bkt.clouddn.com/images/"+ originalFilename;
    }
}
