package com.gdproj.handler;

import com.alibaba.fastjson.JSON;
import com.gdproj.vo.FileVo;
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

public class jsonAndListTypeHandler extends BaseTypeHandler<List<FileVo>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<FileVo> fileVos, JdbcType jdbcType) throws SQLException {
        String listStr = JSON.toJSONString(fileVos);

        preparedStatement.setString(i,listStr);
    }

    @Override
    public List<FileVo> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String o = resultSet.getString(s);

        List<FileVo> list = JSON.parseArray(o, FileVo.class);

        return list;
    }

    @Override
    public List<FileVo> getNullableResult(ResultSet resultSet, int i) throws SQLException {

        String o = resultSet.getString(i);

        List<FileVo> list = JSON.parseArray(o, FileVo.class);

        return list;
    }

    @Override
    public List<FileVo> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String o = callableStatement.getString(i);

        List<FileVo> list = JSON.parseArray(o, FileVo.class);

        return list;
    }
}
