package com.ofg.fraud;

import com.google.common.collect.Lists;
import com.ofg.infrastructure.environment.EnvironmentSetupVerifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import static com.ofg.config.BasicProfiles.*;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
//@EnableCaching
@EnableAsync
class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.addListeners(new EnvironmentSetupVerifier(Lists.newArrayList(DEVELOPMENT, PRODUCTION, TEST)));
        application.run(args);
    }
}