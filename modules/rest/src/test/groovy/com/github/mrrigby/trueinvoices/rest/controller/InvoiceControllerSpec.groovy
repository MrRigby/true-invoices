package com.github.mrrigby.trueinvoices.rest.controller

import com.github.mrrigby.trueinvoices.common.test.rest.StandaloneMockMvcSpec
import com.github.mrrigby.trueinvoices.model.Invoice
import com.github.mrrigby.trueinvoices.model.PaymentKind
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository
import groovy.json.JsonSlurper
import org.springframework.http.MediaType

import java.time.LocalDate

import static com.github.mrrigby.trueinvoices.model.Invoice.anInvoice
import static com.github.mrrigby.trueinvoices.model.InvoiceItem.anInvoiceItem
import static com.github.mrrigby.trueinvoices.model.Purchaser.aPurchaser
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author MrRigby
 */
class InvoiceControllerSpec extends StandaloneMockMvcSpec {

    def "Should return invoice in JSON"() {

        given:
        def invoiceRepository = Mock(InvoiceRepository.class)
        def mockMvc = standaloneMockMvcFor(new InvoiceController(invoiceRepository, invoiceResourceAssembler))
        def invoiceId = 1L
        1 * invoiceRepository.getById(invoiceId) >> mockedInvoice(invoiceId)

        when:
        def response = mockMvc
                .perform(get("/invoice/{id}", invoiceId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())

        then:
        response.andExpect(status().isOk())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)
        content.invoice.id == invoiceId
        content.invoice.businessId == "2015/09/0001"
        content.invoice.items.size() == 2
    }

    private Invoice mockedInvoice(Long id) {
        anInvoice()
            .withId(id)
            .withBusinessId("2015/09/0001")
            .withDocumentDate(LocalDate.now())
            .withSoldDate(LocalDate.now())
            .withPaymentKind(PaymentKind.CASH)
            .withItems(
                anInvoiceItem()
                    .withCommodity("Prunning trees")
                    .withQuantity(1)
                    .withSingleNetPrice(new BigDecimal("499.99"))
                    .withTaxRate((short) 7),
                anInvoiceItem()
                    .withCommodity("Planting shrubs")
                    .withQuantity(2)
                    .withSingleNetPrice(new BigDecimal("299.99"))
                    .withTaxRate((short) 23)
            ).withPurchaser(
                aPurchaser()
                    .withName("John Baker")
                    .withAddress("Baker Street 12")
                    .withTaxIdentifier("1234512345")
                    .withRole("Seller")
            ).build();
    }
}
