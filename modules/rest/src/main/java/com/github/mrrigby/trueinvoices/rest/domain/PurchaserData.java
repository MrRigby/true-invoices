package com.github.mrrigby.trueinvoices.rest.domain;

import com.github.mrrigby.trueinvoices.model.PurchaserItem;

/**
 * @author MrRigby
 */
public class PurchaserData {

    private String name;
    private String address;
    private String taxIdentifier;
    private String role;

    public PurchaserItem.Builder toModelBuilder() {

        PurchaserItem.Builder purchaserBuilder = PurchaserItem.aPurchaser()
                .withName(name)
                .withAddress(address)
                .withTaxIdentifier(taxIdentifier)
                .withRole(role);

        return purchaserBuilder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxIdentifier() {
        return taxIdentifier;
    }

    public void setTaxIdentifier(String taxIdentifier) {
        this.taxIdentifier = taxIdentifier;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
