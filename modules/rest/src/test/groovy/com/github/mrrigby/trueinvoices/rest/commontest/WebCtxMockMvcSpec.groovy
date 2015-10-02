package com.github.mrrigby.trueinvoices.rest.commontest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
/**
 * @author MrRigby
 */
@ContextConfiguration(classes = WebCtxMockMvcSpec.class)
@WebAppConfiguration
class WebCtxMockMvcSpec extends Specification {

    @Autowired
    protected WebApplicationContext webApplicationContext

    protected MockMvc mockMvc

    def setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

}
