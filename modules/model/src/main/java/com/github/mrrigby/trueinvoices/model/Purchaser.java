package com.github.mrrigby.trueinvoices.model;

import java.util.Optional;

/**
 * A natural or legal person that did a purchase and has to receive an invoice.
 *
 * @author MrRigby
 */
public class Purchaser {

    private Optional<Long> id;

    private String name;
    private String address;
    private String taxIdentifier;

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
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
}
