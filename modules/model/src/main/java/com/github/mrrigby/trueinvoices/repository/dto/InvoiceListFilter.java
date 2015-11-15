package com.github.mrrigby.trueinvoices.repository.dto;

import java.time.LocalDate;

/**
 * Object with parameters used to limit list of invoices.
 *
 * @author MrRigby
 */
public class InvoiceListFilter {

    private String businessIdMask;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String purchaserMask;

    public static InvoiceListFilter from(String businessIdMask, LocalDate dateFrom, LocalDate dateTo, String purchaserMask) {
        InvoiceListFilter filter = new InvoiceListFilter();
        filter.businessIdMask = businessIdMask;
        filter.dateFrom = dateFrom;
        filter.dateTo = dateTo;
        filter.purchaserMask = purchaserMask;
        return filter;
    }

    public String getBusinessIdMask() {
        return businessIdMask;
    }

    public void setBusinessIdMask(String businessIdMask) {
        this.businessIdMask = businessIdMask;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getPurchaserMask() {
        return purchaserMask;
    }

    public void setPurchaserMask(String purchaserMask) {
        this.purchaserMask = purchaserMask;
    }
}
