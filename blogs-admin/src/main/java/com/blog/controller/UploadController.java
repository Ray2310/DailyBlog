package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 * @author Ray2310
 */
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    //todo 上传文件（图片）
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}