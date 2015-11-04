package com.github.mrrigby.trueinvoices.repository;

import com.github.mrrigby.trueinvoices.model.Purchaser;
import com.github.mrrigby.trueinvoices.repository.exceptions.PurchaserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repository responsible for persistence of {@link Purchaser} model objects.
 *
 * @author MrRigby
 */
public interface PurchaserRepository {

    /**
     * Tries to find purchaser for given id.
     *
     * @param id identifier of the purchaser to search for
     * @return purchaser object when found
     * @throws PurchaserNotFoundException when no purchaser for given identifier was found
     */
    public Purchaser getById(Long id) throws PurchaserNotFoundException;

    /**
     * List purchasers in a pageable manner.
     *
     * @param pageable parameters used for paging purposes
     * @param filter parameters with query criteria
     * @return a page of data with purchasers
     */
    public Page<Purchaser> listPage(Pageable pageable, PurchaserListFilter filter);

    /**
     * Saves the purchaser object.
     *
     * @param purchaser new object to save
     * @return saved object, with surrogate identifier generated if any
     */
    public Purchaser save(Purchaser purchaser);

    /**
     * Updates the purchaser object.
     *
     * @param purchaser object to update
     * @throws PurchaserNotFoundException when no purchaser for given identifier was found
     */
    public void update(Purchaser purchaser) throws PurchaserNotFoundException;
}
