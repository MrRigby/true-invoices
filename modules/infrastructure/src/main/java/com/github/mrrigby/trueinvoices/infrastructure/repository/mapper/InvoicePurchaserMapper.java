package com.github.mrrigby.trueinvoices.infrastructure.repository.mapper;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoicePurchaserEntity;
import com.github.mrrigby.trueinvoices.infrastructure.entity.PurchaserDataEmbeddable;
import com.github.mrrigby.trueinvoices.model.PurchaserItem;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import static com.github.mrrigby.trueinvoices.model.PurchaserItem.aPurchaser;

/**
 * @author MrRigby
 */
@Service
public class InvoicePurchaserMapper {

    public PurchaserItem entityToModel(InvoicePurchaserEntity purchaserEntity) {

        Preconditions.checkNotNull(purchaserEntity);

        PurchaserItem.Builder purchaser = aPurchaser()
                .withName(purchaserEntity.getPurchaserData().getName())
                .withAddress(purchaserEntity.getPurchaserData().getAddress())
                .withTaxIdentifier(purchaserEntity.getPurchaserData().getTaxId())
                .withRole(purchaserEntity.getRole());

        return purchaser.build();
    }

    public InvoicePurchaserEntity modelToEntity(PurchaserItem purchaserItem) {

        Preconditions.checkNotNull(purchaserItem);

        InvoicePurchaserEntity purchaserEntity = new InvoicePurchaserEntity();

        PurchaserDataEmbeddable dataSection = new PurchaserDataEmbeddable();
        dataSection.setName(purchaserItem.getName());
        dataSection.setAddress(purchaserItem.getAddress());
        dataSection.setTaxId(purchaserItem.getTaxIdentifier());
        purchaserEntity.setPurchaserData(dataSection);

        purchaserEntity.setRole(purchaserItem.getRole());

        return purchaserEntity;
    }
}
