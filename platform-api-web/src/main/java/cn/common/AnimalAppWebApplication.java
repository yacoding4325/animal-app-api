package cn.common;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author yaCoding
 * @create 2023-04-16 下午 8:51
 */

//启动类
@SpringBootApplication(scanBasePackages = {
        "pro.skywalking",
        "cn.common"
})
@MapperScan({"cn.common.repository.repository"})
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
@Configuration
@EnableTransactionManagement
@Slf4j
public class AnimalAppWebApplication {

    public static void main(String[] args) {

    }

}
