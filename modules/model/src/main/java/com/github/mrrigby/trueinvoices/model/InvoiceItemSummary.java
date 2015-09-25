package com.github.mrrigby.trueinvoices.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author MrRigby
 */
public class InvoiceItemSummary {

    private final TaxRate taxRate;
    private final BigDecimal netPrice;

    // calculated derivatives
    private final BigDecimal grossPrice;

    private InvoiceItemSummary(TaxRate taxRate, BigDecimal netPrice) {

        Preconditions.checkNotNull(taxRate);
        Preconditions.checkNotNull(netPrice);
        Preconditions.checkArgument(netPrice.compareTo(BigDecimal.ZERO) >= 0, "SingleNetPrice must be positive number");

        this.taxRate = taxRate;
        this.netPrice = netPrice.setScale(2, RoundingMode.HALF_UP);

        // eagerly calculated derivatives
        this.grossPrice = taxRate.grossFor(netPrice);
    }

    @JsonGetter
    @JsonUnwrapped
    public TaxRate getTaxRate() {
        return taxRate;
    }

    @JsonGetter
    public BigDecimal getNetPrice() {
        return netPrice;
    }

    @JsonGetter
    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public InvoiceItemSummary addNetPrice(BigDecimal netPrice) {
        BigDecimal newNetPrice = this.netPrice.add(netPrice);
        return anInvoiceItemSummary()
                .withTaxRate(this.taxRate)
                .withNetPrice(newNetPrice)
                .build();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("taxRate", taxRate)
                .add("netPrice", netPrice)
                .add("grossPrice", grossPrice)
                .toString();
    }

    public static class Builder {

        private TaxRate taxRate;
        private BigDecimal netPrice = BigDecimal.ZERO;

        private Builder() {
        }

        public Builder withTaxRate(TaxRate taxRate) {
            this.taxRate = taxRate;
            return this;
        }

        public Builder withNetPrice(BigDecimal netPrice) {
            this.netPrice = netPrice;
            return this;
        }

        public InvoiceItemSummary build() {
            return new InvoiceItemSummary(this.taxRate, this.netPrice);
        }
    }

    public static Builder anInvoiceItemSummary() {
        return new Builder();
    }
}
