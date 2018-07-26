package com.finessence.loan;

import com.finessence.loan.config.SpringJpaConfiguration;
import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Import(SpringJpaConfiguration.class)
public class Application {
    private final static Logger LOG = Logger.getLogger(Application.class);
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}