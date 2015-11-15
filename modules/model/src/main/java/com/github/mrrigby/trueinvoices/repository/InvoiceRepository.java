package com.github.mrrigby.trueinvoices.repository;

import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.repository.dto.InvoiceListFilter;
import com.github.mrrigby.trueinvoices.repository.exceptions.InvoiceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MrRigby
 */
public interface InvoiceRepository {

    /**
     * Tries to get the invoice with given <code>id</code>.
     *
     * @param id identifier of the invoice to find
     * @return invoice object
     * @throws InvoiceNotFoundException when no invoice for given <code>id</code> was found
     */
    public Invoice getById(Long id) throws InvoiceNotFoundException;

    /**
     * Tries to get the invoice with given <code>businessId</code>.
     *
     * @param businessId business identifier of the invoice to find
     * @return invoice object
     * @throws InvoiceNotFoundException when no invoice for given <code>businessId</code> was found
     */
    public Invoice getByBusinessId(String businessId) throws InvoiceNotFoundException;

    /**
     * List invoices for given page.
     *
     * @return chunk with invoices for given page
     */
    public Page<Invoice> listPage(Pageable pageable, InvoiceListFilter filter);

    /**
     * Saves the invoice object.
     *
     * @param invoice invoice object to save
     * @return surrogate identifier generated for the created invoice
     */
    public Invoice save(Invoice invoice);

    /**
     * Updates the invoice object.
     *
     * @param invoice invoice object to update
     */
    public void update(Invoice invoice) throws InvoiceNotFoundException;

}
