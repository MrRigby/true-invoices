package com.github.mrrigby.trueinvoices.repository;

import com.github.mrrigby.trueinvoices.model.Invoice;

/**
 * @author MrRigby
 */
public interface InvoiceRepository {

    public Invoice getById(Long id);

    public Invoice getByBusinessId(String businessId);

    public void save(Invoice invoice);

    public void update(Invoice invoice);

}
