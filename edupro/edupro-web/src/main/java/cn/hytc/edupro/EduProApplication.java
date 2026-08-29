package cn.hytc.edupro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class EduProApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduProApplication.class, args);
    }
}