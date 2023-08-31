package com.dengzhihong.bankTransaction.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {

    @Update(" UPDATE  bk_user set money=money-#{moeny} WHERE name=#{outName};")
    void outMoney(@Param("outName") String outName,@Param("moeny") double moeny);

    @Update(" UPDATE  bk_user set money=money+#{moeny} WHERE name=#{inName};")
    void inMoeny(@Param("inName") String inName,@Param("moeny") double moeny);
}
