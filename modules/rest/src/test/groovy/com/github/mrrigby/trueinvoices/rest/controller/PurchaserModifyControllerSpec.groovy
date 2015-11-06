package com.github.mrrigby.trueinvoices.rest.controller

import com.github.mrrigby.trueinvoices.common.test.rest.WebCtxMockMvcSpec
import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig
import com.github.mrrigby.trueinvoices.rest.RestConfig
import groovy.json.JsonSlurper
import org.springframework.hateoas.MediaTypes
import org.springframework.test.context.ContextConfiguration

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author MrRigby
 */
@ContextConfiguration(classes = [RestConfig.class, RepositoryConfig.class])
class PurchaserModifyControllerSpec extends WebCtxMockMvcSpec {

    def "Should save purchaser"() {

        given:
        def purchaserToSave = jsonFromFile("/purchaserToSave.json")

        when:
        def response = mockMvc
                .perform(post("/purchaser").content(purchaserToSave)
                .contentType(MediaTypes.HAL_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())

        then:
        response.andExpect(status().isCreated())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)

        content.purchaser.id != null
        content.purchaser.name == "Purchaser From JSON"
        content.purchaser.address == "Address From JSON"
        content.purchaser.taxIdentifier == "6699669966"
    }

    def "Should update purchaser"() {

        given:
        dataSet PurchaserControllerDataSets.singlePurchaser
        def purchaserToUpdate = jsonFromFile("/purchaserToUpdate.json")
        def purchaserId = 1L

        when:
        def response = mockMvc
                .perform(put("/purchaser/{id}", purchaserId).content(purchaserToUpdate)
                .contentType(MediaTypes.HAL_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())

        then:
        response.andExpect(status().isOk())
        def content = new JsonSlurper().parseText(response.andReturn().response.contentAsString)

        content.purchaser.id == 1L
        content.purchaser.name == "Purchaser From JSON"
        content.purchaser.address == "Address From JSON"
        content.purchaser.taxIdentifier == "6699669966"
    }
}
