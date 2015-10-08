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
                .withAuxiliarySymbol(itemEntity.getAuxiliarySymbol())
                .withMeasure(itemEntity.getMeasure())
                .withQuantity(itemEntity.getQuantity())
                .withSingleNetPrice(itemEntity.getSingleNetPrice())
                .withTaxRate(itemEntity.getTaxRate());

        return invoiceItemBuilder.build();
    }

    public InvoiceItemEntity modelToEntity(InvoiceItem item) {

        Preconditions.checkNotNull(item);

        InvoiceItemEntity itemEntity = new InvoiceItemEntity();
        itemEntity.setQuantity(item.getQuantity());
        itemEntity.setCommodity(item.getCommodity());
        itemEntity.setAuxiliarySymbol(item.getAuxiliarySymbol());
        itemEntity.setMeasure(item.getMeasure());
        itemEntity.setSingleNetPrice(item.getSingleNetPrice());
        itemEntity.setTaxRate(item.getTaxRate().toShort());

        return itemEntity;
    }
}
