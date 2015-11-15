package com.github.mrrigby.trueinvoices.infrastructure.repository;

import com.github.mrrigby.trueinvoices.infrastructure.entity.PurchaserEntity;
import com.github.mrrigby.trueinvoices.infrastructure.repository.mapper.HibernateRepository;
import com.github.mrrigby.trueinvoices.infrastructure.repository.mapper.PurchaserMapper;
import com.github.mrrigby.trueinvoices.model.Purchaser;
import com.github.mrrigby.trueinvoices.repository.PurchaserRepository;
import com.github.mrrigby.trueinvoices.repository.dto.PurchaserListFilter;
import com.github.mrrigby.trueinvoices.repository.exceptions.PurchaserNotFoundException;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
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

import static java.util.stream.Collectors.toList;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Hibernate-based implementation of the {@Link PurchaserRepository}, implemented as an extension of
 * {@link HibernateRepository}.
 *
 * @author MrRigby
 */
@Repository
public class HibernatePurchaserRepository extends HibernateRepository
        implements PurchaserRepository {

    private PurchaserMapper purchaserMapper;

    @Autowired
    public HibernatePurchaserRepository(SessionFactory sessionFactory, PurchaserMapper purchaserMapper) {
        super(sessionFactory);
        this.purchaserMapper = purchaserMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Purchaser getById(Long id) throws PurchaserNotFoundException {

        PurchaserEntity purchaserEntity = (PurchaserEntity) session().get(PurchaserEntity.class, id);
        if (purchaserEntity == null) {
            throw new PurchaserNotFoundException(
                    String.format("No purchaser with id=[%d] found!", id));
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
        Criteria criteria = session()
                .createCriteria(PurchaserEntity.class);
        if (filter == null) {
            return criteria;
        }

        if (!Strings.isNullOrEmpty(filter.getNameMask())) {
            criteria.add(like("purchaserData.name", String.format("%%%s%%", filter.getNameMask())));
        }
        if (!Strings.isNullOrEmpty(filter.getTaxIdentifier())) {
            criteria.add(eq("purchaserData.taxId", String.format("%s", filter.getTaxIdentifier())));
        }

        return criteria;
    }

    @Override
    @Transactional
    public Purchaser save(Purchaser purchaser) {
        Preconditions.checkNotNull(purchaser);
        Preconditions.checkArgument(!purchaser.getId().isPresent());

        PurchaserEntity detachedEntity = purchaserMapper.modelToEntity(purchaser);
        Long purchaserId = (Long) session().save(detachedEntity);

        return getById(purchaserId);
    }

    @Override
    @Transactional
    public void update(Purchaser purchaser) throws PurchaserNotFoundException {
        Preconditions.checkNotNull(purchaser);
        Preconditions.checkArgument(purchaser.getId().isPresent());
        Long purchaserId = purchaser.getId().get();

        PurchaserEntity actualPurchaserEntity = (PurchaserEntity) session().get(PurchaserEntity.class, purchaserId);
        if (actualPurchaserEntity == null) {
            throw new PurchaserNotFoundException(
                    String.format("No purchaser to update found for id=[%d]", purchaserId));
        }

        PurchaserEntity purchaserEntityToUpdate = purchaserMapper.modelToEntity(purchaser);
        session().merge(purchaserEntityToUpdate);
    }
}
