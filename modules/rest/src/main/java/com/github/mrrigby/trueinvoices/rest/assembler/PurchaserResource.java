package com.github.mrrigby.trueinvoices.rest.assembler;

import com.github.mrrigby.trueinvoices.model.Purchaser;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

/**
 * HATEOAS resource wrapping the {@link Purchaser} model instance.
 *
 * @author MrRigby
 */
public class PurchaserResource extends ResourceSupport implements Serializable {

    private final Purchaser purchaser;

    public PurchaserResource(Purchaser purchaser) {
        this.purchaser = purchaser;
    }

    public Purchaser getPurchaser() {
        return purchaser;
    }
}
