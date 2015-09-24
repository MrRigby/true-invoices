package com.github.mrrigby.trueinvoices.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author MrRigby
 */
public class TaxRate {

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

    @JsonGetter("percentageTaxRate")
    public Short toShort() {
        return new Short(percentageTaxRate);
    }

    public BigDecimal toFraction() {
        return new BigDecimal(percentageTaxRate).setScale(2).divide(TO_PERCENT_DIVISOR, RoundingMode.HALF_UP);
    }
}
