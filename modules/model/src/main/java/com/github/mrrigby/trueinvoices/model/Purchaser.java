package com.github.mrrigby.trueinvoices.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.mrrigby.trueinvoices.model.jsonsupport.OptionalSerializer;

import java.util.Optional;

/**
 * A model class responsible for holding the data of single purchaser. The data hold
 * by this object is a kind of template that can used later and associated with
 * {@link Invoice} as as {@link Invoice#purchaserItems purchaserItem}.
 *
 * @author MrRigby
 */
public class Purchaser {

    private Optional<Long> id;
    private String name;
    private String address;
    private String taxIdentifier;

    public Purchaser(Optional<Long> id, String name, String address, String taxIdentifier) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.taxIdentifier = taxIdentifier;
    }

    @JsonGetter
    @JsonSerialize(using = OptionalSerializer.LongOptionalSerializer.class)
    public Optional<Long> getId() {
        return id;
    }

    @JsonGetter
    public String getName() {
        return name;
    }

    @JsonGetter
    public String getAddress() {
        return address;
    }

    @JsonGetter
    public String getTaxIdentifier() {
        return taxIdentifier;
    }

    public static class PurchaserBuilder {
        private Optional<Long> id = Optional.empty();;
        private String name;
        private String address;
        private String taxIdentifier;

        private PurchaserBuilder() {
        }

        public static PurchaserBuilder aPurchaser() {
            return new PurchaserBuilder();
        }

        public PurchaserBuilder withId(Optional<Long> id) {
            this.id = id;
            return this;
        }

        public PurchaserBuilder withId(Long id) {
            this.id = Optional.ofNullable(id);
            return this;
        }

        public PurchaserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PurchaserBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public PurchaserBuilder withTaxIdentifier(String taxIdentifier) {
            this.taxIdentifier = taxIdentifier;
            return this;
        }

        public Purchaser build() {
            return new Purchaser(id, name, address, taxIdentifier);
        }
    }
}
