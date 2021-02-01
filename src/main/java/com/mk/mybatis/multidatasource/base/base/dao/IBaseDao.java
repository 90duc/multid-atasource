package com.mk.mybatis.multidatasource.base.base.dao;

import com.mk.mybatis.multidatasource.base.entity.Base;
import com.mk.mybatis.multidatasource.base.entity.BaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface IBaseDao {
    long countByExample(BaseExample example);

    int deleteByExample(BaseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Base record);

    int insertSelective(Base record);

    List<Base> selectByExampleWithRowbounds(BaseExample example, RowBounds rowBounds);

    List<Base> selectByExample(BaseExample example);

    Base selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Base record, @Param("example") BaseExample example);

    int updateByExample(@Param("record") Base record, @Param("example") BaseExample example);

    int updateByPrimaryKeySelective(Base record);

    int updateByPrimaryKey(Base record);
}