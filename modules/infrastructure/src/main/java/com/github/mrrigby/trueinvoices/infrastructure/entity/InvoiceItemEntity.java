package com.github.mrrigby.trueinvoices.infrastructure.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author MrRigby
 */
@Entity
@Table(name = "invoice_items")
public class InvoiceItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "commodity", length = 250, nullable = false)
    private String commodity;

    @Column(name = "symbol", length = 20, nullable = false)
    private String symbol;

    @Column(name = "measure", length = 20, nullable = false)
    private String measure;

    @Column(name = "single_net_price", scale = 2, nullable = false)
    private BigDecimal singleNetPrice;

    @ManyToOne
    @JoinColumn(name = "tax_rate_id", nullable = false)
    private TaxRateEntity taxRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public BigDecimal getSingleNetPrice() {
        return singleNetPrice;
    }

    public void setSingleNetPrice(BigDecimal singleNetPrice) {
        this.singleNetPrice = singleNetPrice;
    }

    public TaxRateEntity getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(TaxRateEntity taxRate) {
        this.taxRate = taxRate;
    }
}
