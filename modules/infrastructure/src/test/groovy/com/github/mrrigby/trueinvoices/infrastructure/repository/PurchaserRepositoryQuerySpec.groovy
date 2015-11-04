package com.github.mrrigby.trueinvoices.infrastructure.repository

import com.github.mrrigby.trueinvoices.common.test.infrastructure.DbDrivenSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.model.Purchaser
import com.github.mrrigby.trueinvoices.repository.PurchaserRepository
import com.github.mrrigby.trueinvoices.repository.exceptions.PurchaserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

/**
 * @author MrRigby
 */
@ContextConfiguration(classes = RepositoryConfig.class)
@Transactional
class PurchaserRepositoryQuerySpec extends DbDrivenSpec {

    @Autowired
    def PurchaserRepository purchaserRepository

    def "Should throw PurchaserNotFoundException for non existing id"() {

        given:
        dataSet PurchaserRepositoryDataSets.singlePurchaser
        def notExistingId = 0L

        when:
        purchaserRepository.getById(notExistingId)

        then:
        thrown(PurchaserNotFoundException)

    }

    def "Should get purchaser by id"() {

        given:
        dataSet PurchaserRepositoryDataSets.singlePurchaser
        def existingId = 1L

        when:
        def purchaser = purchaserRepository.getById(existingId)

        then:
        purchaser != null
        purchaser.id.get() == existingId
        purchaser.name == "Purchaser 1"
        purchaser.address == "Address 1"
        purchaser.taxIdentifier == "1111111111"
    }
}
