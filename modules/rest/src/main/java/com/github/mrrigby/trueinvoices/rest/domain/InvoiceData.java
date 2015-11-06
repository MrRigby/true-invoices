package com.github.mrrigby.trueinvoices.rest.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.model.PaymentKind;
import com.github.mrrigby.trueinvoices.model.jsonsupport.LocalDateDeserializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import static com.github.mrrigby.trueinvoices.model.Invoice.anInvoice;
import static java.util.stream.Collectors.toList;

/**
 * Object used to deserialize HTTP JSON request to handy JavaBean, that can be further used
 * to easily create {@link Invoice} model.
 *
 * @author MrRigby
 */
public class InvoiceData implements Serializable {

    private String businessId;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate documentDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate soldDate;
    private PaymentKind paymentKind;

    private List<InvoiceItemData> items;
    private List<PurchaserItemData> purchasers;

    public Invoice.Builder toModelBuilder() {

        Invoice.Builder invoiceBuilder = anInvoice()
                .withBusinessId(this.businessId)
                .withDocumentDate(this.documentDate)
                .withSoldDate(this.soldDate)
                .withPaymentKind(this.paymentKind);

        // items
        if (items != null) {
            invoiceBuilder.withItemBuilders(items.stream()
                    .map(InvoiceItemData::toModelBuilder)
                    .collect(toList()));
        }

        // purchasers
        if (purchasers != null) {
            invoiceBuilder.withPurchaserBuilders(purchasers.stream()
                    .map(PurchaserItemData::toModelBuilder)
                    .collect(toList()));
        }

        return invoiceBuilder;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public void setSoldDate(LocalDate soldDate) {
        this.soldDate = soldDate;
    }

    public void setPaymentKind(PaymentKind paymentKind) {
        this.paymentKind = paymentKind;
    }

    public void setItems(List<InvoiceItemData> items) {
        this.items = items;
    }

    public void setPurchasers(List<PurchaserItemData> purchasers) {
        this.purchasers = purchasers;
    }
}
