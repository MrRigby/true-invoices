package com.github.mrrigby.trueinvoices.model

import spock.lang.Specification

import static com.github.mrrigby.trueinvoices.model.InvoiceItem.anInvoiceItem

/**
 * @author MrRigby
 */
class InvoiceItemSpec extends Specification {

    def "Should calculate single gross price"() {

        given:
        InvoiceItem item = anInvoiceItem()
                .withQuantity(2)
                .withSingleNetPrice(BigDecimal.valueOf(499.99))
                .withTaxRate((short) 23)
                .build()

        when:
        def singleGrossPrice = item.getSingleGrossPrice()

        then:
        assert singleGrossPrice == BigDecimal.valueOf(614.99)
    }

    def "Should calculate total net price"() {

        given:
        InvoiceItem item = anInvoiceItem()
                .withQuantity(2)
                .withSingleNetPrice(BigDecimal.valueOf(499.99))
                .withTaxRate((short) 23)
                .build()

        when:
        def totalNetPrice = item.getTotalNetPrice()

        then:
        assert totalNetPrice == BigDecimal.valueOf(999.98)
    }

    def "Should calculate total gross price"() {

        given:
        InvoiceItem item = anInvoiceItem()
                .withQuantity(2)
                .withSingleNetPrice(BigDecimal.valueOf(499.99))
                .withTaxRate((short) 23)
                .build()

        when:
        def totalGrossPrice = item.getTotalGrossPrice()

        then:
        assert totalGrossPrice == BigDecimal.valueOf(1229.98)
    }

}
