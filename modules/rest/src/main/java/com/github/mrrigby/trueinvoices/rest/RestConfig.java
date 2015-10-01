package com.github.mrrigby.trueinvoices.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

/**
 * @author MrRigby
 */
@Configuration
@ComponentScan("com.github.mrrigby.trueinvoices.rest")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class RestConfig {

}
