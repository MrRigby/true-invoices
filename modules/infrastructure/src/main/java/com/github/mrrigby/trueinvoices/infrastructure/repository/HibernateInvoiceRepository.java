package com.github.mrrigby.trueinvoices.infrastructure.repository;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceEntity;
import com.github.mrrigby.trueinvoices.infrastructure.repository.mapper.InvoiceMapper;
import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository;
import com.github.mrrigby.trueinvoices.repository.exceptions.InvoiceNotFoundException;
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
    public Long save(Invoice invoice) {
        InvoiceEntity invoiceEntity = invoiceMapper.modelToEntity(invoice);
        Long generatedId = (Long) sessionFactory.getCurrentSession().save(invoiceEntity);
        return generatedId;
    }

    @Override
    @Transactional
    public void update(Invoice invoice) {



    }
}
