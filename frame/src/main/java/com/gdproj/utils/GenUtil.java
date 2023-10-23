package com.gdproj.utils;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

public class GenUtil {

    public static void main(String[] args)
    {
         //121.717594,31.12055    121.817629,31.090867
//        智慧谷到爱琴海
        GlobalCoordinates source = new GlobalCoordinates(121.266038,30.207474);
        GlobalCoordinates target = new GlobalCoordinates(121.283169,30.201591);

        double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);
        double meter2 = getDistanceMeter(source, target, Ellipsoid.WGS84);

        System.out.println("Sphere坐标系计算结果智慧谷到爱琴海："+meter1 + "米");
        System.out.println("WGS84坐标系计算结果智慧谷到爱琴海："+meter2 + "米");


        //坐标系
        Ellipsoid ellipsoid = Ellipsoid.WGS84;
        //起始位置
        GlobalCoordinates start = new GlobalCoordinates(31.00001, 121.000001);
        //结束位置
        GlobalCoordinates end = new GlobalCoordinates(32.00005, 122.000005);
        //距离 单位 米
        double distance = 50;

        //计算大地测量曲线
        GeodeticCurve geodeticCurve = getCalculateGeodeticCurve(ellipsoid,start,end);
        //启动轴承（度）
        double startBearing = geodeticCurve.getAzimuth();

        GlobalCoordinates globalCoordinates = getulateEndingGlobalCoordinates(ellipsoid,start,startBearing,distance);

        System.out.println(globalCoordinates.toString());


        GlobalCoordinates distanceLonLat = getDistanceLonLat(Ellipsoid.WGS84, 23.010957255877614, 113.08593458404175, 23.04383276093276, 113.1036753731014, -5000.00);
        System.out.println("11111111111111111111111111");
        System.out.println(distanceLonLat.toString());
    }


    /**
     * 原来计算两个之间某个点经纬度
     * @param ellipsoid 坐标系
     * @param startLat 起始纬度
     * @param startLng 起始经度
     * @param startLat 结束纬度
     * @param startLng 结束经度
     * @param startLat 距离
     * @return
     */
    public static GlobalCoordinates getDistanceLonLat(Ellipsoid ellipsoid, Double startLat, Double startLng, Double endLat, Double endLng, Double distance)
    {

        //起始位置
        GlobalCoordinates start = new GlobalCoordinates(startLat, startLng);
        //结束位置
        GlobalCoordinates end = new GlobalCoordinates(endLat, endLng);
        //距离 单位 米
        //double distance = distance;

        //计算大地测量曲线
        GeodeticCurve geodeticCurve = getCalculateGeodeticCurve(ellipsoid,start,end);
        //启动轴承（度）
        double startBearing = geodeticCurve.getAzimuth();

        GlobalCoordinates globalCoordinates = getulateEndingGlobalCoordinates(ellipsoid,start,startBearing,distance);

        System.out.println(globalCoordinates.toString());
        return globalCoordinates;
    }


    /**
     * 计算两个点之间的距离
     * @param gpsFrom
     * @param gpsTo
     * @param ellipsoid
     * @return
     */
    public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid)
    {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }

    /**
     * 计算指定基准上两点之间的大地测量曲线
     * @param start 起始位置
     * @param end 结束位置
     * @param ellipsoid 坐标系
     * @return
     */
    public static GeodeticCurve getCalculateGeodeticCurve(Ellipsoid ellipsoid, GlobalCoordinates start, GlobalCoordinates end)
    {
        GeodeticCurve geodeticCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid,start,end);
        return geodeticCurve;
    }

    /**
     * 计算指定基准上两点之间的大地测量曲线
     * @param start 起始位置
     * @param distance 距离
     * @param ellipsoid 坐标系
     * @param startBearing 大地测量曲线
     * @return
     */
    public static GlobalCoordinates getulateEndingGlobalCoordinates(Ellipsoid ellipsoid, GlobalCoordinates start, double startBearing, double distance)
    {
        GlobalCoordinates globalCoordinates = new GeodeticCalculator().calculateEndingGlobalCoordinates(ellipsoid,start,startBearing,distance);
        return globalCoordinates;
    }

}