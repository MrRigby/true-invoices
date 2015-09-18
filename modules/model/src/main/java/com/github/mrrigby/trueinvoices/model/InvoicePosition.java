package com.github.mrrigby.trueinvoices.model;

import java.util.Currency;
import java.util.Optional;

/**
 * A single position of the invoice.
 *
 * @author MrRigby
 */
public class InvoicePosition {

    private Optional<Long> id;

    private String commodity;
    private Long quantity;
    private Currency singleNetPrice;
    private Integer vat;

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Currency getSingleNetPrice() {
        return singleNetPrice;
    }

    public void setSingleNetPrice(Currency singleNetPrice) {
        this.singleNetPrice = singleNetPrice;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }
}
