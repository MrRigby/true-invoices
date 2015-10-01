package com.github.mrrigby.trueinvoices.rest.controller

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.core.DefaultRelProvider
import org.springframework.hateoas.hal.Jackson2HalModule
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
/**
 * @author MrRigby
 */
class InvoiceControllerSpec extends Specification {

    static def MockMvc mockMvc

    def setupSpec() {

        TypeConstrainedMappingJackson2HttpMessageConverter messageConverter =
                new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
        messageConverter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));

        ObjectMapper objectMapper = messageConverter.getObjectMapper();
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.setHandlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(new DefaultRelProvider(), null, null))

        mockMvc = MockMvcBuilders.standaloneSetup(new InvoiceController()).setMessageConverters(messageConverter).build()
    }

    def "Should return invoice in JSON"() {

        given:
        // def mockMvc = MockMvcBuilders.standaloneSetup(new InvoiceController()).build()
        // def mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        when:
        def response = mockMvc.perform(
                MockMvcRequestBuilders.get("/invoice/{id}", 1).contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())

        then:
        response.andExpect(status().isOk())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)
        content.invoice.id == 100
        content.invoice.businessId == "2015/09/0001"
        content.invoice.items.size() == 2
    }

}
