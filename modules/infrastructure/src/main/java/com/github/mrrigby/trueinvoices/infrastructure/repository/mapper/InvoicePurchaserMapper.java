package com.github.mrrigby.trueinvoices.infrastructure.repository.mapper;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoicePurchaserEntity;
import com.github.mrrigby.trueinvoices.infrastructure.entity.PurchaserDataEmbeddable;
import com.github.mrrigby.trueinvoices.model.Purchaser;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import static com.github.mrrigby.trueinvoices.model.Purchaser.aPurchaser;

/**
 * @author MrRigby
 */
@Service
public class InvoicePurchaserMapper {

    public Purchaser entityToModel(InvoicePurchaserEntity purchaserEntity) {

        Preconditions.checkNotNull(purchaserEntity);

        Purchaser.Builder purchaser = aPurchaser()
                .withName(purchaserEntity.getPurchaserData().getName())
                .withAddress(purchaserEntity.getPurchaserData().getAddress())
                .withTaxIdentifier(purchaserEntity.getPurchaserData().getTaxId())
                .withRole(purchaserEntity.getRole());

        return purchaser.build();
    }

    public InvoicePurchaserEntity modelToEntity(Purchaser purchaser) {

        Preconditions.checkNotNull(purchaser);

        InvoicePurchaserEntity purchaserEntity = new InvoicePurchaserEntity();

        PurchaserDataEmbeddable dataSection = new PurchaserDataEmbeddable();
        dataSection.setName(purchaser.getName());
        dataSection.setAddress(purchaser.getAddress());
        dataSection.setTaxId(purchaser.getTaxIdentifier());
        purchaserEntity.setPurchaserData(dataSection);

        purchaserEntity.setRole(purchaser.getRole());

        return purchaserEntity;
    }
}
