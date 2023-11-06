package com.gdproj;

import com.gdproj.entity.SignExcelEntity;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Appt {

    @Test
    public void exportExcel(){
        String fileName = "E:\\sch-work-space\\projects\\demo2\\src\\main\\resources\\static\\excelnew3.xlsx";
        List<SignExcelEntity> userList =new ArrayList<>();
        System.out.println(Math.round(2/3.0 *100.0)/100.0);


//        excelEntity user01=new excelEntity(1,"李磊","男",1000.91,new Date());
//        excelEntity user02=new excelEntity(2,"张三","男",2000.33,new Date());
//        excelEntity user03=new excelEntity(3,"李四","男",3000.90,new Date());
//        excelEntity user04=new excelEntity(4,"王五","男",7000.90,new Date());
//        userList.add(user01);
//        userList.add(user02);
//        userList.add(user03);
//        userList.add(user04);
//        System.out.println(new Date());
//        EasyExcel.write(fileName,excelEntity.class).sheet("用户信息").doWrite(userList);

    }
}
