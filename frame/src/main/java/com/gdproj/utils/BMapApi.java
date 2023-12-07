package com.gdproj.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
//@ConfigurationProperties(prefix = "api.baidu")
//@ConditionalOnProperty(prefix = "api.baidu",name = "use",havingValue = "true")
public class BMapApi {

    private String ak = "NIfWNvb64zfVGcpCBTnwgFlpc24NPC1Q";
    
    /**
     * 逆地理编码
     */
    private String REVERSE_GEOCODING_API = "https://api.map.baidu.com/reverse_geocoding/v3/";

    private static Set<String> supportedCoordtype = new HashSet<>();

    static {
        /**
         * 目前支持的坐标类型包括：bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标，仅限中国）、wgs84ll（ GPS经纬度）
         */
        supportedCoordtype.add("bd09ll");
        supportedCoordtype.add("bd09mc");
        supportedCoordtype.add("gcj02ll");
        supportedCoordtype.add("wgs84ll");
    }

    /**
     * 全球逆地理编码服务
     * @param lng
     * @param lat
     * @param coordtype 坐标系
     * @return BMapApiResult
     * @see <a href="https://lbsyun.baidu.com/index.php?title=webapi/guide/webservice-geocoding-abroad">...</a>
     */
    public Object reverseGeocoding(BigDecimal lng, BigDecimal lat, String coordtype){
        String location = lat.toString() + "," + lng.toString();
        Map<String,Object> params = new HashMap<>();
        params.put("ak",getAk());
        params.put("output","json");
        params.put("coordtype",coordtype);
        params.put("location",location);
        return JSON.parseObject(HttpUtil.get(REVERSE_GEOCODING_API,params));
    }

    /**
     *
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    public Object reverseGeocoding(BigDecimal lng,BigDecimal lat){
        return reverseGeocoding(lng, lat,"wgs84ll");
    }

    /**
     * 支持的坐标系
     * @param coordtype 坐标系名称
     * @return 是否支持
     */
    public boolean supportCoordtype(String coordtype){
        return supportedCoordtype.contains(coordtype);
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

}
