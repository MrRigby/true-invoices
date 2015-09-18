package com.github.mrrigby.trueinvoices.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author MrRigby
 */
@Entity
@Table(name = "invoices")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "business_id", length = 50, nullable = false)
    private String businessId;  // --> scenarios of type 2015/02 == year/invoice-number

    @Temporal(TemporalType.DATE)
    @Column(name = "document_date", nullable = false)
    private Date documentDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "sold_date", nullable = false)
    private Date soldDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "payment_kind_id", nullable = false)
    private PaymentKindEntity paymentKind;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id", nullable = false)
    @OrderColumn(name = "item_record_number")
    private List<InvoiceItemEntity> items;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id", nullable = false)
    @OrderColumn(name = "item_record_number")
    private List<InvoicePurchaserEntity> purchasers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentKindEntity getPaymentKind() {
        return paymentKind;
    }

    public void setPaymentKind(PaymentKindEntity paymentKind) {
        this.paymentKind = paymentKind;
    }

    public List<InvoiceItemEntity> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemEntity> items) {
        this.items = items;
    }

    public List<InvoicePurchaserEntity> getPurchasers() {
        return purchasers;
    }

    public void setPurchasers(List<InvoicePurchaserEntity> purchasers) {
        this.purchasers = purchasers;
    }
}