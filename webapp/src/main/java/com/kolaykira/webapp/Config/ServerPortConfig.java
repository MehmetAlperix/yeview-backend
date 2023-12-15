package com.kolaykira.webapp.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@Configuration
public class ServerPortConfig {

    @Value("${server.port:8080}")
    private int serverPort;

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Environment env = event.getApplicationContext().getEnvironment();
        int port = Integer.parseInt(env.getProperty("server.port", String.valueOf(serverPort)));
        System.out.println("Hello world listening on port " + port);
    }
}
