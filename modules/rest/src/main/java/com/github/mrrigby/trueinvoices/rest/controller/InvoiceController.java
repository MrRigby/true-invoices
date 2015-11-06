package com.github.mrrigby.trueinvoices.rest.controller;

import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository;
import com.github.mrrigby.trueinvoices.repository.exceptions.InvoiceNotFoundException;
import com.github.mrrigby.trueinvoices.rest.assembler.InvoiceResource;
import com.github.mrrigby.trueinvoices.rest.assembler.InvoiceResourceAssembler;
import com.github.mrrigby.trueinvoices.rest.domain.InvoiceData;
import com.github.mrrigby.trueinvoices.rest.exceptions.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MrRigby
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private InvoiceRepository invoiceRepository;
    private InvoiceResourceAssembler invoiceResourceAssembler;

    @Autowired
    public InvoiceController(InvoiceRepository invoiceRepository,
                             InvoiceResourceAssembler invoiceResourceAssembler) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceResourceAssembler = invoiceResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<PagedResources<InvoiceResource>> getPagedInvoicesList(
            @PageableDefault(size = 20, page = 0) Pageable pageable,
            PagedResourcesAssembler<Invoice> assembler) {
        Page<Invoice> invoicesPage = invoiceRepository.listPage(pageable, null);
        PagedResources<InvoiceResource> invoicesResources = assembler.toResource(invoicesPage, invoiceResourceAssembler);
        return new ResponseEntity<>(invoicesResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<InvoiceResource> getInvoice(@PathVariable("id") Long id) {
        Invoice invoice = invoiceRepository.getById(id);
        InvoiceResource invoiceResource = invoiceResourceAssembler.toResource(invoice);
        return new ResponseEntity<>(invoiceResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<InvoiceResource> saveInvoice(@RequestBody InvoiceData invoiceData) {
        Invoice invoice = invoiceData.toModelBuilder().build();
        Invoice savedInvoice = invoiceRepository.save(invoice);
        InvoiceResource invoiceResource = invoiceResourceAssembler.toResource(savedInvoice);
        return new ResponseEntity<>(invoiceResource, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<InvoiceResource> updateInvoice(
            @PathVariable("id") Long invoiceId, @RequestBody InvoiceData invoiceData) {
        Invoice invoice = invoiceData.toModelBuilder().withId(invoiceId).build();
        invoiceRepository.update(invoice);
        InvoiceResource invoiceResource = invoiceResourceAssembler.toResource(invoice);
        return new ResponseEntity<>(invoiceResource, HttpStatus.OK);
    }

    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<ApiError> invoiceNotFound(
            HttpServletRequest request, HttpServletResponse response, Exception ex) {

        return new ResponseEntity<>(
                new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI().toString()),
                HttpStatus.NOT_FOUND
        );
    }
}
