package com.github.mrrigby.trueinvoices.infrastructure.repository.mapper;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceEntity;
import com.github.mrrigby.trueinvoices.model.Invoice;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

import static com.github.mrrigby.trueinvoices.model.Invoice.anInvoice;
import static java.util.stream.Collectors.toList;

/**
 * @author MrRigby
 */
@Service
public class InvoiceMapper {

    private InvoiceItemMapper invoiceItemMapper;
    private InvoicePurchaserMapper invoicePurchaserMapper;

    @Autowired
    public InvoiceMapper(InvoiceItemMapper invoiceItemMapper,
                         InvoicePurchaserMapper invoicePurchaserMapper) {
        this.invoiceItemMapper = invoiceItemMapper;
        this.invoicePurchaserMapper = invoicePurchaserMapper;
    }

    public Invoice entityToModel(InvoiceEntity invoiceEntity) {

        Preconditions.checkNotNull(invoiceEntity);

        Invoice.Builder invoiceBuilder = anInvoice()
                .withId(invoiceEntity.getId())
                .withBusinessId(invoiceEntity.getBusinessId())
                .withDocumentDate(invoiceEntity.getDocumentDate())
                .withSoldDate(invoiceEntity.getSoldDate())
                .withPaymentKind(invoiceEntity.getPaymentKind())
                .withItems(invoiceEntity.getItems().stream()
                                .map(invoiceItemMapper::entityToModel)
                                .collect(toList()))
                .withPurchasers(invoiceEntity.getPurchasers().stream()
                                .map(invoicePurchaserMapper::entityToModel)
                                .collect(toList()));

        return invoiceBuilder.build();
    }

    public InvoiceEntity modelToEntity(Invoice invoice) {

        Preconditions.checkNotNull(invoice);

        InvoiceEntity entity = new InvoiceEntity();
        entity.setId(invoice.getId().orElse(null));
        entity.setBusinessId(invoice.getBusinessId());
        entity.setDocumentDate(Date.from(invoice.getDocumentDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        entity.setSoldDate(Date.from(invoice.getSoldDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        entity.setPaymentDate(Date.from(invoice.getPaymentDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        entity.setPaymentKind(invoice.getPaymentKind());
        entity.setItems(invoice.getItems().stream()
                        .map(invoiceItemMapper::modelToEntity)
                        .collect(toList()));
        entity.setPurchasers(invoice.getPurchaserItems().stream()
                        .map(invoicePurchaserMapper::modelToEntity)
                        .collect(toList()));

        return entity;
    }
}
