package com.github.mrrigby.trueinvoices.infrastructure.repository;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceEntity;
import com.github.mrrigby.trueinvoices.infrastructure.repository.mapper.InvoiceMapper;
import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.repository.InvoiceListFilter;
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository;
import com.github.mrrigby.trueinvoices.repository.exceptions.InvoiceNotFoundException;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hibernate.criterion.Restrictions.like;

/**
 * @author MrRigby
 */
@Repository
public class HibernateInvoiceRepository implements InvoiceRepository {

    private SessionFactory sessionFactory;
    private InvoiceMapper invoiceMapper;

    @Autowired
    public HibernateInvoiceRepository(SessionFactory sessionFactory, InvoiceMapper invoiceMapper) {
        this.sessionFactory = sessionFactory;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice getById(Long id) throws InvoiceNotFoundException {

        InvoiceEntity invoiceEntity = (InvoiceEntity) sessionFactory.getCurrentSession().get(InvoiceEntity.class, id);
        if (invoiceEntity == null) {
            throw new InvoiceNotFoundException("No invoice with id: " + id);
        }

        return invoiceMapper.entityToModel(invoiceEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice getByBusinessId(String businessId) throws InvoiceNotFoundException {

        Criteria invoiceEntityCriteria = sessionFactory.getCurrentSession().createCriteria(InvoiceEntity.class)
                .add(Restrictions.eq("businessId", businessId));

        InvoiceEntity invoiceEntity = (InvoiceEntity) invoiceEntityCriteria.uniqueResult();
        if (invoiceEntity == null) {
            throw new InvoiceNotFoundException("No invoice with businessId: " + businessId);
        }

        return invoiceMapper.entityToModel(invoiceEntity);
    }

    @Override
    @Transactional
    public Page<Invoice> listPage(Pageable pageable, InvoiceListFilter filter) {

        Criteria pageableCriteria = criteriaForListFilter(filter)
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .addOrder(Order.desc("documentDate").desc("id"));

        List<InvoiceEntity> entities = pageableCriteria.list();
        List<Invoice> invoices = entities.stream()
                .map(invoiceMapper::entityToModel)
                .collect(toList());

        Long invoicesCount = count(filter);

        return new PageImpl<>(invoices, pageable, invoicesCount);
    }

    private Long count(InvoiceListFilter filter) {
        return (Long) criteriaForListFilter(filter)
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    private Criteria criteriaForListFilter(InvoiceListFilter filter) {

        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(InvoiceEntity.class);
        if (filter == null) {
            return criteria;
        }

        if (!Strings.isNullOrEmpty(filter.getBusinessIdMask())) {
            criteria.add(like("businessId", String.format("%%%s%%", filter.getBusinessIdMask())));
        }
        if (filter.getDateFrom() != null) {
            criteria.add(Restrictions.ge("documentDate", filter.getDateFrom()));
        }
        if (filter.getDateTo() != null) {
            criteria.add(Restrictions.le("documentDate", filter.getDateTo()));
        }
        if (!Strings.isNullOrEmpty(filter.getPurchaserMask())) {
            criteria.add(like("purchasers.name", String.format("%%%s%%", filter.getPurchaserMask())));
        }

        return criteria;
    }

    @Override
    @Transactional
    public Invoice save(Invoice invoice) {

        Preconditions.checkArgument(!invoice.getId().isPresent());

        InvoiceEntity detachedEntity = invoiceMapper.modelToEntity(invoice);
        Long invoiceId = (Long) sessionFactory.getCurrentSession().save(detachedEntity);

        return getById(invoiceId);
    }

    /**
     * Updates the invoice. The invoice has to exist in the datastore, otherwise InvoiceNotFoundException will be thrown.
     *
     * @param invoice invoice object to update
     * @throws InvoiceNotFoundException when no invoice with given {@link Invoice#getId()} found to update
     */
    @Override
    @Transactional
    public void update(Invoice invoice) throws InvoiceNotFoundException {

        Preconditions.checkArgument(invoice.getId().isPresent());
        Long invoiceId = invoice.getId().get();

        InvoiceEntity actualInvoiceEntity = (InvoiceEntity) sessionFactory.getCurrentSession().get(InvoiceEntity.class, invoiceId);
        if (actualInvoiceEntity == null) {
            throw new InvoiceNotFoundException("No invoice to update! InvoiceId = " + invoiceId);
        }

        InvoiceEntity invoiceEntityToUpdate = invoiceMapper.modelToEntity(invoice);
        sessionFactory.getCurrentSession().merge(invoiceEntityToUpdate);
        sessionFactory.getCurrentSession().flush();
    }
}
