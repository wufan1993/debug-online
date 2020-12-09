package com.wufan.debug.online.dashboard.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wufan.debug.online.dashboard.domain.MachineInfo;
import com.wufan.debug.online.dashboard.domain.MethodInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 我本非凡
 * Date:2020-12-03
 * Time:17:12:08
 * Description:MachineMapper.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Mapper
public interface MethodMapper extends BaseMapper<MethodInfo> {

    /**
     * 获取数据库最大Id
     * @return
     */
    @Select("SELECT max(id) from METHOD_INFO")
    Long getMaxId();
}
