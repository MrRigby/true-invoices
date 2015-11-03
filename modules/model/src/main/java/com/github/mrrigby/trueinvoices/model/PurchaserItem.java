package com.github.mrrigby.trueinvoices.model;

/**
 * A natural or legal person that did a purchase and has to receive an invoice.
 *
 * @author MrRigby
 */
public class PurchaserItem {

    private String name;
    private String address;
    private String taxIdentifier;

    private String role;

    private PurchaserItem(String name, String address, String taxIdentifier, String role) {
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

        public PurchaserItem build() {
            return new PurchaserItem(name, address, taxIdentifier, role);
        }
    }

    public static Builder aPurchaser() {
        return new Builder();
    }
}
