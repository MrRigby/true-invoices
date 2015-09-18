package com.github.mrrigby.trueinvoices.entity;

import javax.persistence.*;

/**
 * @author MrRigby
 */
@Entity
@Table(name = "invoice_purchasers")
public class InvoicePurchaserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Embedded
    private PurchaserDataEmbeddable purchaserData;

    @Column(name = "role")
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PurchaserDataEmbeddable getPurchaserData() {
        return purchaserData;
    }

    public void setPurchaserData(PurchaserDataEmbeddable purchaserData) {
        this.purchaserData = purchaserData;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
