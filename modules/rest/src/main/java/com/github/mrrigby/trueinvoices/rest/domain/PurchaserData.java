package com.github.mrrigby.trueinvoices.rest.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mrrigby.trueinvoices.model.Purchaser.PurchaserBuilder;


/**
 * Object used to deserialize HTTP JSON request to handy JavaBean, that can be further used
 * to easily create {@link com.github.mrrigby.trueinvoices.model.Purchaser} model.
 *
 * @author MrRigby
 */
public class PurchaserData {

    private String name;
    private String address;
    private String taxIdentifier;

    @JsonCreator
    public PurchaserData(@JsonProperty("name") String name,
                         @JsonProperty("address") String address,
                         @JsonProperty("taxIdentifier") String taxIdentifier) {
        this.name = name;
        this.address = address;
        this.taxIdentifier = taxIdentifier;
    }

    public PurchaserBuilder toModelBuilder() {
        return PurchaserBuilder.aPurchaser()
                .withName(this.name)
                .withAddress(this.address)
                .withTaxIdentifier(this.taxIdentifier);
    }
}
