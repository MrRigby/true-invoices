package com.github.mrrigby.trueinvoices.infrastructure.repository

import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.model.Invoice
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository
import com.github.mrrigby.trueinvoices.repository.exceptions.InvoiceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * @author MrRigby
 */
@ContextConfiguration(classes = RepositoryConfig.class)
class InvoiceRepositorySpec extends Specification {

    @Autowired
    def InvoiceRepository invoiceRepository

    def setup() {
        // maybe something could be inited? :-)
    }

    def "Shoud throw InvoiceNotFoundException for non existing invoice id"() {

        given:
        def nonExistingId = 0L

        when:
        invoiceRepository.getById(nonExistingId)

        then:
        thrown(InvoiceNotFoundException)
    }

    def "Shoud get invoice by invoice id"() {

        given:
        def existingId = 5L

        when:
        def Invoice invoice = invoiceRepository.getById(existingId)

        then:
        invoice != null
        invoice.id.get() == existingId
        invoice.businessId == "2015/09/03"
    }

    def "Shoud throw InvoiceNotFoundException for non existing business id"() {

        given:
        def nonExistingBusinessId = "2010/01/01"

        when:
        invoiceRepository.getByBusinessId(nonExistingBusinessId)

        then:
        thrown(InvoiceNotFoundException)
    }

    def "Shoud get invoice by business id"() {

        given:
        def existingBusinessId = "2015/09/03"

        when:
        def invoice = invoiceRepository.getByBusinessId(existingBusinessId)

        then:
        invoice != null
        invoice.id.get() == 5L
        invoice.businessId == existingBusinessId
    }

}
