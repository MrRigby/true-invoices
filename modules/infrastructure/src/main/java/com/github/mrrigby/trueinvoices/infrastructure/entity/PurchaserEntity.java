package com.github.mrrigby.trueinvoices.infrastructure.entity;

import javax.persistence.*;

/**
 * @author MrRigby
 */
@Entity
@Table(name = "purchasers")
public class PurchaserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Embedded
    private PurchaserDataEmbeddable purchaserData;

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
}
