package cn.baizhi958216.sse_backend_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class SseBackendSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SseBackendSpringApplication.class, args);
    }

}
