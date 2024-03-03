package org.sweetie.objectlog.domain;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
public class BaseMetaObjectHandler extends MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now() ,metaObject);
        this.setFieldValByName( "updateTime",LocalDateTime.now() ,metaObject);
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName( "updateTime",LocalDateTime.now() ,metaObject);
    }
}