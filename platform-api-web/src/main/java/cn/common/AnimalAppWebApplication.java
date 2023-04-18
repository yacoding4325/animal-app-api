package cn.common;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @packageName cn.common
 * @Description: 启动类
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
		SpringApplication.run(AnimalAppWebApplication.class, args);
	}

}
