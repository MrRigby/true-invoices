package com.github.mrrigby.trueinvoices.rest.controller

import com.github.mrrigby.trueinvoices.common.test.rest.WebCtxMockMvcSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.rest.RestConfig
import groovy.json.JsonSlurper
import org.springframework.hateoas.MediaTypes
import org.springframework.test.context.ContextConfiguration

import static com.github.mrrigby.trueinvoices.rest.controller.PurchaserControllerDataSets.manyPurchasers
import static com.github.mrrigby.trueinvoices.rest.controller.PurchaserControllerDataSets.singlePurchaser
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author MrRigby
 */
@ContextConfiguration(classes = [RestConfig.class, RepositoryConfig.class])
class PurchaserQueryControllerSpec extends WebCtxMockMvcSpec {

    def "Should get paged view of purchasers"() {

        given:
        dataSet manyPurchasers

        when:
        def response = mockMvc
                .perform(get("/purchaser?page=0&size=5")
                .contentType(MediaTypes.HAL_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())

        then:
        response.andExpect(status().isOk())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)
        content._embedded.purchaserResourceList.size() == 5
        content.page.size == 5
        content.page.totalElements == 12
        content.page.totalPages == 3
        content.page.number == 0
    }

    def "Should get paged view of purchasers with filtering"() {

        given:
        dataSet manyPurchasers

        when:
        def response = mockMvc
                .perform(get("/purchaser?page=0&size=5&name={name}&taxId={taxId}", "Purchaser 1", "1010101010")
                .contentType(MediaTypes.HAL_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())

        then:
        response.andExpect(status().isOk())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)
        content._embedded.purchaserResourceList.size() == 1
        content.page.size == 5
        content.page.totalElements == 1
        content.page.totalPages == 1
        content.page.number == 0
    }

    def "Should get purchaser by id"() {

        given:
        dataSet singlePurchaser
        def purchaserId = 1L

        when:
        def response = mockMvc
                .perform(get("/purchaser/{id}", purchaserId)
                .contentType(MediaTypes.HAL_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())

        then:
        response.andExpect(status().isOk())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)

        content.purchaser.id == purchaserId
        content.purchaser.name == "Purchaser 1"
        content.purchaser.address == "Address 1"
        content.purchaser.taxIdentifier == "1111111111"
    }

    def "Should get NotFound while getting missing purchaser by id"() {

        given:
        dataSet singlePurchaser
        def notExistingPurchaserId = 20L

        when:
        def response = mockMvc
                .perform(get("/purchaser/{id}", notExistingPurchaserId)
                .contentType(MediaTypes.HAL_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())

        then:
        response.andExpect(status().isNotFound())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)
        content.httpStatusCode == 404
        content.httpStatusName == "NOT_FOUND"
    }
}
