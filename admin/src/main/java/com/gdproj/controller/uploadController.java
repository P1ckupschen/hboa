package com.gdproj.controller;


import com.gdproj.annotation.autoLog;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@Api(tags = "上传功能")
public class uploadController {

    @Value("${ImagesFilePath}")
    String filePath;

    @PostMapping("/commonUpload")
    @autoLog
    @ApiOperation(value = "上传")
    public ResponseResult commonUpload(@RequestBody MultipartFile file, HttpServletRequest request){

        System.out.println(file);
//        String folder = "admin/src/main/resources/images";
        String folder =filePath;
        if (file == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_CONTENT_NULL);
        }
        if (file.getSize() > 1024 * 1024 * 20) {
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_SIZE_MAX);
        }

        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (!"jpg,jpeg,png,pdf,doc,docx,xlsx,xls".toUpperCase().contains(suffix.toUpperCase())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        String savePath = folder;
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdir();
        }

        //通过UUID生成唯一文件名
        String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
        try {
            //将文件保存指定目录
            //file.transferTo(new File(savePath + filename));
            //File file1 = new File(file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(savePath+"/" +  filename));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_STORAGE_ERROR);
        }

        String url = String.valueOf(request.getRequestURL());
        String[] urlsplit = url.split("/upload/commonUpload");
        String baseurl = urlsplit[0];
        String result = "/images/"+filename;
//                String result = baseurl +"/images/"+filename;
        return ResponseResult.okResult(result);
        //返回文件名称
    }
}
