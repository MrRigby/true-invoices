package com.github.mrrigby.trueinvoices.infrastructure.repository

import com.github.mrrigby.trueinvoices.common.test.infrastructure.DbDrivenSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.repository.dto.PurchaserListFilter
import com.github.mrrigby.trueinvoices.repository.PurchaserRepository
import com.github.mrrigby.trueinvoices.repository.exceptions.PurchaserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
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

    def "Should throw PurchaserNotFoundException for non existing purchaser id"() {

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

    def "Should list 1st page with purchasers"() {

        given:
        dataSet PurchaserRepositoryDataSets.manyPurchasers
        def firstPageable = new PageRequest(0, 5)
        def emptyFilter = new PurchaserListFilter()

        when:
        def pageWithPurchasers = purchaserRepository.listPage(firstPageable, emptyFilter)
        
        then:
        pageWithPurchasers != null
        pageWithPurchasers.totalPages == 3
        pageWithPurchasers.totalElements == 12
        pageWithPurchasers.content != null
        pageWithPurchasers.content.size() == 5
        pageWithPurchasers.content[0].id.get() == 1
        pageWithPurchasers.content[4].id.get() == 5
    }

    def "Should list next page with purchasers"() {

        given:
        dataSet PurchaserRepositoryDataSets.manyPurchasers
        def nextPageable = new PageRequest(2, 5)
        def emptyFilter = new PurchaserListFilter()

        when:
        def pageWithPurchasers = purchaserRepository.listPage(nextPageable, emptyFilter)

        then:
        pageWithPurchasers != null
        pageWithPurchasers.totalPages == 3
        pageWithPurchasers.totalElements == 12
        pageWithPurchasers.content != null
        pageWithPurchasers.content.size() == 2
        pageWithPurchasers.content[0].id.get() == 11
        pageWithPurchasers.content[1].id.get() == 12
    }
}
