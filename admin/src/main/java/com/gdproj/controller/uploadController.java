package com.gdproj.controller;


import cn.hutool.core.io.resource.InputStreamResource;
import com.gdproj.annotation.autoLog;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.FileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@Api(tags = "上传功能")
@Slf4j
public class uploadController {

    @Value("${ImagesFilePath}")
    String filePath;

    @PostMapping("/commonUpload")
    @autoLog
    @ApiOperation(value = "上传")
    public ResponseResult commonUpload(@RequestBody MultipartFile file, HttpServletRequest request){
//        String folder = "admin/src/main/resources/images";
        String folder =filePath;
        if (file == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_CONTENT_NULL);
        }
        if (file.getSize() > 1024 * 1024 * 20) {
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_SIZE_MAX);
        }

        //获取文件后缀
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
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

    @PostMapping(value = "/downloadFile")
    public ResponseEntity<InputStreamResource> downloadCode(@RequestBody FileVo fv) {
//        log.info("request param: {}", pf);

        String filename = fv.getName() + System.currentTimeMillis() + ".zip";
        byte[] bytes =  new byte[1024];
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(bais));
    }

}
