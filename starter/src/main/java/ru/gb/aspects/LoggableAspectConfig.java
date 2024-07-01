package ru.gb.aspects;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnProperty(value = "http.logging.enabled", havingValue = "true")
public class LoggableAspectConfig {
    @Bean
    LoggableAspect loggableAspect(){
        return new LoggableAspect();
    }
}