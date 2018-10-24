package cn.doublehh;

import com.didispace.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@EnableSwagger2Doc
@ServletComponentScan
@SpringBootApplication
public class PeWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeWebApplication.class, args);
    }
}
