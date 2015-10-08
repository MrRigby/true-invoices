package com.github.mrrigby.trueinvoices.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A single position of the invoice.
 *
 * @author MrRigby
 */
public class InvoiceItem {

    private final String commodity;
    private final String auxiliarySymbol;
    private final String measure;
    private final Integer quantity;
    private final BigDecimal singleNetPrice;
    private final TaxRate taxRate;

    // calculated derivatives
    private final BigDecimal singleGrossPrice;
    private final BigDecimal totalNetPrice;
    private final BigDecimal totalGrossPrice;

    private InvoiceItem(String commodity, String auxiliarySymbol, String measure,
                        Integer quantity, BigDecimal singleNetPrice, TaxRate taxRate) {

        Preconditions.checkNotNull(commodity);
        Preconditions.checkNotNull(quantity);
        Preconditions.checkNotNull(singleNetPrice);
        Preconditions.checkNotNull(taxRate);

        Preconditions.checkArgument(quantity > 0, "Quantity must be positive number");
        Preconditions.checkArgument(singleNetPrice.compareTo(BigDecimal.ZERO) >= 0, "SingleNetPrice cannot be negative number!");

        this.commodity = commodity;
        this.auxiliarySymbol = auxiliarySymbol;
        this.measure = measure;
        this.quantity = quantity;
        this.singleNetPrice = singleNetPrice.setScale(2, RoundingMode.HALF_UP);
        this.taxRate = taxRate;

        // eagerly calculated derivatives
        this.singleGrossPrice = taxRate.grossFor(singleNetPrice);
        this.totalNetPrice = getSingleNetPrice().multiply(new BigDecimal(this.quantity)).setScale(2, RoundingMode.HALF_UP);
        this.totalGrossPrice = getSingleGrossPrice().multiply(new BigDecimal(this.quantity)).setScale(2, RoundingMode.HALF_UP);
    }

    @JsonGetter
    public String getCommodity() {
        return commodity;
    }

    @JsonGetter
    public String getAuxiliarySymbol() {
        return auxiliarySymbol;
    }

    @JsonGetter
    public String getMeasure() {
        return measure;
    }

    @JsonGetter
    public Integer getQuantity() {
        return quantity;
    }

    @JsonGetter
    public BigDecimal getSingleNetPrice() {
        return singleNetPrice;
    }

    @JsonGetter
    @JsonUnwrapped
    public TaxRate getTaxRate() {
        return taxRate;
    }

    @JsonGetter
    public BigDecimal getSingleGrossPrice() {
        return singleGrossPrice;
    }

    @JsonGetter
    public BigDecimal getTotalNetPrice() {
        return totalNetPrice;
    }

    @JsonGetter
    public BigDecimal getTotalGrossPrice() {
        return totalGrossPrice;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("commodity", commodity)
                .add("auxiliarySymbol", auxiliarySymbol)
                .add("measure", measure)
                .add("quantity", quantity)
                .add("singleNetPrice", singleNetPrice)
                .add("taxRate", taxRate)
                .add("singleGrossPrice", getSingleGrossPrice())
                .add("totalNetPrice", getTotalNetPrice())
                .add("totalGrossPrice", getTotalGrossPrice())
                .toString();
    }

    public static class Builder {

        private String commodity = "...";
        private String auxiliarySymbol = "-";
        private String measure = "-";
        private Integer quantity = 1;
        private BigDecimal singleNetPrice;
        private TaxRate taxRate;

        public Builder withCommodity(String commodity) {
            this.commodity = commodity;
            return this;
        }

        public Builder withAuxiliarySymbol(String auxiliarySymbol) {
            this.auxiliarySymbol = auxiliarySymbol;
            return this;
        }

        public Builder withMeasure(String measure) {
            this.measure = measure;
            return this;
        }

        public Builder withQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withSingleNetPrice(BigDecimal singleNetPrice) {
            this.singleNetPrice = singleNetPrice;
            return this;
        }

        public Builder withTaxRate(TaxRate taxRate) {
            this.taxRate = taxRate;
            return this;
        }

        public Builder withTaxRate(Short taxRate) {
            this.taxRate = TaxRate.valueOf(taxRate);
            return this;
        }

        public InvoiceItem build() {
            return new InvoiceItem(commodity, auxiliarySymbol, measure, quantity, singleNetPrice, taxRate);
        }
    }

    public static Builder anInvoiceItem() {
        return new Builder();
    }
}
