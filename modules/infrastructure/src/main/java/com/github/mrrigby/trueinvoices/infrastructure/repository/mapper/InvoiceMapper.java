package com.github.mrrigby.trueinvoices.infrastructure.repository.mapper;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceEntity;
import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceItemEntity;
import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoicePurchaserEntity;
import com.github.mrrigby.trueinvoices.model.Invoice;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
