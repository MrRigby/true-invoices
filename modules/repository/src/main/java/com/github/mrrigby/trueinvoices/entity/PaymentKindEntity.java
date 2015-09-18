package com.github.mrrigby.trueinvoices.entity;

import javax.persistence.*;

/**
 * @author MrRigby
 */
@Entity
@Table(name = "payment_kinds")
public class PaymentKindEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Column(name = "formula", length = 10, nullable = false)
    private String formula;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}
