package com.github.mrrigby.trueinvoices.infrastructure.repository;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceEntity;
import com.github.mrrigby.trueinvoices.infrastructure.entity.PurchaserEntity;
import com.github.mrrigby.trueinvoices.model.Purchaser;
import com.github.mrrigby.trueinvoices.repository.PurchaserListFilter;
import com.github.mrrigby.trueinvoices.repository.PurchaserRepository;
import com.github.mrrigby.trueinvoices.repository.exceptions.PurchaserNotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.github.mrrigby.trueinvoices.model.Purchaser.PurchaserBuilder.aPurchaser;

/**
 * @author MrRigby
 */
@Repository
public class HibernatePurchaserRepository implements PurchaserRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public HibernatePurchaserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Purchaser getById(Long id) throws PurchaserNotFoundException {

        PurchaserEntity purchaserEntity = (PurchaserEntity) sessionFactory.getCurrentSession().get(PurchaserEntity.class, id);
        if (purchaserEntity == null) {
            throw new PurchaserNotFoundException(
                    String.format("No purchaser with id: [%d] found!", id));
        }

        // FIXME introduce mapper
        return aPurchaser()
                .withId(purchaserEntity.getId())
                .withName(purchaserEntity.getPurchaserData().getName())
                .withAddress(purchaserEntity.getPurchaserData().getAddress())
                .withTaxIdentifier(purchaserEntity.getPurchaserData().getTaxId())
                .build();
    }

    @Override
    public Page<Purchaser> listPage(Pageable pageable, PurchaserListFilter filter) {
        return null;
    }

    @Override
    public Purchaser save(Purchaser purchaser) {
        return null;
    }

    @Override
    public void update(Purchaser purchaser) throws PurchaserNotFoundException {

    }
}
