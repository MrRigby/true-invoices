package com.github.mrrigby.trueinvoices.repository;

import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.repository.exceptions.InvoiceNotFoundException;

/**
 * @author MrRigby
 */
public interface InvoiceRepository {

    public Invoice getById(Long id) throws InvoiceNotFoundException;

    public Invoice getByBusinessId(String businessId) throws InvoiceNotFoundException;

    public void save(Invoice invoice);

    public void update(Invoice invoice);

}
