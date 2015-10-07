package com.github.mrrigby.trueinvoices.common.test.rest

import com.github.mrrigby.trueinvoices.common.test.infrastructure.DbDrivenSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext

/**
 * @author MrRigby
 */
@ContextConfiguration(classes = WebCtxMockMvcSpec.class)
@Transactional
@WebAppConfiguration
class WebCtxMockMvcSpec extends DbDrivenSpec {

    @Autowired
    protected WebApplicationContext webApplicationContext

    protected MockMvc mockMvc

    def setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    def static String jsonFromFile(fileName) {
        this.getClass().getResource(fileName).text
    }
}
