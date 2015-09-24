package com.github.mrrigby.trueinvoices.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.mrrigby.trueinvoices.model.json.LocalDateSerializer;
import com.github.mrrigby.trueinvoices.model.json.OptionalSerializer;
import com.google.common.base.MoreObjects;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * A model class responsible for holding the data of single invoice. Each
 * invoice is related to one purchaser and consists of at least one position.
 *
 * @author MrRigby
 */
public class Invoice {

    private Optional<Long> id;
    private String businessId;

    private LocalDate documentDate;
    private LocalDate soldDate;

    private PaymentKind paymentKind;
    private List<InvoiceItem> items;
    private List<Purchaser> purchasers;

    // calculated derivatives
    private LocalDate paymentDate;

    private Invoice(Optional<Long> id, String businessId, LocalDate documentDate,
                    LocalDate soldDate, PaymentKind paymentKind,
                    List<InvoiceItem> items, List<Purchaser> purchasers) {
        this.id = id;
        this.businessId = businessId;
        this.documentDate = documentDate;
        this.soldDate = soldDate;
        this.paymentKind = paymentKind;

        this.items = new ArrayList<>(items);
        this.purchasers = new ArrayList<>(purchasers);

        // eagerly calculated derivatives
        this.paymentDate = paymentKind.calculatePaymentDate(soldDate);
    }

    @JsonGetter
    @JsonSerialize(using = OptionalSerializer.LongOptionalSerializer.class)
    public Optional<Long> getId() {
        return id;
    }

    @JsonGetter
    public String getBusinessId() {
        return businessId;
    }

    @JsonGetter
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getDocumentDate() {
        return documentDate;
    }

    @JsonGetter
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getSoldDate() {
        return soldDate;
    }

    @JsonGetter
    public PaymentKind getPaymentKind() {
        return paymentKind;
    }

    @JsonGetter
    public List<InvoiceItem> getItems() {
        return items;
    }

    @JsonGetter
    public List<Purchaser> getPurchasers() {
        return purchasers;
    }

    @JsonGetter
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("businessId", businessId)
                .add("documentDate", documentDate)
                .toString();
    }

    public static class Builder {

        private Optional<Long> id;
        private String businessId;

        private LocalDate documentDate = LocalDate.now();
        private LocalDate soldDate = LocalDate.now();

        private PaymentKind paymentKind = PaymentKind.CASH;
        private List<InvoiceItem> items = new ArrayList<>();
        private List<Purchaser> purchasers = new ArrayList<>();

        private Builder() {
        }

        public Builder withId(Optional<Long> id) {
            this.id = id;
            return this;
        }

        public Builder withId(Long id) {
            this.id = Optional.ofNullable(id);
            return this;
        }

        public Builder withBusinessId(String businessId) {
            this.businessId = businessId;
            return this;
        }

        public Builder withDocumentDate(LocalDate documentDate) {
            this.documentDate = documentDate;
            return this;
        }

        public Builder withDocumentDate(Date documentDate) {
            Instant instant = Instant.ofEpochMilli(documentDate.getTime());
            LocalDate res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            return this;
        }

        public Builder withSoldDate(LocalDate soldDate) {
            this.soldDate = soldDate;
            return this;
        }

        public Builder withSoldDate(Date soldDate) {
            Instant instant = Instant.ofEpochMilli(soldDate.getTime());
            LocalDate res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            return this;
        }

        public Builder withPaymentKind(PaymentKind paymentKind) {
            this.paymentKind = paymentKind;
            return this;
        }

        public Builder withItem(InvoiceItem item) {
            this.items.add(item);
            return this;
        }

        public Builder withItem(InvoiceItem.Builder itemBuilder) {
            this.withItem(itemBuilder.build());
            return this;
        }

        public Builder withItems(InvoiceItem... items) {
            Arrays.asList(items).forEach(this::withItem);
            return this;
        }

        public Builder withItems(InvoiceItem.Builder... itemBuilders) {
            Arrays.asList(itemBuilders).forEach(this::withItem);
            return this;
        }

        public Builder withPurchaser(Purchaser purchaser) {
            this.purchasers.add(purchaser);
            return this;
        }

        public Builder withPurchaser(Purchaser.Builder purchaserBuilder) {
            this.withPurchaser(purchaserBuilder.build());
            return this;
        }

        public Builder withPurchasers(Purchaser... purchasers) {
            Arrays.asList(purchasers).forEach(this::withPurchaser);
            return this;
        }

        public Builder withPurchasers(Purchaser.Builder... purchaserBuilders) {
            Arrays.asList(purchaserBuilders).forEach(this::withPurchaser);
            return this;
        }

        public Invoice build() {
            return new Invoice(id, businessId, documentDate, soldDate, paymentKind, items, purchasers);
        }
    }

    public static Builder anInvoice() {
        return new Builder();
    }
}
