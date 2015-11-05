package com.github.mrrigby.trueinvoices.infrastructure.repository
import com.github.mrrigby.trueinvoices.common.test.infrastructure.DbDrivenSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.model.PaymentKind
import com.github.mrrigby.trueinvoices.model.TaxRate
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDate
import java.time.Month

import static com.github.mrrigby.trueinvoices.model.Invoice.anInvoice
import static com.github.mrrigby.trueinvoices.model.InvoiceItem.anInvoiceItem
import static com.github.mrrigby.trueinvoices.model.PurchaserItem.aPurchaser

/**
 * @author MrRigby
 */
@ContextConfiguration(classes = RepositoryConfig.class)
@Transactional
class InvoiceRepositoryModifySpec extends DbDrivenSpec {

    @Autowired
    def InvoiceRepository invoiceRepository

    def "Should save invoice"() {

        given:
        def invoice = anInvoice()
                .withBusinessId("2015/10/0001")
                .withDocumentDate(LocalDate.of(2015, Month.OCTOBER, 1))
                .withSoldDate(LocalDate.of(2015, Month.OCTOBER, 2))
                .withPaymentKind(PaymentKind.TWO_WEEKS)
                .withItemBuilders(
                    anInvoiceItem()
                            .withQuantity(1).withCommodity("Pruning trees")
                            .withSingleNetPrice(new BigDecimal(1499.99)).withTaxRate(TaxRate.valueOf(5)),
                    anInvoiceItem()
                            .withQuantity(2).withCommodity("Mowing")
                            .withSingleNetPrice(new BigDecimal(449.99)).withTaxRate(TaxRate.valueOf(7))
                ).withPurchaserBuilder(
                    aPurchaser()
                            .withName("John Doe Inc.")
                            .withAddress("Spitfire Street 12, London")
                            .withTaxIdentifier("1234567890")
                            .withRole("PurchaserItem")
                ).build()
        def invoiceCountBefore = countFromDbTable("invoices")
        def itemsCountBefore = countFromDbTable("invoice_items")
        def purchasersCountBefore = countFromDbTable("invoice_purchasers")

        when:
        def savedInvoice = invoiceRepository.save(invoice)

        then:
        def invoiceCountAfter = countFromDbTable("invoices")
        invoiceCountAfter == invoiceCountBefore + 1

        def dbInvoiceId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM invoices", Integer.class)
        savedInvoice.id.get() == dbInvoiceId

        def itemsCountAfter = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM invoice_items WHERE invoice_id = ?", Integer.class, dbInvoiceId)
        itemsCountAfter == 2

        def purchasersCountAfter = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM invoice_purchasers WHERE invoice_id = ?", Integer.class, dbInvoiceId)
        purchasersCountAfter == 1
    }

    def "Should update invoice"() {

        given:
        dataSet InvoiceRepositoryDataSets.invoiceWithDependencies
        def invoice = anInvoice().withId(1L).withBusinessId("2015/11/0002")
                .withDocumentDate(LocalDate.of(2015, Month.NOVEMBER, 10))
                .withSoldDate(LocalDate.of(2015, Month.NOVEMBER, 12))
                .withPaymentKind(PaymentKind.ONE_WEEK)
                .withItemBuilder(
                    anInvoiceItem()
                            .withQuantity(1).withCommodity("Planting apple trees")
                            .withSingleNetPrice(new BigDecimal(999.99)).withTaxRate(TaxRate.valueOf(23))
                ).withPurchaserBuilder(
                    aPurchaser()
                            .withName("Peggy McDonnalds Inc.").withAddress("Wall Street 13, Edinburgh")
                            .withTaxIdentifier("1212123456").withRole("Investor")
                ).build()
        def invoiceCountBefore = countFromDbTable("invoices")

        when:
        invoiceRepository.update(invoice)

        then:
        def invoiceCountAfter = countFromDbTable("invoices")
        def itemsCountAfter = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM invoice_items WHERE invoice_id = ?", Integer.class, 1L)
        def purchasersCountAfter = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM invoice_purchasers WHERE invoice_id = ?", Integer.class, 1L)

        invoiceCountAfter == invoiceCountBefore
        itemsCountAfter == 1
        purchasersCountAfter == 1
    }
}
