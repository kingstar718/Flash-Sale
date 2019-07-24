package top.wujinxing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author wujinxing
 * date 2019 2019/7/21 14:14
 * description
 */
@SpringBootApplication
public class MainApplication {

    /*@PostConstruct
    void started(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }*/
    public static void main(String[] args) throws Exception{
        SpringApplication.run(MainApplication.class, args);
    }
}
