package com.gdproj;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.gdproj.entity.Deployee;
import com.gdproj.entity.SignExcelEntity;
import com.gdproj.utils.AesUtil;
import com.gdproj.utils.BMapApi;
import com.gdproj.utils.RSAUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Test
    public void aesTest(){
        String pw = "123456";
        System.out.println(DesensitizedUtil.mobilePhone("17395712681"));
        String encrypt = AesUtil.encrypt(pw, AesUtil.key128);
        String decrypt = AesUtil.decrypt(encrypt, AesUtil.key128);
        System.out.println("原密码：" + pw);
        System.out.println("加密后：" + encrypt);
        System.out.println("解密后：" + decrypt);



    }

    @Test
    public void wxSubscribeTest(){
        System.out.println(HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN", "1tyty"));

        Deployee deployee = new Deployee();
        deployee.setDeployeePhone("17395712681");
        System.out.println(AesUtil.encrypt(deployee.getDeployeePhone(),AesUtil.key128));
        System.out.println(RSAUtil.decrypt("hzIaztKQMS51FwRJKCogCEIerHW+rK721x4ys1lrY7WouJZjxjTY98/VuU27MJGGLI0pyisB3PYzWKImgegKj2SIL68zeVtOxAUXU+u10mwHMDl+5s9Q+XZpq+DlnAtvPFnS+jYOEW8dAzRq6tQVvFYtooAOezrJLQyzswS8bI5UeEVqQEl1cZc6q6dfCy/ZZ0A5jWU2oCMW5ZrWbK0TD1T8Ji1aDEa14Htu9k70dXK4kHpIfAeNaLn2Xzw5DmBqt9huRwapIMkcips9m/g3ZKoqaMnYSxd4tQKv43bzw65Bmh+8drhHpCzQJLYnERrfrdGpxu1oeXk1DsuJVKTKmw=="));
        System.out.println(new Date());
    }
    @Test
    public void jedisTest(){
        /*创建Jedis对象，参数为Redis服务的ip和端口*/
        Jedis jedis = new Jedis("127.0.0.1",6379);
        /*通过输出jedis.ping() 若输出PONG 则可以成功连接Redis服务*/
        System.out.println(jedis.ping());
        /**
         * 通过jedis的set方法存储值、get方法获取值
         * */
        jedis.set("a","999");
        System.out.println(jedis.get("a"));

    }
    @Test
    public void BaiduTest() throws SQLException {
//        "lng":121.26294815548036,"lat":30.186127111198787
        BMapApi bMapApi = new BMapApi();
        JSONObject o = (JSONObject) bMapApi.reverseGeocoding(BigDecimal.valueOf(121.26294815548036), BigDecimal.valueOf(30.186127111198787));
        System.out.println(o.get("result"));
        System.out.println(JSONUtil.parseObj(o.get("result")).get("formatted_address"));
        Connection connection = DriverManager.getConnection("jdbc:mysql://39.105.102.72:3306/lvzy?" + "user=lvzy&password=Fez4gjDUvf8zj86j."+ "&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true");
        System.out.println(connection);
    }

    @Test
    public void timeTest() throws ParseException {
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = sdf.parse("2024-03-01 00:00:00");
        instance.setTime(parse);
//        instance.set(2024,2,1);
        instance.add(Calendar.DATE,-1);
        System.out.println(instance.get(Calendar.DATE));

        String  time = "2024-02-01";
        SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf1.parse(time));
        Calendar instance1 = Calendar.getInstance();
        instance1.setTime(sdf1.parse(time));
        System.out.println(instance1.get(Calendar.DATE));

    }

    @Test
    public void hutoolExcel (){
//        File templateFile = new File("G:/桌面/excel文件.xlsx");
//        File outputFile = new File("G:/桌面/bbb.xlsx");
//        ExcelReader reader = ExcelUtil.getReader("G:/桌面/excel文件.xlsx");
//
//        List<List<Object>> rowObjects = reader.read();
//        System.out.println(rowObjects);

        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("姓名", "张三");
        row1.put("年龄", 23);
        row1.put("成绩", 88.32);
        row1.put("是否合格", true);
        row1.put("考试日期", DateUtil.date());

        Map<String, Object> row2 = new LinkedHashMap<>();
        row2.put("姓名", "李四");
        row2.put("年龄", 33);
        row2.put("成绩", 59.50);
        row2.put("是否合格", false);
        row2.put("考试日期", DateUtil.date());

        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);

        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/writeMapTest.xlsx");
// 合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "一班成绩单");
// 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
// 关闭writer，释放内存
        writer.close();

//        //读取数据，指定标题行和起始数据行
//        List<Map<String, Object>> rowMaps = reader.read(1, 2, Integer.MAX_VALUE);
//        System.out.println(rowMaps);
//
//        //读取数据，指定标题行和起始数据行，转换为对象
//        reader.addHeaderAlias("名称", "name");
//        reader.addHeaderAlias("数值", "value");
//        List<ZhenquanReport> reports = reader.read(1, 2, Integer.MAX_VALUE, ZhenquanReport.class);
//        System.out.println(reports);
//
//        //读取指定单元格
//        String value = String.valueOf(reader.readCellValue(2,2));
//        System.out.println(value);
//
//        //关闭
//        reader.close();
//
//        //===========================写入测试=====================================
//        ExcelWriter excelWriter = new ExcelWriter(templateFile);
//        excelWriter.writeCellValue(3, 2, "error"); //写入值，x=列、y=行号
//
//        //设置单元格样式
//        CellStyle cellStyle = excelWriter.createCellStyle(3, 2);
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //设置背景色
//        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//        cellStyle.setBorderBottom(BorderStyle.DASHED); //设置边框线条与颜色
//        cellStyle.setBottomBorderColor(IndexedColors.PINK.getIndex());
//        Font font = excelWriter.createFont();
//        font.setColor(IndexedColors.RED.getIndex());
//        cellStyle.setFont(font); //设置字体
//
//        //设置输出文件路径
//        excelWriter.setDestFile(outputFile);
//        excelWriter.close();

    }
}
