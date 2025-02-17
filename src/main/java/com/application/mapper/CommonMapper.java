package com.application.mapper;

import com.application.entity.DemoUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liurb
 * @since 2024-05-18
 */
@Mapper
public interface CommonMapper extends BaseMapper<DemoUser> {
    Object querySql(@Param("sql") String sql) ;
}
