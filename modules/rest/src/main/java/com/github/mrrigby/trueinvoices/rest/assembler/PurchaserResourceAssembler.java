package com.github.mrrigby.trueinvoices.rest.assembler;

import com.github.mrrigby.trueinvoices.model.Purchaser;
import com.github.mrrigby.trueinvoices.rest.controller.PurchaserController;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * HATEOAS assembler able to transform {@link Purchaser} model into {@link PurchaserResource}.
 *
 * @author MrRigby
 */
@Service
public class PurchaserResourceAssembler implements ResourceAssembler<Purchaser, PurchaserResource> {

    public static final String REL_UPDATE = "update";

    @Override
    public PurchaserResource toResource(Purchaser purchaser) {
        PurchaserResource purchaserResource = new PurchaserResource(purchaser);

        Long purchaserId = purchaser.getId().get();

        purchaserResource.add(linkTo(methodOn(PurchaserController.class)
                .getPurchaser(purchaserId))
                .withSelfRel());
        purchaserResource.add(linkTo(methodOn(PurchaserController.class)
                .updatePurchaser(purchaserId, null))
                .withRel(REL_UPDATE));

        // TODO delete? other actions?

        return purchaserResource;
    }
}
