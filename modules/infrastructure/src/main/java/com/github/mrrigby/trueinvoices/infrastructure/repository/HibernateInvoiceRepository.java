package com.github.mrrigby.trueinvoices.infrastructure.repository;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceEntity;
import com.github.mrrigby.trueinvoices.infrastructure.repository.mapper.InvoiceMapper;
import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository;
import com.github.mrrigby.trueinvoices.repository.exceptions.InvoiceNotFoundException;
import com.google.common.base.Preconditions;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public Invoice save(Invoice invoice) {

        Preconditions.checkArgument(!invoice.getId().isPresent());

        InvoiceEntity detachedEntity = invoiceMapper.modelToEntity(invoice);
        Long invoiceId = (Long) sessionFactory.getCurrentSession().save(detachedEntity);

        return getById(invoiceId);
    }

    @Override
    @Transactional
    public boolean update(Invoice invoice) {

        Preconditions.checkArgument(invoice.getId().isPresent());

        Long invoiceId = invoice.getId().get();
        InvoiceEntity entityToUpdate = (InvoiceEntity) sessionFactory.getCurrentSession().get(InvoiceEntity.class, invoiceId);
        if (entityToUpdate == null) {
            return false;
        }

        InvoiceEntity detachedEntity = invoiceMapper.modelToEntity(invoice);
        sessionFactory.getCurrentSession().merge(detachedEntity);
        sessionFactory.getCurrentSession().flush();
        return true;
    }
}
