package com.gdproj.utils;

import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownloadUtils {
    public static void downloadExcel(String fileName, HttpServletResponse response) {
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());


            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + "sign.xlsx");
            // 设置响应头
            response.setContentType("application/octet-stream");

            //创建存放文件内容的数组
            byte[] buff = new byte[1024];
            //所读取的内容使用n来接收
            int n;
            //当没有读取完时,继续读取,循环
            while ((n = fis.read(buff)) != -1) {
                //将字节数组的数据全部写入到输出流中
                outputStream.write(buff, 0, n);
            }
            //强制将缓存区的数据进行输出
            outputStream.flush();
            //关流
            outputStream.close();
            fis.close();

        } catch (IOException e) {
            throw new SystemException(AppHttpCodeEnum.DOWNLOAD_EXCEL_ERROR);
        }

    }
}
