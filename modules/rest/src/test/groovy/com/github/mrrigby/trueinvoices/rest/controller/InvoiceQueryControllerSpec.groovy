package com.github.mrrigby.trueinvoices.rest.controller

import com.github.mrrigby.trueinvoices.common.test.rest.WebCtxMockMvcSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.rest.RestConfig
import groovy.json.JsonSlurper
import org.springframework.hateoas.MediaTypes
import org.springframework.test.context.ContextConfiguration

import static com.github.mrrigby.trueinvoices.rest.controller.InvoiceControllerDataSets.invoiceWithDependencies
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author MrRigby
 */
@ContextConfiguration(classes = [RestConfig.class, RepositoryConfig.class])
class InvoiceQueryControllerSpec extends WebCtxMockMvcSpec {

    def "Should get invoice by id"() {

        given:
        dataSet invoiceWithDependencies
        def invoiceId = 1L

        when:
        def response = mockMvc
                .perform(get("/invoice/{id}", invoiceId)
                .contentType(MediaTypes.HAL_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())

        then:
        response.andExpect(status().isOk())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)

        content.invoice.id == invoiceId
        content.invoice.businessId == "2015/09/03"
        content.invoice.soldDate == "2015-10-02"
        content.invoice.paymentDate == "2015-10-16"
        content.invoice.paymentKind == "TWO_WEEKS"

        content.invoice.items.size() == 2
        content.invoice.items[0].commodity == "Pruning trees"
        content.invoice.items[1].commodity == "Mowing"

        content.invoice.purchasers.size() == 1
        content.invoice.purchasers[0].name == "John Doe Inc."
    }
}
