package com.github.mrrigby.trueinvoices.rest.controller
import com.github.mrrigby.trueinvoices.common.test.rest.WebCtxMockMvcSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.rest.RestConfig
import groovy.json.JsonSlurper
import org.springframework.hateoas.MediaTypes
import org.springframework.test.context.ContextConfiguration

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
/**
 * @author MrRigby
 */
@ContextConfiguration(classes = [RestConfig.class, RepositoryConfig.class])
class InvoiceModifyControllerSpec extends WebCtxMockMvcSpec {

    def "Should save invoice"() {

        given:
        def invoiceToSave = jsonFromFile("/invoiceToSave.json")

        when:
        def response = mockMvc
                .perform(post("/invoice").content(invoiceToSave)
                .contentType(MediaTypes.HAL_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())

        then:
        response.andExpect(status().isCreated())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)

        content.invoice.id != null
        content.invoice.businessId == "123"
        content.invoice.documentDate == "2015-11-11"
        content.invoice.soldDate == "2015-11-21"
        content.invoice.paymentDate == "2015-11-21"
        content.invoice.paymentKind == "CASH"

        content.invoice.items.size() == 2
        content.invoice.items[0].commodity == "Pruning trees"
        content.invoice.items[1].commodity == "Mowing"

        content.invoice.purchaserItems.size() == 1
        content.invoice.purchaserItems[0].name == "John Doe Inc."
    }

    def "Should update invoice"() {

        given:
        dataSet InvoiceControllerDataSets.invoiceWithDependencies
        def invoiceToUpdate = jsonFromFile("/invoiceToUpdate.json")
        def invoiceId = 1L

        when:
        def response = mockMvc
                .perform(put("/invoice/{id}", invoiceId).content(invoiceToUpdate)
                .contentType(MediaTypes.HAL_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())

        then:
        response.andExpect(status().isOk())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)

        content.invoice.id == invoiceId
        content.invoice.businessId == "123"
        content.invoice.documentDate == "2015-11-11"
        content.invoice.soldDate == "2015-11-21"
        content.invoice.paymentDate == "2015-11-21"
        content.invoice.paymentKind == "CASH"

        content.invoice.items.size() == 1
        content.invoice.items[0].commodity == "Planting apple tree"

        content.invoice.purchaserItems.size() == 1
        content.invoice.purchaserItems[0].name == "Mrs Applebaum Co."
    }

    def "Should get NotFound while the missing invoice's update"() {

        given:
        dataSet InvoiceControllerDataSets.invoiceWithDependencies
        def invoiceToUpdate = jsonFromFile("/invoiceToUpdate.json")
        def missingInvoiceId = 100L

        when:
        def response = mockMvc
                .perform(put("/invoice/{id}", missingInvoiceId).content(invoiceToUpdate)
                .contentType(MediaTypes.HAL_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())

        then:
        response.andExpect(status().isNotFound())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)
        content.httpStatusCode == 404
        content.httpStatusName == "NOT_FOUND"
    }
}
