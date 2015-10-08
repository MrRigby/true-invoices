package com.github.mrrigby.trueinvoices.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A ValueObject tax rate.
 *
 * @author MrRigby
 */
public class TaxRate implements Comparable<TaxRate> {

    private static final BigDecimal TO_PERCENT_DIVISOR = new BigDecimal(100);

    private short percentageTaxRate;

    public TaxRate(Short percentageTaxRate) {
        Preconditions.checkNotNull(percentageTaxRate);
        Preconditions.checkArgument(
                (percentageTaxRate >= 0 && percentageTaxRate <= 100),
                "The percentage tax rate has to be between 0 and 100!");
        this.percentageTaxRate = (short) percentageTaxRate;
    }

    @Override
    public String toString() {
        return String.format("%d %%", percentageTaxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.percentageTaxRate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxRate that = (TaxRate) o;

        return Objects.equal(this.percentageTaxRate, that.percentageTaxRate);
    }

    @Override
    public int compareTo(TaxRate that) {
        return Short.compare(this.percentageTaxRate, that.percentageTaxRate);
    }

    @JsonGetter("percentageTaxRate")
    public Short toShort() {
        return Short.valueOf(percentageTaxRate);
    }

    public BigDecimal toFraction() {
        return new BigDecimal(percentageTaxRate)
                .setScale(2, RoundingMode.HALF_UP)
                .divide(TO_PERCENT_DIVISOR, RoundingMode.HALF_UP);
    }

    public BigDecimal grossFor(BigDecimal netPrice) {
        BigDecimal taxValue = netPrice.multiply(this.toFraction());
        BigDecimal calculatedGrossPrice = netPrice.add(taxValue);
        return calculatedGrossPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public static TaxRate valueOf(short percentageTaxRate) {
        return new TaxRate(percentageTaxRate);
    }

    public static TaxRate valueOf(int percentageTaxRate) {
        return valueOf((short) percentageTaxRate);
    }
}
