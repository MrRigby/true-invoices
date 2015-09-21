package com.github.mrrigby.trueinvoices.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.time.LocalDate;
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

        // eagerly calculated derivatives
        this.paymentDate = paymentKind.calculatePaymentDate(soldDate);
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
            this.documentDate = documentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return this;
        }

        public Builder withSoldDate(LocalDate soldDate) {
            this.soldDate = soldDate;
            return this;
        }

        public Builder withSoldDate(Date soldDate) {
            this.soldDate = soldDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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

        public Builder withItems(InvoiceItem... items) {
            this.items.addAll(Arrays.asList(items));
            return this;
        }

        public Builder withPurchaser(Purchaser purchaser) {
            this.purchasers.add(purchaser);
            return this;
        }

        public Builder withPurchasers(Purchaser... purchasers) {
            this.purchasers.addAll(Arrays.asList(purchasers));
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
