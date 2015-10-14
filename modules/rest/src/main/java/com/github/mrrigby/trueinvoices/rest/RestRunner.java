package com.github.mrrigby.trueinvoices.rest;

import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author MrRigby
 */
@EnableAutoConfiguration
@Import({RestConfig.class, RepositoryConfig.class})
public class RestRunner {

    public static void main(String[] args) {
        SpringApplication.run(RestRunner.class, args);
    }

}
