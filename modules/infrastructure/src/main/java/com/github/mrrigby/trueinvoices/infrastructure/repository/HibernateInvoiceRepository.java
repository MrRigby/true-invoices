package com.github.mrrigby.trueinvoices.infrastructure.repository;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceEntity;
import com.github.mrrigby.trueinvoices.infrastructure.repository.mapper.InvoiceMapper;
import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository;
import com.github.mrrigby.trueinvoices.repository.exceptions.InvoiceNotFoundException;
import com.google.common.base.Preconditions;
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
    public List<Invoice> listAll() {

        Criteria allInvoiceEntitiessCriteria = sessionFactory.getCurrentSession()
                .createCriteria(InvoiceEntity.class);
        List<InvoiceEntity> invoiceEntities = allInvoiceEntitiessCriteria.list();

        List<Invoice> invoices = invoiceEntities.stream()
                .map(invoiceMapper::entityToModel)
                .collect(toList());

        return invoices;
    }

    @Override
    @Transactional
    public Long count() {

        Criteria countInvoicesCriteria = sessionFactory.getCurrentSession()
                .createCriteria(InvoiceEntity.class)
                .setProjection(Projections.rowCount());

        return (Long) countInvoicesCriteria.uniqueResult();
    }

    @Override
    @Transactional
    public Page<Invoice> listPage(Pageable pageable) {

        Criteria pageableInvoicesCriteria = sessionFactory.getCurrentSession()
                .createCriteria(InvoiceEntity.class)
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .addOrder(Order.desc("documentDate").desc("id"));

        List<InvoiceEntity> invoiceEntities = pageableInvoicesCriteria.list();

        List<Invoice> invoices = invoiceEntities.stream()
                .map(invoiceMapper::entityToModel)
                .collect(toList());

        Long invoicesCount = count();

        return new PageImpl<Invoice>(invoices, pageable, invoicesCount);
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
