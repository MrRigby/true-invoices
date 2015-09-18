package com.github.mrrigby.trueinvoices.model;

import java.util.List;
import java.util.Optional;

/**
 * A model class responsible for holding the data of single invoice. Each
 * invoice is related to one purchaser and consists of at least one position.
 *
 * @author MrRigby
 */
public class Invoice {

    private Optional<Long> id;
    private String number;
    private Purchaser purchaser;
    private List<InvoicePosition> positions;

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Purchaser getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(Purchaser purchaser) {
        this.purchaser = purchaser;
    }

    public List<InvoicePosition> getPositions() {
        return positions;
    }

    public void setPositions(List<InvoicePosition> positions) {
        this.positions = positions;
    }
}
