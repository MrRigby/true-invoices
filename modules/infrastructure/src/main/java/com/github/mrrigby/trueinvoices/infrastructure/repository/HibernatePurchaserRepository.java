package com.github.mrrigby.trueinvoices.infrastructure.repository;

import com.github.mrrigby.trueinvoices.infrastructure.entity.PurchaserEntity;
import com.github.mrrigby.trueinvoices.infrastructure.repository.mapper.PurchaserMapper;
import com.github.mrrigby.trueinvoices.model.Purchaser;
import com.github.mrrigby.trueinvoices.repository.PurchaserListFilter;
import com.github.mrrigby.trueinvoices.repository.PurchaserRepository;
import com.github.mrrigby.trueinvoices.repository.exceptions.PurchaserNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author MrRigby
 */
@Repository
public class HibernatePurchaserRepository implements PurchaserRepository {

    private SessionFactory sessionFactory;
    private PurchaserMapper purchaserMapper;

    @Autowired
    public HibernatePurchaserRepository(SessionFactory sessionFactory, PurchaserMapper purchaserMapper) {
        this.sessionFactory = sessionFactory;
        this.purchaserMapper = purchaserMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Purchaser getById(Long id) throws PurchaserNotFoundException {

        PurchaserEntity purchaserEntity = (PurchaserEntity) sessionFactory.getCurrentSession().get(PurchaserEntity.class, id);
        if (purchaserEntity == null) {
            throw new PurchaserNotFoundException(
                    String.format("No purchaser with id: [%d] found!", id));
        }

        return purchaserMapper.entityToModel(purchaserEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Purchaser> listPage(Pageable pageable, PurchaserListFilter filter) {

        Criteria pageableCriteria = criteriaForListFilter(filter)
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .addOrder(Order.asc("id"));

        List<PurchaserEntity> entities = pageableCriteria.list();
        List<Purchaser> purchasers = entities.stream()
                .map(purchaserMapper::entityToModel)
                .collect(toList());

        Long purchasersCount = count(filter);

        return new PageImpl<>(purchasers, pageable, purchasersCount);
    }

    private Long count(PurchaserListFilter filter) {
        return (Long) criteriaForListFilter(filter)
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    private Criteria criteriaForListFilter(PurchaserListFilter filter) {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(PurchaserEntity.class);
        return criteria;
    }

    @Override
    @Transactional
    public Purchaser save(Purchaser purchaser) {
        return null;
    }

    @Override
    @Transactional
    public void update(Purchaser purchaser) throws PurchaserNotFoundException {

    }
}
