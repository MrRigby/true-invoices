package com.github.mrrigby.trueinvoices.infrastructure.repository

import com.github.mrrigby.trueinvoices.common.test.infrastructure.DbDrivenSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.repository.PurchaserRepository
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import static com.github.mrrigby.trueinvoices.model.Purchaser.PurchaserBuilder.aPurchaser

/**
 * @author MrRigby
 */
@ContextConfiguration(classes = RepositoryConfig.class)
@Transactional
class PurchaserRepositoryModifySpec extends DbDrivenSpec {

    @Autowired
    def PurchaserRepository purchaserRepository

    @Autowired
    def SessionFactory sessionFactory

    def "Should save purchaser"() {

        given:
        def purchaser = aPurchaser()
            .withName("Zenon")
            .withAddress("Baker Street 12, London 123")
            .withTaxIdentifier("1234567890")
            .build()
        def purchasersCountBefore = countFromDbTable("purchasers")

        when:
        def savedPurchaser = purchaserRepository.save(purchaser)

        then:
        countFromDbTable("purchasers") == purchasersCountBefore + 1

        def dbPurchaserId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM purchasers", Integer.class)
        savedPurchaser.id.get() == dbPurchaserId
    }

    def "Should update purchaser"() {

        given:
        dataSet PurchaserRepositoryDataSets.singlePurchaser
        def purchaser = aPurchaser()
                .withId(1L)
                .withName("Zenon")
                .withAddress("Baker Street 12, London 123")
                .withTaxIdentifier("1234567890")
                .build()
        def purchasersCountBefore = countFromDbTable("purchasers")

        when:
        purchaserRepository.update(purchaser)
        sessionFactory.getCurrentSession().flush();

        then:
        countFromDbTable("purchasers") == purchasersCountBefore

        def purchaserFromDb = jdbcTemplate.queryForMap(
                """SELECT * FROM purchasers WHERE id = ?""", purchaser.id.get())
        purchaserFromDb.name == "Zenon"
        purchaserFromDb.address == "Baker Street 12, London 123"
        purchaserFromDb.tax_id == "1234567890"
    }
}
