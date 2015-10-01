package com.github.mrrigby.trueinvoices.rest.domain;

import com.github.mrrigby.trueinvoices.model.Invoice;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author MrRigby
 */
public class InvoiceResource extends ResourceSupport {

    private final Invoice invoice;

    public InvoiceResource(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
