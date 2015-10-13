package com.github.mrrigby.trueinvoices.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author MrRigby
 */
@Configuration
@ComponentScan("com.github.mrrigby.trueinvoices.rest")
@EnableHypermediaSupport(type = HypermediaType.HAL)
@EnableSpringDataWebSupport
public class RestConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jacksonMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .defaultViewInclusion(true);
    }

}
