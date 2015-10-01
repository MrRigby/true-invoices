package com.github.mrrigby.trueinvoices.model

import spock.lang.Specification

import java.time.LocalDate

import static com.github.mrrigby.trueinvoices.model.Invoice.anInvoice
import static com.github.mrrigby.trueinvoices.model.InvoiceItem.anInvoiceItem
import static com.github.mrrigby.trueinvoices.model.PaymentKind.ONE_WEEK

/**
 * @author MrRigby
 */
class InvoiceSpec extends Specification {

    def "Should calculate payment date for given payment kind"() {

        given:
        Invoice invoice = anInvoice()
                .withSoldDate(LocalDate.of(2015, 9, 12))
                .withPaymentKind(ONE_WEEK)
                .build()

        when:
        def paymentDate = invoice.getPaymentDate()

        then:
        assert paymentDate == LocalDate.of(2015, 9, 19)
    }

    def "Should calculate invoice summary"() {

        given:
        Invoice invoice = anInvoice()
                .withItems(
                    anInvoiceItem().withQuantity(1).withSingleNetPrice(BigDecimal.valueOf(999.99)).withTaxRate((short) 7),
                    anInvoiceItem().withQuantity(2).withSingleNetPrice(BigDecimal.valueOf(499.99)).withTaxRate((short) 23)
                ).build()

        when:
        def invoiceSummary = invoice.calculateInvoiceSummary()

        then:
        assert invoiceSummary.getTotalNet() == BigDecimal.valueOf(1999.97)
        assert invoiceSummary.getTotalGross() == BigDecimal.valueOf(2299.97)
        assert invoiceSummary.getItemSummaries().size() == 2
    }

}
