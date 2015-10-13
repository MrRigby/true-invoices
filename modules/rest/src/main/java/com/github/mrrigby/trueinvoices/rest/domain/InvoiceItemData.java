package com.github.mrrigby.trueinvoices.rest.domain;

import com.github.mrrigby.trueinvoices.model.InvoiceItem;
import com.github.mrrigby.trueinvoices.model.TaxRate;

import java.math.BigDecimal;

import static com.github.mrrigby.trueinvoices.model.InvoiceItem.anInvoiceItem;

/**
 * @author MrRigby
 */
public class InvoiceItemData {

    private String commodity;
    private String auxiliarySymbol;
    private String measure;
    private Integer quantity;
    private BigDecimal singleNetPrice;
    private TaxRate taxRate;

    public InvoiceItem toModel() {

        InvoiceItem.Builder itemBuilder = anInvoiceItem()
                .withCommodity(commodity)
                .withAuxiliarySymbol(auxiliarySymbol)
                .withMeasure(measure)
                .withQuantity(quantity)
                .withSingleNetPrice(singleNetPrice)
                .withTaxRate(taxRate);

        return itemBuilder.build();
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getAuxiliarySymbol() {
        return auxiliarySymbol;
    }

    public void setAuxiliarySymbol(String auxiliarySymbol) {
        this.auxiliarySymbol = auxiliarySymbol;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSingleNetPrice() {
        return singleNetPrice;
    }

    public void setSingleNetPrice(BigDecimal singleNetPrice) {
        this.singleNetPrice = singleNetPrice;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(TaxRate taxRate) {
        this.taxRate = taxRate;
    }
}
