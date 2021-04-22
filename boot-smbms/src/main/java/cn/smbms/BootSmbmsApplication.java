package cn.smbms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("cn.smbms.dao")
@EnableTransactionManagement//开启事务
public class BootSmbmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootSmbmsApplication.class, args);
    }

}
