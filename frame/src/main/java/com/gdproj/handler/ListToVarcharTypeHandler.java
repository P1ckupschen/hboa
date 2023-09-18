package com.gdproj.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 类型转换器，用于数据库的varchar和Java中List<String>类型的相互转换
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
@Component
public class ListToVarcharTypeHandler implements TypeHandler<List<String>> {
 
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        // 遍历List类型的入参，拼装为String类型，使用Statement对象插入数据库
        StringBuffer sb = new StringBuffer();
        for (String string : strings) {
           sb.append(string).append(",");
        }
        String substring = sb.substring(0, sb.length() - 1);
        preparedStatement.setString(i, substring);
    }
 
    @Override
    public List<String> getResult(ResultSet resultList, String s) throws SQLException {
        // 获取String类型的结果，使用","分割为List后返回
        String resultString = resultList.getString(s);
        if (StringUtils.isNotEmpty(resultString)) {
            return Arrays.stream(resultString.split(",")).collect(Collectors.toList());
        }
        return null;
    }
 
    @Override
    public List<String> getResult(ResultSet resultList, int i) throws SQLException {
        // 获取String类型的结果，使用","分割为List后返回
        String resultString = resultList.getString(i);
        if (StringUtils.isNotEmpty(resultString)) {
            return Arrays.stream(resultString.split(",")).collect(Collectors.toList());
        }
        return null;
    }
 
    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        // 获取String类型的结果，使用","分割为List后返回
        String resultString = callableStatement.getString(i);
        if (StringUtils.isNotEmpty(resultString)) {
            return Arrays.stream(resultString.split(",")).collect(Collectors.toList());
        }
        return null;
    }
}