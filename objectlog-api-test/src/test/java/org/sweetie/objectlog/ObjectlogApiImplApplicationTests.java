package org.sweetie.objectlog;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ObjectlogApiImplApplicationTests {

    @Test
    void contextLoads() {
        String holderName = "AA";
        String cardNumber = "";
        //银行卡信息
        Assert.isTrue(StrUtil.isBlank(holderName) && StrUtil.isNotBlank(cardNumber), () -> new RuntimeException("缺少户名信息"));
        Assert.isTrue(StrUtil.isNotBlank(holderName) && StrUtil.isBlank(cardNumber), () -> new RuntimeException("缺少银行卡信息"));

    }

}
