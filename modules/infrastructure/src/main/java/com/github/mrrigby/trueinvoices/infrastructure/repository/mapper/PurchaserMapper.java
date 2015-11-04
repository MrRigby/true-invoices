package com.github.mrrigby.trueinvoices.infrastructure.repository.mapper;

import com.github.mrrigby.trueinvoices.infrastructure.entity.PurchaserDataEmbeddable;
import com.github.mrrigby.trueinvoices.infrastructure.entity.PurchaserEntity;
import com.github.mrrigby.trueinvoices.model.Purchaser;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import static com.github.mrrigby.trueinvoices.model.Purchaser.PurchaserBuilder.aPurchaser;

/**
 * @author MrRigby
 */
@Service
public class PurchaserMapper {

    public Purchaser entityToModel(PurchaserEntity purchaserEntity) {
        Preconditions.checkNotNull(purchaserEntity);

        return aPurchaser()
                .withId(purchaserEntity.getId())
                .withName(purchaserEntity.getPurchaserData().getName())
                .withAddress(purchaserEntity.getPurchaserData().getAddress())
                .withTaxIdentifier(purchaserEntity.getPurchaserData().getTaxId())
                .build();
    }

    public PurchaserEntity modelToEntity(Purchaser purchaser) {
        PurchaserEntity entity = new PurchaserEntity();
        entity.setId(purchaser.getId().orElse(null));

        PurchaserDataEmbeddable dataSection = new PurchaserDataEmbeddable();
        dataSection.setName(purchaser.getName());
        dataSection.setAddress(purchaser.getAddress());
        dataSection.setTaxId(purchaser.getTaxIdentifier());
        entity.setPurchaserData(dataSection);

        return entity;
    }

}
