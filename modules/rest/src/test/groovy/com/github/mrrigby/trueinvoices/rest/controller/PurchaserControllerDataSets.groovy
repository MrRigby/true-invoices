package com.github.mrrigby.trueinvoices.rest.controller

import javax.persistence.Column

/**
 * @author MrRigby
 */
class PurchaserControllerDataSets {

    public static singlePurchaser = {
        purchasers id: 1, name: "Purchaser 1", address: "Address 1", tax_id: "1111111111"
    }

    public static manyPurchasers = {
        purchasers id: 1, name: "Purchaser 1", address: "Address 1", tax_id: "1111111111"
        purchasers id: 2, name: "Purchaser 2", address: "Address 2", tax_id: "2222222222"
        purchasers id: 3, name: "Purchaser 3", address: "Address 3", tax_id: "3333333333"
        purchasers id: 4, name: "Purchaser 4", address: "Address 4", tax_id: "4444444444"
        purchasers id: 5, name: "Purchaser 5", address: "Address 5", tax_id: "5555555555"
        purchasers id: 6, name: "Purchaser 6", address: "Address 6", tax_id: "6666666666"
        purchasers id: 7, name: "Purchaser 7", address: "Address 7", tax_id: "7777777777"
        purchasers id: 8, name: "Purchaser 8", address: "Address 8", tax_id: "8888888888"
        purchasers id: 9, name: "Purchaser 9", address: "Address 9", tax_id: "9999999999"
        purchasers id: 10, name: "Purchaser 10", address: "Address 10", tax_id: "1010101010"
        purchasers id: 11, name: "Purchaser 11", address: "Address 11", tax_id: "1100110011"
        purchasers id: 12, name: "Purchaser 12", address: "Address 12", tax_id: "1200120012"
    }

}
