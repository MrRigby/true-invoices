package com.github.mrrigby.trueinvoices.infrastructure.repository
import com.github.mrrigby.trueinvoices.common.test.infrastructure.DbDrivenSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.model.PaymentKind
import com.github.mrrigby.trueinvoices.model.TaxRate
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository
import org.hibernate.SessionFactory
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

    @Autowired
    def SessionFactory sessionFactory

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

        when:
        def savedInvoice = invoiceRepository.save(invoice)

        then:
        countFromDbTable("invoices") == invoiceCountBefore + 1
        countInvoiceItemsForInvoiceId(savedInvoice.id.get()) == 2
        countPurchasersForInvoiceId(savedInvoice.id.get()) == 1

        def invoiceFromDb = jdbcTemplate.queryForMap(
                """SELECT * FROM invoices WHERE id = ?""", savedInvoice.id.get())
        invoiceFromDb.businessId == "2015/10/0001"
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
        sessionFactory.getCurrentSession().flush();

        then:
        countFromDbTable("invoices") == invoiceCountBefore
        countInvoiceItemsForInvoiceId(invoice.id.get()) == 1
        countPurchasersForInvoiceId(invoice.id.get()) == 1

        def invoiceFromDb = jdbcTemplate.queryForMap(
                """SELECT * FROM invoices WHERE id = ?""", invoice.id.get())
        invoiceFromDb.businessId == "2015/11/0002"
    }

    private def countInvoiceItemsForInvoiceId(invoiceId) {
        jdbcTemplate.queryForObject(
                """SELECT COUNT(*)
                       FROM invoice_items
                       WHERE invoice_id = ?""", Integer.class, invoiceId)
    }

    private def countPurchasersForInvoiceId(invoiceId) {
        jdbcTemplate.queryForObject(
                """SELECT COUNT(*)
                       FROM invoice_purchasers
                       WHERE invoice_id = ?""", Integer.class, invoiceId)
    }

}
