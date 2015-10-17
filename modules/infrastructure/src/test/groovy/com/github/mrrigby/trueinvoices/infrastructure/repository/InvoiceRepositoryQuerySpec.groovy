package com.github.mrrigby.trueinvoices.infrastructure.repository

import com.github.mrrigby.trueinvoices.common.test.infrastructure.DbDrivenSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.model.Invoice
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository
import com.github.mrrigby.trueinvoices.repository.exceptions.InvoiceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
/**
 * @author MrRigby
 */
@ContextConfiguration(classes = RepositoryConfig.class)
@Transactional
class InvoiceRepositoryQuerySpec extends DbDrivenSpec {

    @Autowired
    def InvoiceRepository invoiceRepository

    def JdbcTemplate jdbcTemplate

    def setup() {
        jdbcTemplate = new JdbcTemplate(dataSource)
    }

    def "Should throw InvoiceNotFoundException for non existing invoice id"() {

        given:
        dataSet InvoiceRepositoryDataSets.invoiceWithNoDependencies
        def nonExistingId = 0L

        when:
        invoiceRepository.getById(nonExistingId)

        then:
        thrown(InvoiceNotFoundException)
    }

    def "Should get invoice by invoice id"() {

        given:
        dataSet InvoiceRepositoryDataSets.invoiceWithNoDependencies
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
        dataSet InvoiceRepositoryDataSets.invoiceWithNoDependencies
        def nonExistingBusinessId = "2010/01/01"

        when:
        invoiceRepository.getByBusinessId(nonExistingBusinessId)

        then:
        thrown(InvoiceNotFoundException)
    }

    def "Should get invoice by business id"() {

        given:
        dataSet InvoiceRepositoryDataSets.invoiceWithNoDependencies
        def existingBusinessId = "2015/09/03"

        when:
        def invoice = invoiceRepository.getByBusinessId(existingBusinessId)

        then:
        invoice != null
        invoice.id.get() == 1L
        invoice.businessId == existingBusinessId
    }

    def "Should list all invoices"() {

        given:
        dataSet InvoiceRepositoryDataSets.manyInvoices

        when:
        def invoices = invoiceRepository.listAll()

        then:
        invoices != null
        invoices.size() == 7
    }

    def "Should count all invoices"() {

        given:
        dataSet InvoiceRepositoryDataSets.manyInvoices

        when:
        def invoicesCount = invoiceRepository.count()

        then:
        invoicesCount == 7
    }

    def "Should list 1st page with invoices"() {

        given:
        dataSet InvoiceRepositoryDataSets.manyInvoices
        def firstPageable = new PageRequest(0, 5)

        when:
        def pageWithInvoices = invoiceRepository.listPage(firstPageable)

        then:
        pageWithInvoices != null
        pageWithInvoices.totalPages == 2
        pageWithInvoices.totalElements == 7
        pageWithInvoices.content != null
        pageWithInvoices.content.size() == 5
        pageWithInvoices.content[0].id.get() == 7
        pageWithInvoices.content[4].id.get() == 3
    }

    def "Should list next page with invoices"() {

        given:
        dataSet InvoiceRepositoryDataSets.manyInvoices
        def firstPageable = new PageRequest(1, 5)

        when:
        def pageWithInvoices = invoiceRepository.listPage(firstPageable)

        then:
        pageWithInvoices != null
        pageWithInvoices.totalPages == 2
        pageWithInvoices.totalElements == 7
        pageWithInvoices.content != null
        pageWithInvoices.content.size() == 2
        pageWithInvoices.content[0].id.get() == 2
        pageWithInvoices.content[1].id.get() == 1
    }
}
