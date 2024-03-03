package org.sweetie.objectlog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableFeignClients
@ServletComponentScan
@MapperScan("org.sweetie.objectlog.**.mapper")
public class ObjectLogApiImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObjectLogApiImplApplication.class, args);
    }

}
