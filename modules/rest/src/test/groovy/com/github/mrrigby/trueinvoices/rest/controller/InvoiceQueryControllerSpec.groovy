package com.github.mrrigby.trueinvoices.rest.controller

import com.github.mrrigby.trueinvoices.common.test.rest.WebCtxMockMvcSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.rest.RestConfig
import groovy.json.JsonSlurper
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
/**
 * @author MrRigby
 */
@ContextConfiguration(classes = [RestConfig.class, RepositoryConfig.class])
class InvoiceQueryControllerSpec extends WebCtxMockMvcSpec {

    public static invoiceWithNoItems = {
        invoices id: 1,
                business_id: "2015/09/03",
                document_date: "2015-10-01",
                sold_date: "2015-10-01",
                payment_date: "2015-10-01",
                payment_kind: "CASH"
    }

    def "Should find invoice by id"() {

        given:
        dataSet invoiceWithNoItems
        def invoiceId = 1L

        when:
        def response = mockMvc
                .perform(get("/invoice/{id}", invoiceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())


        then:
        response.andExpect(status().isOk())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)
        content.invoice.id == invoiceId
        content.invoice.businessId == "2015/09/03"
        content.invoice.items.size() == 0
    }
}
