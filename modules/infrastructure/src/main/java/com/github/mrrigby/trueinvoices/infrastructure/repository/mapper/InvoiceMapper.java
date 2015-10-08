package com.github.mrrigby.trueinvoices.infrastructure.repository.mapper;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceEntity;
import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceItemEntity;
import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoicePurchaserEntity;
import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.model.InvoiceItem;
import com.github.mrrigby.trueinvoices.model.Purchaser;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.github.mrrigby.trueinvoices.model.Invoice.anInvoice;

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
                .withPaymentKind(invoiceEntity.getPaymentKind());

        // private List<InvoiceItem> items;
        for (InvoiceItemEntity eachItemEntity : invoiceEntity.getItems()) {
            invoiceBuilder.withItem(invoiceItemMapper.entityToModel(eachItemEntity));
        }

        // private List<Purchaser> purchasers;
        for (InvoicePurchaserEntity eachPurchaserEntity : invoiceEntity.getPurchasers()) {
            invoiceBuilder.withPurchaser(invoicePurchaserMapper.entityToModel(eachPurchaserEntity));
        }

        return invoiceBuilder.build();
    }

    public InvoiceEntity modelToEntity(Invoice invoice) {

        Preconditions.checkNotNull(invoice);

        InvoiceEntity entity = new InvoiceEntity();
        entity.setBusinessId(invoice.getBusinessId());
        entity.setDocumentDate(Date.from(invoice.getDocumentDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        entity.setSoldDate(Date.from(invoice.getSoldDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        entity.setPaymentDate(Date.from(invoice.getPaymentDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        entity.setPaymentKind(invoice.getPaymentKind());

        // private List<InvoiceItem> items;
        List<InvoiceItemEntity> entityItems = new ArrayList<>();
        for (InvoiceItem eachItem : invoice.getItems()) {
            entityItems.add(invoiceItemMapper.modelToEntity(eachItem));
        }
        entity.setItems(entityItems);

        // private List<Purchaser> purchasers;
        List<InvoicePurchaserEntity> entityPurchasers = new ArrayList<>();
        for (Purchaser eachPurchaser: invoice.getPurchasers()) {
            entityPurchasers.add(invoicePurchaserMapper.modelToEntity(eachPurchaser));
        }
        entity.setPurchasers(entityPurchasers);

        return entity;
    }
}

