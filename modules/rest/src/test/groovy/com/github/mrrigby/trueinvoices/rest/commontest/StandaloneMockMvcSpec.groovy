package com.github.mrrigby.trueinvoices.rest.commontest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.core.DefaultRelProvider
import org.springframework.hateoas.hal.Jackson2HalModule
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
/**
 * @author MrRigby
 */
class StandaloneMockMvcSpec extends Specification {

    protected MockMvc standaloneMockMvcFor(Object... controllers) {
        MockMvcBuilders.standaloneSetup(controllers)
                .setMessageConverters(messageConverters())
                .build()
    }

    private static TypeConstrainedMappingJackson2HttpMessageConverter messageConverters() {

        TypeConstrainedMappingJackson2HttpMessageConverter messageConverter =
                new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
        messageConverter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));

        ObjectMapper objectMapper = messageConverter.getObjectMapper();
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.setHandlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(new DefaultRelProvider(), null, null))
        messageConverter
    }

}
