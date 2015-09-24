package com.github.mrrigby.trueinvoices.rest.controller;

import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.model.InvoiceItem;
import com.github.mrrigby.trueinvoices.model.PaymentKind;
import com.github.mrrigby.trueinvoices.model.Purchaser;
import com.github.mrrigby.trueinvoices.rest.domain.InvoiceResource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.github.mrrigby.trueinvoices.model.Invoice.anInvoice;
import static com.github.mrrigby.trueinvoices.model.InvoiceItem.anInvoiceItem;
import static com.github.mrrigby.trueinvoices.model.Purchaser.aPurchaser;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author MrRigby
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<InvoiceResource> getInvoice(@PathVariable("id") Long id) {

        Invoice invoice = temporarilyMockedInvoice();
        if (invoice != null) {
            InvoiceResource invoiceResource = assemblyInvoiceResource(invoice);
            return new ResponseEntity<InvoiceResource>(invoiceResource, HttpStatus.OK);
        }

        return new ResponseEntity<InvoiceResource>(HttpStatus.NOT_FOUND);
    }

    // extract to separate class: *Assembler + probably map model class Invoice to some DTO object
    private InvoiceResource assemblyInvoiceResource(Invoice invoice) {

        System.out.println(">>> invoice.getItems(): " + invoice.getItems());

        InvoiceResource invoiceResource = new InvoiceResource(invoice);
        invoiceResource.add(linkTo(methodOn(InvoiceController.class).getInvoice(invoice.getId().get())).withSelfRel());
        return invoiceResource;
    }

    private Invoice temporarilyMockedInvoice() {

        Invoice.Builder invoiceBuilder = anInvoice()
                .withId(100L)
                .withBusinessId("2015/09/0001")
                .withDocumentDate(LocalDate.now())
                .withSoldDate(LocalDate.now())
                .withPaymentKind(PaymentKind.CASH)
                .withItem(anInvoiceItem()
                        .withCommodity("Prunning trees")
                        .withQuantity(1)
                        .withSingleNetPrice(new BigDecimal("499.99"))
                        .withTaxRate((short) 7))
                .withItem(anInvoiceItem()
                        .withCommodity("Planting shrubs")
                        .withQuantity(2)
                        .withSingleNetPrice(new BigDecimal("299.99"))
                        .withTaxRate((short) 23))
                .withPurchaser(aPurchaser()
                        .withName("John Baker")
                        .withAddress("Baker Street 12")
                        .withTaxIdentifier("1234512345")
                        .withRole("Seller"));

        return invoiceBuilder.build();
    }
}
