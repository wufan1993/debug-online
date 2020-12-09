package com.wufan.debug.online.dashboard.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wufan.debug.online.dashboard.util.ReflectUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * 我本非凡
 * Date:2020-12-09
 * Time:15:12:46
 * Description:BaseController.java
 *
 * @author wufan02
 * @since JDK 1.8
 * Enjoy a grander sight By climbing to a greater height
 */
@Slf4j
public class BaseController {

    /**
     * 保存数据
     *
     * @param baseMapper
     * @param entity
     * @param <T>
     */
    public <T> boolean saveEntity(BaseMapper baseMapper, T entity, Supplier<Long> idSupplier) {
        try {
            Long id = ReflectUtils.getFieldValue(entity, "id");
            if (idSupplier == null || id != null) {
                int update = baseMapper.updateById(entity);
                if (update == 1) {
                    return true;
                }
            }

            if (id == null) {
                ReflectUtils.setFieldValue(entity, "id", idSupplier.get());
                int insert = baseMapper.insert(entity);
                if (insert == 1) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("插入失败", e);
        }
        return false;
    }
}
