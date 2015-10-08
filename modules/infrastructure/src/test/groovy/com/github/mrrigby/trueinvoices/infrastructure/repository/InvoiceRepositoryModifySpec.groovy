package com.github.mrrigby.trueinvoices.infrastructure.repository
import com.github.mrrigby.trueinvoices.common.test.infrastructure.DbDrivenSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.model.PaymentKind
import com.github.mrrigby.trueinvoices.model.TaxRate
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDate
import java.time.Month

import static com.github.mrrigby.trueinvoices.model.Invoice.anInvoice
import static com.github.mrrigby.trueinvoices.model.InvoiceItem.anInvoiceItem
import static com.github.mrrigby.trueinvoices.model.Purchaser.aPurchaser
/**
 * @author MrRigby
 */
@ContextConfiguration(classes = RepositoryConfig.class)
@Transactional
class InvoiceRepositoryModifySpec extends DbDrivenSpec {

    @Autowired
    def InvoiceRepository invoiceRepository

    def JdbcTemplate jdbcTemplate

    def setup() {
        jdbcTemplate = new JdbcTemplate(dataSource)
    }

    def "Should save invoice"() {

        given:
        def invoice = anInvoice()
                .withBusinessId("2015/10/0001")
                .withDocumentDate(LocalDate.of(2015, Month.OCTOBER, 1))
                .withSoldDate(LocalDate.of(2015, Month.OCTOBER, 2))
                .withPaymentKind(PaymentKind.TWO_WEEKS)
                .withItems(
                    anInvoiceItem()
                            .withCommodity("Pruning trees")
                            .withQuantity(1)
                            .withSingleNetPrice(new BigDecimal(1499.99))
                            .withTaxRate(TaxRate.valueOf(5)),
                    anInvoiceItem()
                            .withCommodity("Mowing")
                            .withQuantity(2)
                            .withSingleNetPrice(new BigDecimal(449.99))
                            .withTaxRate(TaxRate.valueOf(7))
                ).withPurchaser(
                    aPurchaser()
                            .withName("John Doe Inc.")
                            .withAddress("Spitfire Street 12, London")
                            .withTaxIdentifier("1234567890")
                            .withRole("Purchaser")
                ).build()
        def invoiceCountBefore = countForTable("invoices")
        def itemsCountBefore = countForTable("invoice_items")
        def purchasersCountBefore = countForTable("invoice_purchasers")

        when:
        def savedInvoiceId = invoiceRepository.save(invoice)

        then:
        def invoiceCountAfter = countForTable("invoices")
        def itemsCountAfter = countForTable("invoice_items")
        def purchasersCountAfter = countForTable("invoice_purchasers")

        invoiceCountAfter == invoiceCountBefore + 1
        itemsCountAfter == itemsCountBefore + 2
        purchasersCountAfter == purchasersCountBefore + 1

        def dbInvoiceId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM invoices", Integer.class)
        savedInvoiceId == dbInvoiceId
    }

    private int countForTable(tableName) {
        jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ${tableName}", Integer.class)
    }
}
