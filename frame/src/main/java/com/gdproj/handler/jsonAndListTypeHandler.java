package com.gdproj.handler;

import com.alibaba.fastjson.JSON;
import com.gdproj.vo.fileVo;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * @tableField  自定义typehandler
 * 字段list接收前端 json数组 并转化varchar存入数据库
 * 读取的时候也是用varchar 转list
 */

public class jsonAndListTypeHandler extends BaseTypeHandler<List<fileVo>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<fileVo> fileVos, JdbcType jdbcType) throws SQLException {
        String listStr = JSON.toJSONString(fileVos);

        preparedStatement.setString(i,listStr);
    }

    @Override
    public List<fileVo> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String o = resultSet.getString(s);

        List<fileVo> list = JSON.parseArray(o,fileVo.class);

        return list;
    }

    @Override
    public List<fileVo> getNullableResult(ResultSet resultSet, int i) throws SQLException {

        String o = resultSet.getString(i);

        List<fileVo> list = JSON.parseArray(o,fileVo.class);

        return list;
    }

    @Override
    public List<fileVo> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String o = callableStatement.getString(i);

        List<fileVo> list = JSON.parseArray(o,fileVo.class);

        return list;
    }
}
