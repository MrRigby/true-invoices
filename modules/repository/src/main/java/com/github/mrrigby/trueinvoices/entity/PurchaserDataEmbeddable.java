package com.github.mrrigby.trueinvoices.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author MrRigby
 */
@Embeddable
public class PurchaserDataEmbeddable {

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "tax_id", length = 10)
    private String taxId;

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

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
}
