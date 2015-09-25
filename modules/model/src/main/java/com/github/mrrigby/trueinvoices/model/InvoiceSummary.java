package com.github.mrrigby.trueinvoices.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.mrrigby.trueinvoices.model.InvoiceItemSummary.anInvoiceItemSummary;

/**
 * @author MrRigby
 */
public class InvoiceSummary {

    private final BigDecimal totalNet;
    private final BigDecimal totalGross;
    private final List<InvoiceItemSummary> itemSummaries = new ArrayList<>();

    private InvoiceSummary(BigDecimal totalNet, BigDecimal totalGross, List<InvoiceItemSummary> itemSummaries) {
        this.totalNet = totalNet.setScale(2, RoundingMode.HALF_UP);
        this.totalGross = totalGross.setScale(2, RoundingMode.HALF_UP);
        this.itemSummaries.addAll(itemSummaries);
    }

    @JsonGetter
    public BigDecimal getTotalNet() {
        return totalNet;
    }

    @JsonGetter
    public BigDecimal getTotalGross() {
        return totalGross;
    }

    @JsonGetter("itemSummaries")
    public List<InvoiceItemSummary> getItemSummaries() {
        return itemSummaries;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("totalNet", totalNet)
                .add("totalGross", totalGross)
                .add("itemSummaries", itemSummaries)
                .toString();
    }

    public static class Builder {

        private BigDecimal totalNet = BigDecimal.ZERO;
        private BigDecimal totalGross = BigDecimal.ZERO;
        private final Map<TaxRate, InvoiceItemSummary> taxRateSummaries = new TreeMap<>();

        private Builder() {
        }

        public Builder withItem(InvoiceItem item) {

            BigDecimal itemNetPrice = item.getTotalNetPrice();
            BigDecimal itemGrossPrice = item.getTotalGrossPrice();
            TaxRate itemTaxRate = item.getTaxRate();

            InvoiceItemSummary itemSummary = taxRateSummaries.getOrDefault(
                    itemTaxRate, anInvoiceItemSummary().withTaxRate(itemTaxRate).build());
            itemSummary = itemSummary.addNetPrice(itemNetPrice);
            taxRateSummaries.put(itemTaxRate, itemSummary);

            this.totalNet = this.totalNet.add(itemNetPrice);
            this.totalGross = this.totalGross.add(itemGrossPrice);

            return this;
        }

        public Builder withItems(List<InvoiceItem> items) {
            items.forEach(this::withItem);
            return this;
        }

        public InvoiceSummary build() {
            List<InvoiceItemSummary> itemSummaries = taxRateSummaries.entrySet().stream()
                    .map(entry -> entry.getValue())
                    .collect(Collectors.toList());

            return new InvoiceSummary(this.totalNet, this.totalGross, itemSummaries);
        }
    }

    public static Builder anInvoiceSummary() {
        return new Builder();
    }
}
