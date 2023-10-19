package com.gdproj.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * @author shmily
 * @description java实现百度地图定位
 */
public class BaiduMapUtil {

    public static void main(String[] args) {
        System.out.println(getAddress(""));
    }


    public static String getAddress(String ip) {
        String address = "";
        String ak = "NIfWNvb64zfVGcpCBTnwgFlpc24NPC1Q";
        try {
            // 这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
            JSONObject resultJson = readJsonFromUrl("http://api.map.baidu.com/location/ip?ip=" + ip + "&ak=" + ak + "&coor=bd09ll");
            //resultJson 是返回结果，当前只取位置信息
            System.out.println(resultJson);
            address = ((JSONObject) resultJson.get("content")).getString("address");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    /**
     * 读取
     *
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * 创建链接
     *
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     */
    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = JSONObject.parseObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String getLocation() {
        String ak = "NIfWNvb64zfVGcpCBTnwgFlpc24NPC1Q";
        String address = "sdds";
//        String httpUrl = "http://api.map.baidu.com/geocoding/v3/?address="+address+ ak;
        String httpUrl = "http://api.map.baidu.com/geocoding/v3/?address=" + address + "&output=json&ak=" + ak;
        StringBuilder json = new StringBuilder();
        try {
            URL url = new URL(httpUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        String result = null;
        String data = json.toString();
        if (data != null && !"".equals(data)) {
            Map map = JSON.parseObject(data, Map.class);
            if ("0".equals(map.getOrDefault("status", "500").toString())) {
                Map childMap = (Map) map.get("result");
                Map posMap = (Map) childMap.get("location");
                double lng = Double.parseDouble(posMap.getOrDefault("lng", "0").toString()); // 经度
                double lat = Double.parseDouble(posMap.getOrDefault("lat", "0").toString()); // 纬度
                DecimalFormat df = new DecimalFormat("#.######");
                String lngStr = df.format(lng);
                String latStr = df.format(lat);
                result = lngStr + "," + latStr;
                System.out.println("坐标" + result);
            }
        }
        return result;
    }


}
