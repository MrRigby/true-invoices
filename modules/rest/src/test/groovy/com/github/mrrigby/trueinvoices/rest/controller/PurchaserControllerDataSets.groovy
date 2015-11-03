package com.github.mrrigby.trueinvoices.rest.controller

import javax.persistence.Column

/**
 * @author MrRigby
 */
class PurchaserControllerDataSets {

    public static singlePurchaser = {
        purchaser_templates id: 1, name: "PurchaserItem 1", address: "Address 1", tax_id: "1111111111"
    }

    public static manyPurchasers = {
        purchaser_templates id: 1, name: "PurchaserItem 1", address: "Address 1", tax_id: "1111111111"
        purchaser_templates id: 2, name: "PurchaserItem 2", address: "Address 2", tax_id: "2222222222"
        purchaser_templates id: 3, name: "PurchaserItem 3", address: "Address 3", tax_id: "3333333333"
        purchaser_templates id: 4, name: "PurchaserItem 4", address: "Address 4", tax_id: "4444444444"
        purchaser_templates id: 5, name: "PurchaserItem 5", address: "Address 5", tax_id: "5555555555"
        purchaser_templates id: 6, name: "PurchaserItem 6", address: "Address 6", tax_id: "6666666666"
        purchaser_templates id: 7, name: "PurchaserItem 7", address: "Address 7", tax_id: "7777777777"
        purchaser_templates id: 8, name: "PurchaserItem 8", address: "Address 8", tax_id: "8888888888"
        purchaser_templates id: 9, name: "PurchaserItem 9", address: "Address 9", tax_id: "9999999999"
        purchaser_templates id: 10, name: "PurchaserItem 10", address: "Address 10", tax_id: "1010101010"
        purchaser_templates id: 11, name: "PurchaserItem 11", address: "Address 11", tax_id: "1100110011"
        purchaser_templates id: 12, name: "PurchaserItem 12", address: "Address 12", tax_id: "1200120012"
    }

}
