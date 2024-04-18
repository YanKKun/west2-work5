package com.yk;

import com.yk.config.WebsocketConfig;
import com.yk.pojo.ws.ChatEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 12080
 */
@SpringBootApplication
@EnableScheduling
public class Work5Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Work5Application.class, args);
        ChatEndpoint.setApplicationContext(applicationContext);
        WebsocketConfig.setApplicationContext(applicationContext);
    }

}
