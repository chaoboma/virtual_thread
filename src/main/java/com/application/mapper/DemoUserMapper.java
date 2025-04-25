package com.application.mapper;

import com.application.dynamic.DataSource;
import com.application.dynamic.DbsConstant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.application.entity.DemoUser;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
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
@Component
@Repository
@Mapper
//@DS("slave")
//@DataSource(value = DbsConstant.slave)
public interface DemoUserMapper extends BaseMapper<Object> {
    @DataSource(value = DbsConstant.slave)
    String allUsers();
    @DataSource(value = DbsConstant.master)
    String allUsers2();
}
