package com.dengzhihong.mapper;

import com.dengzhihong.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper {

    @Select("SELECT * FROM tb_brand;")
    @ResultMap("brandMap")
    List<Brand> selectAll();

    int add(Brand brand);

    void deleteByIds(String[] idarrs);

    @Delete("delete from tb_brand where id=#{id}")
    @ResultMap("brandMap")
    void deleteOne(String id);

    int updateById(Brand brand);

    List<Brand> selectCondition(Brand brand);

    @Select(" SELECT * from tb_brand limit #{begin},#{size}")
    @ResultMap("brandMap")
    List<Brand> selectByPage(@Param("begin") int begin,@Param("size") int size);

    @Select("SELECT count(*) from tb_brand")
    int selectTotalCount();

    int selectTotalCountCondition(Brand brand);

    List<Brand> selectByPageCondition(@Param("begin")int currentPage,@Param("size") int size,@Param("brand")  Brand brand);
}
