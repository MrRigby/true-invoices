package com.github.mrrigby.trueinvoices.rest.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.model.PaymentKind;
import com.github.mrrigby.trueinvoices.model.jsonsupport.LocalDateDeserializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import static com.github.mrrigby.trueinvoices.model.Invoice.anInvoice;

/**
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
    private List<PurchaserData> purchasers;

    public Invoice toModel(Long id) {

        Invoice.Builder invoiceBuilder = anInvoice()
                .withId(id)
                .withBusinessId(this.businessId)
                .withDocumentDate(this.documentDate)
                .withSoldDate(this.soldDate)
                .withPaymentKind(this.paymentKind);

        // items

        // purchasers

        return invoiceBuilder.build();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public LocalDate getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(LocalDate soldDate) {
        this.soldDate = soldDate;
    }

    public PaymentKind getPaymentKind() {
        return paymentKind;
    }

    public void setPaymentKind(PaymentKind paymentKind) {
        this.paymentKind = paymentKind;
    }

    public List<InvoiceItemData> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemData> items) {
        this.items = items;
    }

    public List<PurchaserData> getPurchasers() {
        return purchasers;
    }

    public void setPurchasers(List<PurchaserData> purchasers) {
        this.purchasers = purchasers;
    }
}
