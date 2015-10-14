package com.github.mrrigby.trueinvoices.rest.assembler;

import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.rest.controller.InvoiceController;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author MrRigby
 */
@Service
public class InvoiceResourceAssembler {

    public InvoiceResource toHateoasResource(Invoice invoice) {
        InvoiceResource invoiceResource = new InvoiceResource(invoice);
        invoiceResource.add(linkTo(methodOn(InvoiceController.class).getInvoice(invoice.getId().get())).withSelfRel());
        return invoiceResource;
    }

}
