package com.github.mrrigby.trueinvoices.repository;

import java.time.LocalDate;

public class InvoiceListFilter {

    private String businessIdMask;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String purchaserMask;

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
