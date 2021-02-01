package com.mk.mybatis.multidatasource.one.base.dao;

import com.mk.mybatis.multidatasource.one.entity.Man;
import com.mk.mybatis.multidatasource.one.entity.ManExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface IManDao {
    long countByExample(ManExample example);

    int deleteByExample(ManExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Man record);

    int insertSelective(Man record);

    List<Man> selectByExampleWithRowbounds(ManExample example, RowBounds rowBounds);

    List<Man> selectByExample(ManExample example);

    Man selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Man record, @Param("example") ManExample example);

    int updateByExample(@Param("record") Man record, @Param("example") ManExample example);

    int updateByPrimaryKeySelective(Man record);

    int updateByPrimaryKey(Man record);
}