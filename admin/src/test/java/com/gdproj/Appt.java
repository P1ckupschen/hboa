package com.gdproj;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
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
import java.util.ArrayList;
import java.util.Date;
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
}
