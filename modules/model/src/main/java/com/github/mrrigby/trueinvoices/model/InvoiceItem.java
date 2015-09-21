package com.github.mrrigby.trueinvoices.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;

/**
 * A single position of the invoice.
 *
 * @author MrRigby
 */
public class InvoiceItem {

    private final String commodity;
    private final BigDecimal quantity;
    private final BigDecimal singleNetPrice;
    private final TaxRate taxRate;

    // calculated derivatives
    private final BigDecimal singleGrossPrice;
    private final BigDecimal totalNetPrice;
    private final BigDecimal totalGrossPrice;

    private InvoiceItem(String commodity, Integer quantity, BigDecimal singleNetPrice, TaxRate taxRate) {

        Preconditions.checkNotNull(commodity);
        Preconditions.checkNotNull(quantity);
        Preconditions.checkNotNull(singleNetPrice);
        Preconditions.checkNotNull(taxRate);

        Preconditions.checkArgument(quantity > 0, "Quantity must be positive number");
        Preconditions.checkArgument(singleNetPrice.compareTo(BigDecimal.ZERO) > 0, "SingleNetPrice must be positive number");

        this.commodity = commodity;
        this.quantity = new BigDecimal(quantity);
        this.singleNetPrice = singleNetPrice;
        this.taxRate = taxRate;

        // eagerly calculated derivatives
        this.singleGrossPrice = singleNetPrice.add(singleNetPrice.multiply(taxRate.toFraction())).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.totalNetPrice = getSingleNetPrice().multiply(this.quantity).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.totalGrossPrice = getSingleGrossPrice().multiply(this.quantity).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public String getCommodity() {
        return commodity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getSingleNetPrice() {
        return singleNetPrice;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public BigDecimal getSingleGrossPrice() {
        return singleGrossPrice;
    }

    public BigDecimal getTotalNetPrice() {
        return totalNetPrice;
    }

    public BigDecimal getTotalGrossPrice() {
        return totalGrossPrice;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("commodity", commodity)
                .add("quantity", quantity)
                .add("singleNetPrice", singleNetPrice)
                .add("taxRate", taxRate)
                .add("singleGrossPrice", getSingleGrossPrice())
                .add("totalNetPrice", getTotalNetPrice())
                .add("totalGrossPrice", getTotalGrossPrice())
                .toString();
    }

    public static class Builder {

        private String commodity;
        private Integer quantity;
        private BigDecimal singleNetPrice;
        private TaxRate taxRate;

        public Builder withCommodity(String commodity) {
            this.commodity = commodity;
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
            this.taxRate = new TaxRate(taxRate);
            return this;
        }

        public InvoiceItem build() {
            return new InvoiceItem(commodity, quantity, singleNetPrice, taxRate);
        }
    }

    public static Builder anInvoiceItem() {
        return new Builder();
    }
}
