package com.github.mrrigby.trueinvoices.rest.assembler;

import com.github.mrrigby.trueinvoices.model.Invoice;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

/**
 * @author MrRigby
 */
public class InvoiceResource extends ResourceSupport implements Serializable {

    private final Invoice invoice;

    public InvoiceResource(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
