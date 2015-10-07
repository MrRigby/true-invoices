package com.github.mrrigby.trueinvoices.infrastructure.repository

import com.github.mrrigby.trueinvoices.common.test.infrastructure.DbDrivenSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.model.Invoice
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository
import com.github.mrrigby.trueinvoices.repository.exceptions.InvoiceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
/**
 * @author MrRigby
 */
@ContextConfiguration(classes = RepositoryConfig.class)
@Transactional
class InvoiceRepositorySpec extends DbDrivenSpec {

    public static invoiceWithNoItems = {
        invoices id: 1,
                business_id: "2015/09/03",
                document_date: "2015-10-01",
                sold_date: "2015-10-01",
                payment_date: "2015-10-01",
                payment_kind: "CASH"
    }

    @Autowired
    def InvoiceRepository invoiceRepository

    def JdbcTemplate jdbcTemplate

    def setup() {
        jdbcTemplate = new JdbcTemplate(dataSource)
    }

    def "Should throw InvoiceNotFoundException for non existing invoice id"() {

        given:
        dataSet invoiceWithNoItems
        def nonExistingId = 0L

        when:
        invoiceRepository.getById(nonExistingId)

        then:
        thrown(InvoiceNotFoundException)
    }

    def "Should get invoice by invoice id"() {

        given:
        dataSet invoiceWithNoItems
        def existingId = 1L

        when:
        def Invoice invoice = invoiceRepository.getById(existingId)

        then:
        invoice != null
        invoice.id.get() == existingId
        invoice.businessId == "2015/09/03"
    }

    def "Should throw InvoiceNotFoundException for non existing business id"() {

        given:
        dataSet invoiceWithNoItems
        def nonExistingBusinessId = "2010/01/01"

        when:
        invoiceRepository.getByBusinessId(nonExistingBusinessId)

        then:
        thrown(InvoiceNotFoundException)
    }

    def "Should get invoice by business id"() {

        given:
        dataSet invoiceWithNoItems
        def existingBusinessId = "2015/09/03"

        when:
        def invoice = invoiceRepository.getByBusinessId(existingBusinessId)

        then:
        invoice != null
        invoice.id.get() == 1L
        invoice.businessId == existingBusinessId
    }

}
