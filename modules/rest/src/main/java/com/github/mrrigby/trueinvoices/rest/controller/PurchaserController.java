package com.github.mrrigby.trueinvoices.rest.controller;

import com.github.mrrigby.trueinvoices.model.Purchaser;
import com.github.mrrigby.trueinvoices.repository.PurchaserRepository;
import com.github.mrrigby.trueinvoices.repository.exceptions.PurchaserNotFoundException;
import com.github.mrrigby.trueinvoices.rest.assembler.PurchaserResource;
import com.github.mrrigby.trueinvoices.rest.assembler.PurchaserResourceAssembler;
import com.github.mrrigby.trueinvoices.rest.domain.PurchaserData;
import com.github.mrrigby.trueinvoices.rest.exceptions.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MrRigby
 */
@RestController
@RequestMapping("/purchaser")
public class PurchaserController {

    private PurchaserRepository purchaserRepository;
    private PurchaserResourceAssembler purchaserResourceAssembler;

    @Autowired
    public PurchaserController(PurchaserRepository purchaserRepository,
                               PurchaserResourceAssembler purchaserResourceAssembler) {
        this.purchaserRepository = purchaserRepository;
        this.purchaserResourceAssembler = purchaserResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<PagedResources<PurchaserResource>> getPagedPurchasersList(
            @PageableDefault(size = 20, page = 0) Pageable pageable,
            PagedResourcesAssembler<Purchaser> assembler) {
        Page<Purchaser> purchasersPage = purchaserRepository.listPage(pageable, null);
        PagedResources<PurchaserResource> purchasersResources = assembler.toResource(purchasersPage, purchaserResourceAssembler);
        return new ResponseEntity<>(purchasersResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<PurchaserResource> getPurchaser(@PathVariable("id") Long id) {
        Purchaser purchaser = purchaserRepository.getById(id);
        PurchaserResource purchaserResource = purchaserResourceAssembler.toResource(purchaser);
        return new ResponseEntity<>(purchaserResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<PurchaserResource> savePurchaser(@RequestBody PurchaserData purchaserData) {
        Purchaser purchaser = purchaserData.toModelBuilder().build();
        Purchaser savedPurchaser = purchaserRepository.save(purchaser);
        PurchaserResource purchaserResource = purchaserResourceAssembler.toResource(savedPurchaser);
        return new ResponseEntity<>(purchaserResource, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<PurchaserResource> updatePurchaser(
            @PathVariable("id") Long purchaserId, @RequestBody PurchaserData purchaserData) {
        Purchaser purchaser = purchaserData.toModelBuilder().withId(purchaserId).build();
        purchaserRepository.update(purchaser);
        PurchaserResource purchaserResource = purchaserResourceAssembler.toResource(purchaser);
        return new ResponseEntity<>(purchaserResource, HttpStatus.OK);
    }

    @ExceptionHandler(PurchaserNotFoundException.class)
    public ResponseEntity<ApiError> purchaserNotFound(
            HttpServletRequest request, HttpServletResponse response, Exception ex) {

        return new ResponseEntity<>(
                new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI().toString()),
                HttpStatus.NOT_FOUND
        );
    }
}
