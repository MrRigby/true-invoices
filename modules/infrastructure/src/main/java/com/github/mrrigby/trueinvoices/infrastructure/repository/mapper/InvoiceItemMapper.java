package com.github.mrrigby.trueinvoices.infrastructure.repository.mapper;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceItemEntity;
import com.github.mrrigby.trueinvoices.model.InvoiceItem;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import static com.github.mrrigby.trueinvoices.model.InvoiceItem.anInvoiceItem;

/**
 * @author MrRigby
 */
@Service
public class InvoiceItemMapper {

    public InvoiceItem entityToModel(InvoiceItemEntity itemEntity) {

        Preconditions.checkNotNull(itemEntity);

        InvoiceItem.Builder invoiceItemBuilder = anInvoiceItem()
                .withCommodity(itemEntity.getCommodity())
                .withQuantity(itemEntity.getQuantity())
                .withSingleNetPrice(itemEntity.getSingleNetPrice())
                .withTaxRate(itemEntity.getTaxRate().getValue());

        return invoiceItemBuilder.build();
    }
}
