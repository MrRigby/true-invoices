package com.github.mrrigby.trueinvoices.infrastructure.repository

import com.github.mrrigby.trueinvoices.common.test.infrastructure.DbDrivenSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.repository.PurchaserRepository
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
        def purchasersCountAfter = countFromDbTable("purchasers")
        purchasersCountAfter == purchasersCountBefore + 1

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

        then:
        def purchasersCountAfter = countFromDbTable("purchasers")
        purchasersCountAfter == purchasersCountBefore
    }
}
