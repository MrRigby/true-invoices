package com.github.mrrigby.trueinvoices.rest.controller;

import com.github.mrrigby.trueinvoices.model.Purchaser;
import com.github.mrrigby.trueinvoices.rest.assembler.PurchaserResource;
import com.github.mrrigby.trueinvoices.rest.assembler.PurchaserResourceAssembler;
import com.github.mrrigby.trueinvoices.rest.domain.PurchaserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.github.mrrigby.trueinvoices.model.Purchaser.PurchaserBuilder.aPurchaser;

/**
 * @author MrRigby
 */
@RestController
@RequestMapping("/purchaser")
public class PurchaserController {

    // private PurchaserRepository purchaserRepository;
    private PurchaserResourceAssembler purchaserResourceAssembler;

    @Autowired
    public PurchaserController(PurchaserResourceAssembler purchaserResourceAssembler) {
        this.purchaserResourceAssembler = purchaserResourceAssembler;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<PurchaserResource> getPurchaser(@PathVariable("id") Long id) {
        Purchaser purchaser = aPurchaser()
                .withId(Optional.ofNullable(1L))
                .withName("Zenon Makaron")
                .withAddress("Wall Street 12")
                .withTaxIdentifier("1234567890")
                .build();
        PurchaserResource purchaserResource = purchaserResourceAssembler.toResource(purchaser);
        return new ResponseEntity<>(purchaserResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<PurchaserResource> updatePurchaser(
            @PathVariable("id") Long purchaserId, @RequestBody PurchaserData purchaserData) {
        // FIXME original PurchaserData --> PurchaserItemData
        PurchaserResource purchaserResource = null;
        return new ResponseEntity<>(purchaserResource, HttpStatus.OK);
    }

}
