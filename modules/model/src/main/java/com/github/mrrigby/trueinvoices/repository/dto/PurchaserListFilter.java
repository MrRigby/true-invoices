package com.github.mrrigby.trueinvoices.repository.dto;

/**
 * Object with parameters used to limit list of purchasers.
 *
 * @author MrRigby
 */
public class PurchaserListFilter {

    private String nameMask;
    private String taxIdentifier;

    public static PurchaserListFilter from(String name, String taxId) {
        PurchaserListFilter filter = new PurchaserListFilter();
        filter.nameMask = name;
        filter.taxIdentifier = taxId;
        return filter;
    }

    public String getNameMask() {
        return nameMask;
    }

    public void setNameMask(String nameMask) {
        this.nameMask = nameMask;
    }

    public String getTaxIdentifier() {
        return taxIdentifier;
    }

    public void setTaxIdentifier(String taxIdentifier) {
        this.taxIdentifier = taxIdentifier;
    }

}
