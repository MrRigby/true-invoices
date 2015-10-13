package com.github.mrrigby.trueinvoices.rest.controller;

import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository;
import com.github.mrrigby.trueinvoices.rest.domain.InvoiceData;
import com.github.mrrigby.trueinvoices.rest.domain.InvoiceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author MrRigby
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<InvoiceResource> getInvoice(@PathVariable("id") Long id) {

        Invoice invoice = invoiceRepository.getById(id);
        if (invoice != null) {
            InvoiceResource invoiceResource = assemblyInvoiceResource(invoice);
            return new ResponseEntity<InvoiceResource>(invoiceResource, HttpStatus.OK);
        }

        return new ResponseEntity<InvoiceResource>(HttpStatus.NOT_FOUND);
    }

    // extract to separate class: *Assembler + probably map model class Invoice to some DTO object
    private InvoiceResource assemblyInvoiceResource(Invoice invoice) {

        InvoiceResource invoiceResource = new InvoiceResource(invoice);
        invoiceResource.add(linkTo(methodOn(InvoiceController.class).getInvoice(invoice.getId().get())).withSelfRel());
        return invoiceResource;
    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<InvoiceResource> saveInvoice(@RequestBody InvoiceData invoiceData) {

        try {
            Invoice invoice = invoiceData.toModel();
            Invoice savedInvoice = invoiceRepository.save(invoice);
            InvoiceResource invoiceResource = assemblyInvoiceResource(savedInvoice);
            return new ResponseEntity<InvoiceResource>(invoiceResource, HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<InvoiceResource>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<InvoiceResource> updateInvoice(
            @PathVariable("id") Long invoiceId, @RequestBody InvoiceData invoiceData) {

        try {
            Invoice invoice = invoiceData.toModel(invoiceId);
            boolean updated = invoiceRepository.update(invoice);
            if (!updated) {
                return new ResponseEntity<InvoiceResource>(HttpStatus.NOT_FOUND);
            } else {
                InvoiceResource invoiceResource = assemblyInvoiceResource(invoice);
                return new ResponseEntity<InvoiceResource>(invoiceResource, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<InvoiceResource>(HttpStatus.BAD_REQUEST);
        }
    }

}