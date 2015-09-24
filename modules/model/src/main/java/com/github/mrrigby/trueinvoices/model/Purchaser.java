package com.github.mrrigby.trueinvoices.model;

import java.util.Optional;

/**
 * A natural or legal person that did a purchase and has to receive an invoice.
 *
 * @author MrRigby
 */
public class Purchaser {

    private String name;
    private String address;
    private String taxIdentifier;

    private String role;

    private Purchaser(String name, String address, String taxIdentifier, String role) {
        this.name = name;
        this.address = address;
        this.taxIdentifier = taxIdentifier;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTaxIdentifier() {
        return taxIdentifier;
    }

    public String getRole() {
        return role;
    }

    public static class Builder {

        private String name;
        private String address;
        private String taxIdentifier;
        private String role;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withTaxIdentifier(String taxIdentifier) {
            this.taxIdentifier = taxIdentifier;
            return this;
        }

        public Builder withRole(String role) {
            this.role = role;
            return this;
        }

        public Purchaser build() {
            return new Purchaser(name, address, taxIdentifier, role);
        }
    }

    public static Builder aPurchaser() {
        return new Builder();
    }
}
